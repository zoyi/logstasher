package com.zoyi.logstasher;

/**
 * Created by lloyd on 2017-04-04
 */
public interface Logstasher extends AutoCloseable {
  void initialize(Configuration configuration);
  void close() throws Exception;
  void put(Message<?> message);
}
