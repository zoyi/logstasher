package com.zoyi.logstasher.message;

/**
 * @author Junbong
 * @since 2017-04-04
 */
public interface Message<T> {
  String getType();
  int getByteLength();
  T encode() throws Exception;
}
