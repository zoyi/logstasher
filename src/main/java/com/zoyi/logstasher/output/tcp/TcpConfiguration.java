package com.zoyi.logstasher.output.tcp;

import com.zoyi.logstasher.configuration.Configuration;
import com.zoyi.logstasher.configuration.ConfigurationImpl;

import java.time.ZoneId;


/**
 * @author Junbong
 */
public class TcpConfiguration extends ConfigurationImpl {
  // Config keys
  public static final String HOST = "host";

  public static final String PORT = "port";

  public static final String CONNECTION_TIMEOUT = "connectionTimeout";

  public static final String RECONNECT_ATTEMPTS = "reconnectAttempts";

  public static final String POP_SIZE = "popSize";

  public static final String PUSH_INTERVAL = "pushInterval";

  public static final String MAX_TRAVERSES = "maxTraverses";

  public static final String CONSUME_MESSAGES_AT_CLOSE = "consumeMessagesAtClose";

  public static final String MAX_CLOSE_WAITING_TIME = "maxCloseWaitingTime";

  public static final String TIMEZONE = "timezone";

  // Default values
  public static final String DEFAULT_HOST = "localhost";

  public static final Integer DEFAULT_PORT = 12340;

  public static final Integer DEFAULT_CONNECTION_TIMEOUT = 5000;

  public static final Integer DEFAULT_RECONNECT_ATTEMPTS = 3;

  public static final Integer DEFAULT_POP_SIZE = 10;

  public static final Integer DEFAULT_PUSH_INTERVAL = 2000;

  public static final Integer DEFAULT_MAX_TRAVERSES = 20;

  public static final Boolean DEFAULT_CONSUME_MESSAGES_AT_CLOSE = true;

  public static final Integer DEFAULT_MAX_CLOSE_WAITING_TIME = 5000;

  public static final String DEFAULT_TIMEZONE = ZoneId.systemDefault().toString();


  public TcpConfiguration() {
    this.setHost(DEFAULT_HOST)
        .setPort(DEFAULT_PORT)
        .setConnectionTimeout(DEFAULT_CONNECTION_TIMEOUT)
        .setReconnectAttempts(DEFAULT_RECONNECT_ATTEMPTS)
        .setPopSize(DEFAULT_POP_SIZE)
        .setPushInterval(DEFAULT_PUSH_INTERVAL)
        .setMaxTraverses(DEFAULT_MAX_TRAVERSES)
        .setConsumeMessagesAtClose(DEFAULT_CONSUME_MESSAGES_AT_CLOSE)
        .setMaxCloseWaitingTime(DEFAULT_MAX_CLOSE_WAITING_TIME)
        .setTimezone(DEFAULT_TIMEZONE);
  }


  public TcpConfiguration(Configuration configuration) {
    super(configuration);
    this.merge(configuration);
  }


  public TcpConfiguration setHost(String host) {
    put(HOST, host);
    return this;
  }


  public TcpConfiguration setPort(int port) {
    put(PORT, port);
    return this;
  }


  public TcpConfiguration setConnectionTimeout(int timeout) {
    put(CONNECTION_TIMEOUT, timeout);
    return this;
  }


  public TcpConfiguration setReconnectAttempts(int reconnectAttempts) {
    put(RECONNECT_ATTEMPTS, reconnectAttempts);
    return this;
  }


  public TcpConfiguration setPopSize(int popSize) {
    put(POP_SIZE, popSize);
    return this;
  }


  public TcpConfiguration setPushInterval(int pushInterval) {
    put(PUSH_INTERVAL, pushInterval);
    return this;
  }


  public TcpConfiguration setMaxTraverses(int maxTraverses) {
    put(MAX_TRAVERSES, maxTraverses);
    return this;
  }


  public TcpConfiguration setConsumeMessagesAtClose(boolean consumeMessagesAtClose) {
    put(CONSUME_MESSAGES_AT_CLOSE, consumeMessagesAtClose);
    return this;
  }


  public TcpConfiguration setMaxCloseWaitingTime(int maxCloseWaitingTime) {
    put(MAX_CLOSE_WAITING_TIME, maxCloseWaitingTime);
    return this;
  }


  public TcpConfiguration setTimezone(String timezone) {
    put(TIMEZONE, timezone);
    return this;
  }
}
