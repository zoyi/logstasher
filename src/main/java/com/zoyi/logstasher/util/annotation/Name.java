package com.zoyi.logstasher.util.annotation;

/**
 * @author Loustler(Dongyeon Lee)
 * @since 2017-04-05
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
