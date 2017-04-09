package com.zoyi.logstasher.output;

import com.zoyi.logstasher.Logstasher;

import java.time.LocalDateTime;


/**
 * @author Junbong
 */
public abstract class BaseLogstasherImpl implements Logstasher {
  /**
   * Print specified message through STDOUT console.
   * This method functions for debugging situation.
   *
   * @param message Something to print.
   */
  protected void printStdOut(String message) {
    System.out.println(String.format("%s\t%s\t%s",
        LocalDateTime.now(), this.getClass().getSimpleName(), message));
  }


  protected abstract void push(String type, int count, int maxTraverses) throws Exception;
}
