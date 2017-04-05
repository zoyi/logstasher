package com.zoyi.logstasher.util.annotation;

/**
 * Created by lou on 2017-04-05 14:55
 */
public enum Name {
  EMPTY(""),
  TCP("tcp");

  private final String name;

  Name(final String name) {
    this.name = name;
  }

  public String getName() { return this.name; }
}
