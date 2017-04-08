package com.zoyi.logstasher;

import com.zoyi.logstasher.configuration.Configuration;
import com.zoyi.logstasher.message.Message;

import java.util.Map;


/**
 * @author Junbong
 * @since 2017-04-04
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

  void put(Message<?> message);

  /**
   * Force push messages in queue.
   * <p>
   * <i>Note</i>: in most situation this method is not needed to call
   * because {@link #put(Map)} may automatically invokes this.
   * </p>
   *
   * @throws Exception when error occurred during push.
   */
  void push() throws Exception;
}
