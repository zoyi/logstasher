package com.zoyi.logstasher.util;

import io.vertx.core.Vertx;

import java.time.LocalDateTime;


/**
 * Created by lloyd on 2017-04-04
 */
public class MockServer {
  public static void main(String[] args) {
    MockServer mockServer = new MockServer(12340);
    mockServer.start();
  }


  private final int port;


  public MockServer(int port) {
    this.port = port;
  }


  public void start() {
    Vertx.vertx()
         .createNetServer()
         .connectHandler(socket -> {
           System.out.println(
               String.format("[MockServer] %s  New connection established", LocalDateTime.now()));

           socket.handler(buffer -> {
             System.out.println(String.format("[MockServer] %s  %d\t%s",
                 LocalDateTime.now(), buffer.length(), buffer.toString()));
           });
         })
         .listen(port, event -> {
           if (event.succeeded()) {
             System.out.println("MockServer now listening on port " + port);
           }
         });
  }
}
