package com.zoyi.logstasher.util.annotation.processor;

import com.zoyi.logstasher.output.tcp.TcpLogstasherImpl;
import com.zoyi.logstasher.util.annotation.Name;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class StasherAnnotationProcessorTest {
  @Test
  public void shouldClassIsNotEmpty() throws Exception {
    final String clazzName = Name.TCP.getName();
    Class<?> clazz = StasherAnnotationProcessor.STASHER_PROCESSOR.getClass(clazzName);

    assertNotNull(clazz);
    assertTrue(TcpLogstasherImpl.class.isAssignableFrom(clazz));
  }
}