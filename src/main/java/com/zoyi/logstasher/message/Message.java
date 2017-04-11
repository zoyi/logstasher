package com.zoyi.logstasher.message;

import java.time.Instant;
import java.time.LocalDateTime;


/**
 * @author Junbong
 * @since 2017-04-04
 */
public interface Message<T> {
  String getType();

  int getByteLength();

  T encode() throws Exception;

  Message<T> setTimestamp(Instant instant);

  Message<T> setTimestamp(LocalDateTime localDateTime);

  Message<T> put(String key, Boolean value);

  Message<T> put(String key, Character value);

  Message<T> put(String key, Short value);

  Message<T> put(String key, Float value);

  Message<T> put(String key, Integer value);

  Message<T> put(String key, Long value);

  Message<T> put(String key, Double value);

  Message<T> put(String key, CharSequence value);

  Message<T> put(String key, String value);

  Message<T> put(String key, Object value);

  boolean containsKey(String key);
}
