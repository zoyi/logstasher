package com.zoyi.logstasher.output.tcp;

import com.zoyi.logstasher.Logstasher;
import com.zoyi.logstasher.util.MockServer;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by lloyd on 2017-04-04
 */
public class TcpLogstasherImplTest {
  private MockServer mockServer;
  private Logstasher logstasher;


  @Before
  public void initialize() {
    logstasher = new TcpLogstasherImpl();

    // localhost:12340
    logstasher.initialize();

//    mockServer = new MockServer(12340);
  }


  @Test
  public void testConnectAndPush() {
    final Map<String, Object> data1 = new HashMap<>();
    data1.put("foo", "bar");

    final Map<String, Object> data2 = new HashMap<>();
    data2.put("numbers", 1000000);

    final Map<String, Object> data3 = new HashMap<>();
    data3.put("Hello", "World");
    //data3.put("Floating", -0.12f);
    data3.put("Long", 1234567890);

    final Map<String, Object> data4 = new HashMap<>();

    logstasher.put(data1);
    logstasher.put(data2);
    logstasher.put(data3);
    logstasher.put(data4);
  }
}
