package com.zoyi.logstasher;

import com.zoyi.logstasher.configuration.Configuration;

import java.util.Map;


/**
 * Created by lloyd on 2017-04-04
 */
public interface Logstasher extends AutoCloseable {
  /**
   * Simple form of {@link #initialize(Configuration)} method.
   * Initializes Logstasher object with default configuration.
   *
   * @apiNote It calls {@link #initialize(Configuration)} method with {@code null} parameter
   */
  void initialize();

  /**
   * Initializes Logstasher object with specified configuration.
   *
   * @param configuration configuration which configured
   */
  void initialize(Configuration configuration);

  void close() throws Exception;

  void put(Map<String, Object> data);
}
