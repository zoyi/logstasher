package com.zoyi.logstasher.util;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by lou on 2017-04-05 14:21
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


  /**
   * Do something when string is not null or empty and empty or null.
   * If string is not null or empty, then do first consumer.
   * If string is null or empty, then do last consumer.
   * Each situation have consumer, you will handling situation via each consumer.
   *
   * @param str The string to test.
   * @param notNullConsumer Do something when string is not null or empty.
   * @param nullConsumer Do something when string is null or empty.
   */
  public static void ifNotNullOrEmptyThen(
    final String str,
    final Consumer<String> notNullConsumer,
    final Consumer<String> nullConsumer
  ) {
    if (isNotNullOrEmpty(str))
      notNullConsumer.accept(str);
    else
      nullConsumer.accept(str);
  }
}
