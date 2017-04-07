package com.zoyi.logstasher.output.tcp;

import com.zoyi.logstasher.Logstasher;
import com.zoyi.logstasher.Logstashers;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;


/**
 * Created by lloyd on 2017-04-04
 */
@Stasher(name = Name.TCP)
public class TcpLogstasherImpl implements Logstasher {
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
    final Configuration configuration = new ConfigurationImpl();
    configuration.put("popSize", 10);
    configuration.put("connectionTimeout", 5000);
    configuration.put("reconnectAttempts", 3);
    configuration.put("host", "localhost");
    configuration.put("port", 12340);
    configuration.put("maxTraverses", 20);

    this.initialize(configuration);
  }


  @Override
  public void initialize(Configuration configuration) {
    // TODO: merge base configuration and new configuration

    if (Objects.isNull(configuration))
      configuration = Logstashers.newConfiguration();

    if (!this.initialized) {
      this.configuration = configuration;
      this.popSize = configuration.getInteger("popSize", 10);
      this.initialized = true;
    }
  }


  @Override
  public synchronized void close() throws Exception {
    // TODO: handle messages which remained in queue

    // Disconnect socket
    if (checkSocketConnection()) {
      socketRef.get().close();
      socketRef.set(null);
    }
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
            final String host = configuration.getString("host", "localhost");
            final int port = configuration.getInteger("port", 12340);
            final int connectionTimeout = configuration.getInteger("connectionTimeout", 5000);
            final int reconnectAttempts = configuration.getInteger("reconnectAttempts", 3);

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
    if (checkSocketConnection()) {
      final MessageQueue queue = BaseMessageQueue.create(1024, 2048);
      queue.put(new JsonMessage(data));

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


  private void push() throws Exception {
    if (checkSocketConnection()) {
      final List<Message> messages =
          BaseMessageQueue.getInstance()
                          .pop(JsonMessage.TYPE,
                               popSize,
                               configuration.getInteger("maxTraverses", 20)
                          );

      System.out.println("Messages to be written: " + messages.size());

      for (Message message : messages) {
        final JsonMessage jsonMessage = (JsonMessage) message;
        socketRef.get().write(Buffer.buffer(jsonMessage.encode()));
        socketRef.get().write("\n");

        System.out.println("Write");
      }

      lastPushedTimestamp = Instant.now();

      System.out.println("Pushed");

    } else {
      // TODO: Create pushExceptionCallback
    }
  }
}
