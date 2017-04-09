package com.zoyi.logstasher.output.tcp;

/**
 * @author Junbong
 */
public class TcpLogstasherConfigs {
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
}
