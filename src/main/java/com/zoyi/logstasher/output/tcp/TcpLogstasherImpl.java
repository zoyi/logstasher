package com.zoyi.logstasher.output.tcp;

import com.zoyi.logstasher.Logstasher;
import com.zoyi.logstasher.configuration.Configuration;
import com.zoyi.logstasher.configuration.ConfigurationImpl;
import com.zoyi.logstasher.message.JsonMessage;
import com.zoyi.logstasher.message.Message;
import com.zoyi.logstasher.queue.BaseMessageQueue;
import com.zoyi.logstasher.queue.MessageQueue;
import com.zoyi.logstasher.util.annotation.Name;
import com.zoyi.logstasher.util.annotation.Stasher;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;


/**
 * @author Junbong
 * @since 2017-04-04
 */
@Stasher(name = Name.TCP)
public class TcpLogstasherImpl implements Logstasher {
  public static Configuration defaultConfiguration() {
    return
        new ConfigurationImpl()
            .put(POP_SIZE, DEFAULT_POP_SIZE)
            .put(CONNECTION_TIME_OUT, DEFAULT_CONNECTION_TIMEOUT)
            .put(RECONNECT_ATTEMPTS, DEFAULT_RECONNECT_ATTEMPTS)
            .put(HOST, DEFAULT_HOST)
            .put(PORT, DEFAULT_PORT)
            .put(MAX_TRAVERSES, DEFAULT_MAX_TRAVERSES);
  }


  private static final Integer DEFAULT_POP_SIZE           = 10;
  private static final Integer DEFAULT_CONNECTION_TIMEOUT = 5000;
  private static final Integer DEFAULT_RECONNECT_ATTEMPTS = 3;
  private static final String  DEFAULT_HOST               = "localhost";
  private static final Integer DEFAULT_PORT               = 12340;
  private static final Integer DEFAULT_MAX_TRAVERSES      = 20;

  public static final String POP_SIZE            = "popSize";
  public static final String CONNECTION_TIME_OUT = "connectionTimeout";
  public static final String RECONNECT_ATTEMPTS  = "reconnectAttempts";
  public static final String HOST                = "host";
  public static final String PORT                = "port";
  public static final String MAX_TRAVERSES       = "maxTraverses";

  private final AtomicReference<NetSocket> socketRef = new AtomicReference<>();
  private Consumer<Void> onSocketConnectionClosedCallback;

  // Configuration
  private Configuration configuration;
  private int popSize;

  // Status
  private boolean initialized;
  private final AtomicBoolean tryingToConnect = new AtomicBoolean();
  private Instant lastPushedTimestamp;


  @Override
  public void initialize() {
    this.initialize(defaultConfiguration());
  }


  @Override
  public void initialize(Configuration configuration) {
    // TODO: merge base configuration and new configuration

    if (Objects.isNull(configuration))
      configuration = new ConfigurationImpl();

    if (!this.initialized) {
      this.configuration = configuration;
      this.popSize = configuration.getInteger("popSize", 10);
      this.initialized = true;
    }
  }


  @Override
  public synchronized void close() throws Exception {
    // TODO: handle messages which remained in queue
    consumeMessagesLeftInQueue();

    // Disconnect socket
    if (checkSocketConnection()) {
      socketRef.get().close();
      socketRef.set(null);
    }
  }


  protected void consumeMessagesLeftInQueue() throws Exception {
    final ExecutorService notifier = Executors.newSingleThreadExecutor();
    notifier.submit((Callable<Void>) () -> {
      push();
      return null;
    }).get();

    notifier.awaitTermination(5000, TimeUnit.MILLISECONDS);
  }


  public Logstasher setOnSocketConnectionClosedCallback(Consumer<Void> c) {
    this.onSocketConnectionClosedCallback = c;
    return this;
  }


  private boolean checkSocketConnection() {
    synchronized (socketRef) {
      if (!this.tryingToConnect.get() && this.socketRef.get() == null) {
        tryingToConnect.set(true);
        final ExecutorService notifier = Executors.newSingleThreadExecutor();

        try {
          final Future<NetSocket> result = notifier.submit(() -> {
            final String host = configuration.getString(HOST, DEFAULT_HOST);
            final int port = configuration.getInteger(PORT, DEFAULT_PORT);
            final int connectionTimeout = configuration.getInteger(CONNECTION_TIME_OUT, DEFAULT_CONNECTION_TIMEOUT);
            final int reconnectAttempts = configuration.getInteger(RECONNECT_ATTEMPTS, DEFAULT_RECONNECT_ATTEMPTS);

            final NetClientOptions options = new NetClientOptions();
            options.setConnectTimeout(connectionTimeout);
            options.setReconnectAttempts(reconnectAttempts);

            final AtomicReference<NetSocket> innerRef = new AtomicReference<>();
            final AtomicBoolean connecting = new AtomicBoolean(true);

            Vertx.vertx()
                 .createNetClient(options)
                 .connect(port, host, connectionResult -> {
                   if (connectionResult.succeeded()) {
                     final NetSocket socket = connectionResult.result();
                     socket.closeHandler(_void -> {
                       // Remove reference to socket
                       synchronized (socketRef) {
                         socketRef.set(null);
                         if (onSocketConnectionClosedCallback != null)
                           onSocketConnectionClosedCallback.accept(null);
                       }
                     });
                     innerRef.set(socket);
                     connecting.set(false);
                   } else {
                     connecting.set(false);
                     throw new RuntimeException(connectionResult.cause());
                   }
                 });

            while (connecting.get() && innerRef.get() == null) {
              /* await */
              Thread.sleep(100);
            }

            return innerRef.get();
          });

          // Set new reference
          this.socketRef.compareAndSet(
              null,
              result.get(5000 * 3, TimeUnit.MILLISECONDS)
          );

          return socketRef.get() != null;

        } catch (Exception e) {
          return false;

        } finally {
          notifier.shutdownNow();
          tryingToConnect.set(false);
        }
      }
    }

    return true;
  }


  @Override
  public void put(Map<String, Object> data) {
    this.put(new JsonMessage(data));
  }


  @Override
  public void put(Message<?> message) {
    if (checkSocketConnection()) {
      final MessageQueue queue = BaseMessageQueue.create(
          configuration.getInteger(BaseMessageQueue.CAPACITY, 1024),
          configuration.getInteger(BaseMessageQueue.SIZE, 2048)
      );
      queue.put(message);

      // Flush messages
      try {
        if (queue.isExceeded() ||
            lastPushedTimestamp != null &&
                Duration.between(lastPushedTimestamp, Instant.now())
                        .toMillis() > 1000) {
          push();
        }

        lastPushedTimestamp = Instant.now();

      } catch (Exception e) {
        throw new RuntimeException(e);
      }

    } else {
      // TODO: Create putExceptionCallback
      throw new RuntimeException("Socket connection failed");
    }
  }


  @Override
  public void push() throws Exception {
    if (checkSocketConnection()) {
      final List<Message> messages =
          BaseMessageQueue.getInstance()
                          .pop(JsonMessage.TYPE,
                               popSize,
                               configuration.getInteger(MAX_TRAVERSES, DEFAULT_MAX_TRAVERSES)
                          );

      System.out.println("Messages to be written: " + messages.size());

      for (Message message : messages) {
        final JsonMessage jsonMessage = (JsonMessage) message;
        socketRef.get().write(Buffer.buffer(jsonMessage.encode()));
        socketRef.get().write("\n");
      }

      lastPushedTimestamp = Instant.now();

      System.out.println("Pushed");

    } else {
      // TODO: Create pushExceptionCallback
    }
  }
}
