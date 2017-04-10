package com.zoyi.logstasher.queue;

import com.zoyi.logstasher.message.Message;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author Junbong
 * @since 2017-04-04
 */
public class BaseMessageQueue implements MessageQueue {
  public static final int    DEFAULT_CAPACITY_LIMIT = 512;
  public static final int    DEFAULT_SIZE_LIMIT     = DEFAULT_CAPACITY_LIMIT * 1024;
  public static final String CAPACITY               = "capacity";
  public static final String SIZE                   = "size";


  /**
   * Create new message queue with specified capacity and size.
   * <p>
   *   The capacity means how many message can be queued in this queue.
   * </p>
   * <p>
   *   The size means actual memory bytes of all messages in this queue.
   *   So this value always has to be equal or bigger than capacity.
   * </p>
   *
   * @param capacity Maximum count of messages
   * @param size     Maximum memory allocation size of all messages
   * @return Newly created message queue or instance created before.
   */
  public static MessageQueue create(int capacity, int size) {
    if (instance == null) {
      synchronized (BaseMessageQueue.class) {
        if (instance == null) {
          capacity = Math.max(capacity, DEFAULT_CAPACITY_LIMIT);
          size = Math.max(size, DEFAULT_SIZE_LIMIT);

          instance = new BaseMessageQueue((int)(capacity * 1.1), (int)(size * 1.1));
        }
      }
    }

    return instance;
  }


  public static MessageQueue getInstance() {
    return create(DEFAULT_CAPACITY_LIMIT, DEFAULT_SIZE_LIMIT);
  }


  private static BaseMessageQueue instance;

  private final int capacityLimit;
  private final int sizeLimit;
  private final ConcurrentLinkedQueue<Message> qInternal = new ConcurrentLinkedQueue<>();
  private final AtomicInteger currentByteSize = new AtomicInteger();


  private BaseMessageQueue(int capacityLimit, int sizeLimit) {
    this.capacityLimit = capacityLimit;
    this.sizeLimit = sizeLimit;
  }


  @Override
  public void put(Message<?> message) {
    synchronized (qInternal) {
      qInternal.add(message);
      currentByteSize.addAndGet(message.getByteLength());
    }
  }


  @Override
  public List<Message> pop(String type, int count, int maxTraverses) {
    // Count might be bigger than zero
    count = Math.max(count, 1);
    // MaxTraverses might be equal or bigger than count
    maxTraverses = Math.max(maxTraverses, count);

    final List<Message> result = new LinkedList<>();

    synchronized (qInternal) {
      Message msg;
      int traverse = 0;
      int reduceSize = 0;

      while ((msg=qInternal.peek()) != null
          && traverse <= maxTraverses
          && result.size() < count) {
        if (msg.getType().equals(type)) {
          final Message message = qInternal.poll();
          result.add(message);

          // Note message sizes
          reduceSize += message.getByteLength();
        }

        traverse++;
      }

      // Arrange size
      currentByteSize.addAndGet(-reduceSize);
    }

    return result;
  }


  @Override
  public int capacity() {
    return qInternal.size();
  }


  @Override
  public int size() {
    return currentByteSize.get();
  }


  @Override
  public boolean isExceeded() {
    return capacity() >= capacityLimit
        || size() >= sizeLimit;
  }
}
