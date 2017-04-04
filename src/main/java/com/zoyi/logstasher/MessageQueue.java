package com.zoyi.logstasher;

import java.util.List;


/**
 * Created by lloyd on 2017-04-04
 */
public interface MessageQueue {
  void put(Message<?> message);
  List<Message> pop(int count);
  int size();
}
