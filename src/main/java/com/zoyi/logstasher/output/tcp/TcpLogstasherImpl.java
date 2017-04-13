package com.zoyi.logstasher.output.tcp;

import com.zoyi.logstasher.Logstasher;
import com.zoyi.logstasher.configuration.Configuration;
import com.zoyi.logstasher.message.JsonMessage;
import com.zoyi.logstasher.message.Message;
import com.zoyi.logstasher.output.BaseLogstasherImpl;
import com.zoyi.logstasher.queue.BaseMessageQueue;
import com.zoyi.logstasher.queue.MessageQueue;
import com.zoyi.logstasher.util.StringUtil;
import com.zoyi.logstasher.util.annotation.Name;
import com.zoyi.logstasher.util.annotation.Stasher;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static com.zoyi.logstasher.output.tcp.TcpConfiguration.*;


/**
 * @author Junbong
 * @since 2017-04-04
 */
@Stasher(name = Name.TCP)
public class TcpLogstasherImpl extends BaseLogstasherImpl {
  private final AtomicReference<Vertx> vertxRef = new AtomicReference<>();
  private final AtomicReference<NetClient> clientRef = new AtomicReference<>();
  private final AtomicReference<NetSocket> socketRef = new AtomicReference<>();
  private Consumer<Void> onSocketConnectionClosedCallback;

  // Configuration
  private Configuration configuration = new TcpConfiguration();
  private int popSize;
  private int maxTraverse;
  private int pushInterval;

  // Status
  private final AtomicBoolean initialized = new AtomicBoolean();
  private final AtomicBoolean tryingToConnect = new AtomicBoolean();
  private Instant lastPushedTimestamp = Instant.now();


  @Override
  public void initialize() {
    this.initialize(null);
  }


  @Override
  public void initialize(Configuration configuration) {
    synchronized (this.initialized) {
      if (!this.initialized.get()) {
        if (configuration != null) getConfiguration().merge(configuration);
        this.popSize = getConfiguration().getInteger(POP_SIZE, DEFAULT_POP_SIZE);
        this.maxTraverse = getConfiguration().getInteger(MAX_TRAVERSES, DEFAULT_MAX_TRAVERSES);
        this.pushInterval = getConfiguration().getInteger(PUSH_INTERVAL, DEFAULT_PUSH_INTERVAL);
        this.initialized.set(true);

      } else {
        printStdOut("This client already initialized: Can not be initialized again before closed");
      }
    }
  }


  @Override
  public synchronized void close() throws Exception {
    // Set this flag false to make it stop operating
    this.initialized.set(false);

    if (getConfiguration().getBoolean(
        CONSUME_MESSAGES_AT_CLOSE, DEFAULT_CONSUME_MESSAGES_AT_CLOSE)) {
      consumeAllMessagesInQueue();
    }

    // Disconnect socket
    if (checkSocketConnection()) {
      socketRef.get().close();
      socketRef.set(null);
    }

    // Close socket client
    if (clientRef.get() != null) {
      clientRef.get().close();
      clientRef.set(null);
      vertxRef.get().close();
      vertxRef.set(null);
      printStdOut("Client closed");
    }
  }


  /**
   * Consume all messages left in queue.
   *
   * @throws Exception when any exception occurred during consuming
   */
  protected void consumeAllMessagesInQueue() throws Exception {
    final int waitingTime =
        getConfiguration().getInteger(
            MAX_CLOSE_WAITING_TIME, DEFAULT_MAX_CLOSE_WAITING_TIME);

    // Handle all messages left in queue
    final ExecutorService notifier = Executors.newSingleThreadExecutor();
    notifier.submit((Callable<Void>) () -> {
      // TODO: push(all)
      push();
      return null;
    }).get();

    notifier.shutdown();
    notifier.awaitTermination(waitingTime, TimeUnit.MILLISECONDS);
  }


  /**
   * Retrieve configuration which initialized with this client.
   * <p>
   *   <i>Note</i>: Do not change anything manually in this configuration.
   *   Instead, {@link #close()} first this client, and call
   *   {@link #initialize(Configuration)} again with new configuration.
   * </p>
   *
   * @return configuration
   */
  protected Configuration getConfiguration() {
    return this.configuration;
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
            if (clientRef.get() == null) {
              final int connectionTimeout =
                  getConfiguration().getInteger(CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT);
              final int reconnectAttempts =
                  getConfiguration().getInteger(RECONNECT_ATTEMPTS, DEFAULT_RECONNECT_ATTEMPTS);

              final NetClientOptions options = new NetClientOptions();
              options.setConnectTimeout(connectionTimeout);
              options.setReconnectAttempts(reconnectAttempts);

              final Vertx vertx = Vertx.vertx();
              final NetClient netClient = vertx.createNetClient(options);
              vertxRef.set(vertx);
              clientRef.set(netClient);
            }

            final String host = getConfiguration().getString(HOST, DEFAULT_HOST);
            final int port = getConfiguration().getInteger(PORT, DEFAULT_PORT);

            final AtomicReference<NetSocket> innerRef = new AtomicReference<>();
            final AtomicBoolean connecting = new AtomicBoolean(true);

            clientRef.get()
                     .connect(port, host, connectionResult -> {
                       if (connectionResult.succeeded()) {
                         printStdOut(String.format("Socket connected(%s:%d)", host, port));

                         final NetSocket socket = connectionResult.result();
                         socket.closeHandler(_void -> {
                           printStdOut(String.format("Socket closed(%s:%d)", host, port));

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
    if (this.initialized.get()) {
      if (checkSocketConnection()) {
        final MessageQueue queue = BaseMessageQueue.create(
            getConfiguration().getInteger(BaseMessageQueue.CAPACITY, 1024),
            getConfiguration().getInteger(BaseMessageQueue.SIZE, 2048)
        );
        queue.put(message);

        printStdOut(">>\t" +queue.toString());

        // Flush messages
        try {
          printStdOut("Queue is exceeded?: " + queue.isExceeded());

          if (queue.isExceeded() ||
              lastPushedTimestamp != null &&
                  Duration.between(lastPushedTimestamp, Instant.now())
                          .toMillis() > this.pushInterval) {
            push();
          }

          printStdOut("<<\t" + queue.toString());

        } catch (Exception e) {
          throw new RuntimeException(e);
        }

      } else {
        // TODO: Create putExceptionCallback
        throw new RuntimeException("Socket connection failed");
      }

    } else {
      printStdOut("Can not accept any message: this client is closed or not initialized yet");
    }
  }


  @Override
  public void push() throws Exception {
    this.push(JsonMessage.TYPE, popSize, maxTraverse);
  }


  @Override
  protected void push(String type, int count, int maxTraverses) throws Exception {
    if (checkSocketConnection()) {
      final List<Message> messages =
          BaseMessageQueue.getInstance()
                          .pop(type, count, maxTraverses);

      printStdOut("Messages to be written: " + messages.size());

      for (Message message : messages) {
        final JsonMessage jsonMessage = (JsonMessage) message;
        setTimestampIfNotSet(message);
        socketRef.get().write(Buffer.buffer(jsonMessage.encode()));
        socketRef.get().write("\n");
      }

      lastPushedTimestamp = Instant.now();

      printStdOut("All messages in queue has been pushed.");

    } else {
      // TODO: Create pushExceptionCallback
    }
  }


  protected void setTimestampIfNotSet(Message message) {
    if (!message.containsKey("@timestamp") && !message.containsKey("timestamp")) {
      final String timezone = getConfiguration().getString("timezone");
      message.setTimestamp(LocalDateTime.now(
          StringUtil.isNotNullOrEmpty(timezone)
              ? ZoneId.of(timezone)
              : ZoneId.systemDefault()));
    }
  }
}
