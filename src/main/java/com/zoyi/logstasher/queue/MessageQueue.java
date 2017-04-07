package com.zoyi.logstasher.queue;

import com.zoyi.logstasher.message.Message;

import java.util.List;


/**
 * @author Junbong
 * @since 2017-04-04
 */
public interface MessageQueue {
  void put(Message<?> message);

  /**
   * Collect messages from this queue by specified type of message.
   * Result has equal or more than zero elements to given count;
   *
   * @param type         Type to filter message.
   * @param count        Count that how many messages to collect.
   * @param maxTraverses Maximum count of node to predicate message.
   * @return List of messages which collected from this queue
   * @apiNote {@code type} can be {@code null} and do not filter any message when {@code null} passed.
   * {@code count} might be bigger than zero. {@code maxTraverses} might be equal or bigger than count.
   */
  List<Message> pop(String type, int count, int maxTraverses);

  int capacity();

  int size();

  boolean isExceeded();
}
