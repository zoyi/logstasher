package com.zoyi.logstasher;

import com.zoyi.logstasher.configuration.Configuration;
import com.zoyi.logstasher.configuration.ConfigurationImpl;
import com.zoyi.logstasher.output.tcp.TcpLogstasherImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * Created by lloyd on 2017-04-06
 */
public class TestByKeyInput {
  public static void main(String[] args) {
    final Logstasher logstasher = new TcpLogstasherImpl();

    final Configuration configuration = new ConfigurationImpl();
    configuration.put("popSize", 10);
    configuration.put("maxTraverses", 20);
    configuration.put("connectionTimeout", 1000);
    configuration.put("reconnectAttempts", 1);
    configuration.put("host", "localhost");
    configuration.put("port", 12340);

    // localhost:12340
    logstasher.initialize(configuration);

    final Scanner scanner = new Scanner(System.in);
    String line;

    while ((line=scanner.nextLine()) != null) {
      if (!line.isEmpty()) {
        final Map<String, Object> data = new HashMap<>();
        data.put("@default", "default value");

        try {
          final String[] tokens = line.split(",\\s*");
          for (String t : tokens) {
            final String[] tk = t.split(":");
            data.put(tk[0], tk[1]);
          }
        } catch (Exception e) {}

        try {
          logstasher.put(data);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
