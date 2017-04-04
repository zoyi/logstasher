package com.zoyi.logstasher.queue;

import com.zoyi.logstasher.Message;
import com.zoyi.logstasher.MessageQueue;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * Created by lloyd on 2017-04-04
 */
public class MessageQueueImpl implements MessageQueue {
  private final ConcurrentLinkedQueue<Message> qInternal = new ConcurrentLinkedQueue<>();


  @Override
  public void put(Message<?> message) {

  }


  @Override
  public List<Message> pop(int count) {
    return null;
  }


  @Override
  public int size() {
    return 0;
  }
}
