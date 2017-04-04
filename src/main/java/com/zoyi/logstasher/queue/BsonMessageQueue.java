package com.zoyi.logstasher.queue;

import com.zoyi.logstasher.message.BsonMessage;
import com.zoyi.logstasher.message.Message;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by lloyd on 2017-04-04
 */
public class BsonMessageQueue implements MessageQueue {
  public static int DEFAULT_CAPACITY_LIMIT = Integer.MAX_VALUE;
  public static int DEFAULT_SIZE_LIMIT = Integer.MAX_VALUE;


  public static MessageQueue create(int capacity, int size) {
    if (instance == null) {
      synchronized (BsonMessageQueue.class) {
        if (instance == null) {
          // TODO: use default value when arguments are negative number

          instance = new BsonMessageQueue((int)(capacity * 1.1), (int)(size * 1.1));
        }
      }
    }

    return instance;
  }


  public static MessageQueue getInstance() {
    return create(DEFAULT_CAPACITY_LIMIT, DEFAULT_SIZE_LIMIT);
  }


  private static BsonMessageQueue instance;

  private final int capacityLimit;
  private final int sizeLimit;
  private final ConcurrentLinkedQueue<Message> qInternal = new ConcurrentLinkedQueue<>();
  private final AtomicInteger currentByteSize = new AtomicInteger();


  private BsonMessageQueue(int capacityLimit, int sizeLimit) {
    this.capacityLimit = capacityLimit;
    this.sizeLimit = sizeLimit;
  }


  @Override
  public void put(Message<?> message) {
    synchronized (qInternal) {
      qInternal.add(message);
      currentByteSize.addAndGet(message.byteLength());
    }
  }


  @Override
  public List<Message> pop(String type, int count, int maxTraverses) {
    final List<Message> result = new LinkedList<>();

    synchronized (qInternal) {
      Message msg;
      int traverse = 0;

      while ((msg=qInternal.peek()) != null
          && traverse <= maxTraverses) {
        if (msg.getType().equals(BsonMessage.TYPE)) {
          result.add(qInternal.poll());
        }
        traverse++;
      }
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
