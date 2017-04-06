package com.zoyi.logstasher.util.annotation.processor;

import com.zoyi.logstasher.util.annotation.Name;
import org.junit.Test;

/**
 * Created by lou on 2017-04-04 16:16
 */
public class StasherProcessorTest {
  @Test
  public void shouldClassIsNotEmpty() throws Exception {
    final String clazzName = Name.TCP.getName();
    Class<?> clazz = StasherProcessor.STASHER_PROCESSOR.getClass(clazzName);
    System.out.println(clazz);
  }
}