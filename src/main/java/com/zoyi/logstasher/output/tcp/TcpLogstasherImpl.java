package com.zoyi.logstasher.output.tcp;

import com.zoyi.logstasher.Logstashers;
import com.zoyi.logstasher.util.annotation.Name;
import com.zoyi.logstasher.util.annotation.Stasher;
import com.zoyi.logstasher.configuration.Configuration;
import com.zoyi.logstasher.Logstasher;
import com.zoyi.logstasher.message.BsonMessage;
import com.zoyi.logstasher.message.Message;
import com.zoyi.logstasher.queue.BsonMessageQueue;
import com.zoyi.logstasher.queue.MessageQueue;
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
import java.util.concurrent.atomic.AtomicReference;


/**
 * Created by lloyd on 2017-04-04
 */
@Stasher(name = Name.TCP)
public class TcpLogstasherImpl implements Logstasher {
  private final AtomicReference<NetSocket> socketRef = new AtomicReference<>();

  private Configuration configuration;
  private boolean initialized;
  private int popSize;

  private Instant lastPushedTimestamp;


  @Override
  public void initialize() {
    this.initialize(null);
  }


  @Override
  public void initialize(Configuration configuration) {
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


  private boolean checkSocketConnection() {
    synchronized (socketRef) {
      if (this.socketRef.get() == null) {
        final ExecutorService notifier = Executors.newSingleThreadExecutor();

        try {
          final Future<NetSocket> result = notifier.submit(() -> {
            final NetClientOptions options = new NetClientOptions();
            options.setConnectTimeout(
              configuration.getInteger("connectionTimeout", 5000)
            );
            options.setReconnectAttempts(
              configuration.getInteger("reconnectAttempts", 3)
            );

            final AtomicReference<NetSocket> innerRef = new AtomicReference<>();

            Vertx.vertx()
                 .createNetClient(options)
                 .connect(configuration.getInteger("port", 12340),
                          configuration.getString("host", "localhost"),
                          connectionResult -> {
                            if (connectionResult.succeeded()) {
                              innerRef.set(connectionResult.result());
                            } else {
                              throw new RuntimeException(connectionResult.cause());
                            }
                          });

            while (innerRef.get() == null) {
              /* await */
              Thread.sleep(100);
            }
            return innerRef.get();
          });

          // Set reference
          return this.socketRef.compareAndSet(
              null,
              result.get(5000 * 3, TimeUnit.MILLISECONDS)
          );

        } catch (Exception e) {
          return false;

        } finally {
          notifier.shutdownNow();
        }
      }
    }

    return true;
  }


  @Override
  public void put(Map<String, Object> data) {
    if (checkSocketConnection()) {
      final MessageQueue queue = BsonMessageQueue.create(2, 1024);
      queue.put(new BsonMessage(data));

      // Flush messages
      if (queue.isExceeded() ||
          lastPushedTimestamp != null &&
          Duration.between(lastPushedTimestamp, Instant.now())
                  .toMillis() > 1000) {
        push();
      }

      lastPushedTimestamp = Instant.now();

    } else {
      throw new RuntimeException("Socket connection failed");
    }
  }


  private void push() {
    if (checkSocketConnection()) {
      final List<Message> messages =
          BsonMessageQueue.getInstance()
                          .pop(BsonMessage.TYPE,
                               popSize,
                               configuration.getInteger("maxTraverses", 20)
                          );

      System.out.println("Messages to be written: " + messages.size());

      for (Message message : messages) {
        final BsonMessage bsonMessage = (BsonMessage) message;
        socketRef.get().write(Buffer.buffer(bsonMessage.encode()));
      }

      lastPushedTimestamp = Instant.now();

      System.out.println("Pushed");
    }
  }
}
