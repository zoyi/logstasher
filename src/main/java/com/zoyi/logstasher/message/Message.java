package com.zoyi.logstasher.message;

/**
 * Created by lloyd on 2017-04-04
 */
public interface Message<T> {
  String getType();
  int byteLength();
  T encode();
}
