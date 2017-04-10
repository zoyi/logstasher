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

  /**
   * Close this client to make stop operation.
   * Once closed, not any messages can be accepted through this client
   * until {@link #initialize()} method called.
   *
   * @throws Exception When any exception occurred during closing
   * @apiNote This method should blocks all input, and consume all messages by configuration.
   */
  void close() throws Exception;

  /**
   * Put new log data into this client.
   * Specified map converted to {@link Message} object automatically.
   * If given message does not have {@code @timestamp} field,
   * client will put this field with current time.
   *
   * @param data Log message
   * @throws NullPointerException specified log message is {@code null}.
   */
  void put(Map<String, Object> data);

  /**
   * Put new log data into this client.
   * If given message does not have {@code @timestamp} field,
   * client will put this field with current time.
   *
   * @param message Log message
   * @throws NullPointerException specified log message is {@code null}.
   */
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
