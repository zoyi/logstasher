package com.zoyi.logstasher.util;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author Loustler(Dongyeon Lee)
 * @since 2017-04-05
 */
public class StringUtil {
  private static final Predicate<String> NOT_NULL_OR_EMPTY = str -> Objects.nonNull(str)
                                                                    && !str.isEmpty();

  /**
   * Returns {@code true} when specified string is <i>NOT</i> {@code null} and empty.
   * Otherwise returns {@code false}.
   *
   * @param str string to test
   * @return {@code true} when string is not {@code null} and empty, otherwise {@code false}
   */
  public static boolean isNotNullOrEmpty(final String str) {
    return NOT_NULL_OR_EMPTY.test(str);
  }


  /**
   * Returns {@code true} when specified string is {@code null} or empty.
   * Otherwise returns {@code false}.
   *
   * @param str string to test
   * @return {@code true} when string is {@code null} or empty, otherwise {@code false}
   */
  public static boolean isNullOrEmpty(final String str) { return !isNotNullOrEmpty(str); }
}
