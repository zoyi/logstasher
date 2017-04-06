package com.zoyi.logstasher;

import com.zoyi.logstasher.configuration.Configuration;

import java.util.Map;


/**
 * Created by lloyd on 2017-04-04
 */
public interface Logstasher extends AutoCloseable {
  /**
   * Simple form of {@link #initialize(Configuration)} method.
   * Initializes Logstasher object with configuration that made by property file.
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
