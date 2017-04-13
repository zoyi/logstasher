package com.zoyi.logstasher;

import com.zoyi.logstasher.configuration.Configuration;
import com.zoyi.logstasher.message.JsonMessage;
import com.zoyi.logstasher.message.Message;
import com.zoyi.logstasher.output.tcp.TcpConfiguration;
import com.zoyi.logstasher.output.tcp.TcpLogstasherImpl;
import com.zoyi.logstasher.util.ArgumentParser;
import io.vertx.core.json.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.ZoneId;
import java.util.Map;
import java.util.Scanner;


/**
 * Simple CLI client for test purpose.
 * This client takes some arguments as UNIX-like phrase.
 *
 * @author Junbong
 * @version 1
 * @since 0.1.4
 */
public class CliClient {
  private static final Logger log = LogManager.getLogger(CliClient.class);
  private static final String prompt = "Message> ";


  public static void main(String[] args) throws Exception {
    final Logstasher logstasher = new TcpLogstasherImpl();

    final Map<String, Object> arguments = ArgumentParser.parse(args);

    final String host = (String) arguments.getOrDefault("host", "localhost");
    final Integer port = (Integer) arguments.getOrDefault("port", 12340);
    final String timezone = (String) arguments.getOrDefault("timezone", ZoneId.systemDefault().toString());
    final Integer pushInterval = (Integer) arguments.getOrDefault("pushInterval", 2000);

    final Configuration configuration =
        new TcpConfiguration().setHost(host)
                              .setPort(port)
                              .setPushInterval(pushInterval)
                              .setTimezone(timezone);

    // localhost:12340
    logstasher.initialize(configuration);

    final Scanner scanner = new Scanner(System.in);
    String line;

    System.out.println("Logstasher CLI Client");
    System.out.print(prompt);

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
            try {
              final JsonObject jsonInput = new JsonObject(line);
              final Message data = new JsonMessage(jsonInput);

              try {
                logstasher.put(data);
              } catch (Exception e) {
                log.error("Log putting error");
              }

            } catch (Exception e) {
              log.error("Input parsing error", e);
            }
          }
        }
      }

      System.out.print(prompt);
    }
  }
}
