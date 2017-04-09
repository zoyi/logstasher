package com.zoyi.logstasher;

import com.zoyi.logstasher.configuration.Configuration;
import com.zoyi.logstasher.configuration.ConfigurationImpl;
import com.zoyi.logstasher.message.JsonMessage;
import com.zoyi.logstasher.message.Message;
import com.zoyi.logstasher.output.tcp.TcpLogstasherImpl;

import java.util.Scanner;

import static com.zoyi.logstasher.output.tcp.TcpLogstasherConfigs.*;


/**
 * Created by lloyd on 2017-04-06
 */
public class CliTest {
  public static void main(String[] args) throws Exception {
    final Logstasher logstasher = new TcpLogstasherImpl();

    final Configuration configuration = new ConfigurationImpl();
    configuration.put(HOST, "localhost");
    configuration.put(PORT, 12340);
    configuration.put(CONNECTION_TIMEOUT, 1000);
    configuration.put(RECONNECT_ATTEMPTS, 1);
    configuration.put(POP_SIZE, 10);
    configuration.put(MAX_TRAVERSES, 20);

    // localhost:12340
    logstasher.initialize(configuration);

    final Scanner scanner = new Scanner(System.in);
    String line;

    ReadLine:
    while ((line=scanner.nextLine()) != null) {
      if (!line.isEmpty()) {
        switch (line) {
          case "close": {
            logstasher.close();
            break;
          }

          case "init": {
            logstasher.initialize(configuration);
            break;
          }

          case "exit": {
            logstasher.close();
            break ReadLine;
          }

          default: {
            final Message data = new JsonMessage();
            data.put("@defaultMessage", "Default Message Provided!");

            try {
              final String[] tokens = line.split(",\\s*");
              for (String t : tokens) {
                final String[] tk = t.split(":");
                data.put(tk[0], tk[1]);
              }
            } catch (Exception e) {
              e.printStackTrace();
            }

            try {
              logstasher.put(data);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      }
    }
  }
}
