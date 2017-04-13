package com.zoyi.logstasher;

import com.zoyi.logstasher.configuration.Configuration;
import com.zoyi.logstasher.message.JsonMessage;
import com.zoyi.logstasher.message.Message;
import com.zoyi.logstasher.output.tcp.TcpConfiguration;
import com.zoyi.logstasher.output.tcp.TcpLogstasherImpl;
import io.vertx.core.json.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.ZoneId;
import java.util.Scanner;

import static com.zoyi.logstasher.util.StringUtil.isNotNullOrEmpty;


/**
 * Created by lloyd on 2017-04-06
 */
public class CliClient {
  private static final Logger log = LogManager.getLogger(CliClient.class);


  public static void main(String[] args) throws Exception {
    final Logstasher logstasher = new TcpLogstasherImpl();

    final String host =
        (args.length > 0 && isNotNullOrEmpty(args[0])) ? args[0] : "localhost";
    final String port =
        (args.length > 1 && isNotNullOrEmpty(args[1])) ? args[1] : "12340";
    final String timezone =
        (args.length > 2 && isNotNullOrEmpty(args[2])) ? args[2] : ZoneId.systemDefault().toString();

    final Configuration configuration =
        new TcpConfiguration().setHost(host)
                              .setPort(Integer.parseInt(port))
                              .setPushInterval(1000)
                              .setTimezone(timezone);

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
    }
  }
}
