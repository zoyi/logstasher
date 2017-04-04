package com.zoyi.logstasher.tcp;

import com.zoyi.logstasher.Configuration;
import com.zoyi.logstasher.Logstasher;
import com.zoyi.logstasher.Message;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;


/**
 * Created by lloyd on 2017-04-04
 */
public class TcpLogstasherImpl implements Logstasher {
  private NetSocket socket;


  @Override
  public void initialize(Configuration configuration) {
    final NetClientOptions options = new NetClientOptions();
    options.setConnectTimeout(1000);

    Vertx.vertx()
         .createNetClient(options)
         .connect(5432, "", result -> {
           if (result.succeeded()) {
            socket = result.result();
           } else {
             throw new RuntimeException(result.cause());
           }
         });
  }


  @Override
  public void close() throws Exception {
    // TODO: handle messages which remained in queue

    // Disconnect socket
    socket.close();
  }


  @Override
  public void put(Message<?> message) {

  }


  private void push() {
    socket.write("");
  }
}
