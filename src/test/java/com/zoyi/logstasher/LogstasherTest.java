package com.zoyi.logstasher;

import com.zoyi.logstasher.output.tcp.TcpLogstasherImpl;
import com.zoyi.logstasher.util.annotation.Name;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LogstasherTest {
  @Test
  public void shouldBeLogStasherNotNull() {
    Logstasher logstasher = Logstashers.create(Name.TCP.getName());

    assertNotNull(logstasher);
    assertTrue(TcpLogstasherImpl.class.isAssignableFrom(logstasher.getClass()));
  }
}
