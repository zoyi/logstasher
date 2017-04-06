package com.zoyi.logstasher.output.tcp;

import com.zoyi.logstasher.Logstasher;
import com.zoyi.logstasher.configuration.Configuration;
import com.zoyi.logstasher.configuration.ConfigurationImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by lloyd on 2017-04-04
 */
public class TcpLogstasherImplTest {
  private Logstasher logstasher;


  @Before
  public void initialize() {
    logstasher = new TcpLogstasherImpl();

    final Configuration configuration = new ConfigurationImpl();
    configuration.put("popSize", 1);
    configuration.put("connectionTimeout", 1000);
    configuration.put("reconnectAttempts", 1);
    configuration.put("host", "localhost");
    configuration.put("port", 12340);
    configuration.put("maxTraverses", 2);

    // localhost:12340
    logstasher.initialize(configuration);
  }


  @Test
  public void testConnectAndPush() {
    final ScheduledExecutorService scheduler =
        Executors.newScheduledThreadPool(3);

    final Map<String, Object> data0 = new HashMap<>();
    final Map<String, Object> nestedBody = new HashMap<>();
    nestedBody.put("title", "Star Light");
    nestedBody.put("Content", "I count every single star in the indigo sky.");
    data0.put("nested_body", nestedBody);
    logstasher.put(data0);

    scheduler.schedule(() -> {
      final Map<String, Object> data1 = new HashMap<>();
      data1.put("@foo", "bar");
      logstasher.put(data1);
    }, 1, TimeUnit.SECONDS);

    scheduler.schedule(() -> {
      final Map<String, Object> data2 = new HashMap<>();
      data2.put("@numbers", 1000000);

      final Map<String, Object> data3 = new HashMap<>();
      data3.put("@hello", "World");
      data3.put("@floating", -0.12f);
      data3.put("@long", 1234567890);
      logstasher.put(data2);
      logstasher.put(data3);
    }, 4, TimeUnit.SECONDS);

    scheduler.schedule(() -> {
      final Map<String, Object> data4 = new HashMap<>();
      logstasher.put(data4);
    }, 8, TimeUnit.SECONDS);


    scheduler.schedule(scheduler::shutdown, 10, TimeUnit.SECONDS);
  }
}
