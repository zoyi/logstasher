package com.zoyi.logstasher;

import com.zoyi.logstasher.util.annotation.Name;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by lou on 2017-04-05 12:36
 */
public class LogStasherTest {
  @Test
  public void shouldBeLogStasherNotNull() {
    Logstasher logstasher = Logstashers.newLogstasher(Name.TCP.getName());

    assertThat(logstasher).isNotNull();
  }
}
