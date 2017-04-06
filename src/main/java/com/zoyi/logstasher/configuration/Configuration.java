package com.zoyi.logstasher.configuration;

import com.zoyi.logstasher.util.Tuple;

import java.util.Map;

/**
 * @author Junbong
 * @since 2017-04-04
 */
public interface Configuration extends Map<String, Tuple> {
  // TODO: method without default value
  Boolean getBoolean(final String key, final Boolean defaultValue);

  Byte getByte(final String key, final Byte defaultValue);

  Character getCharacter(final String key, final Character defaultValue);

  Short getShort(final String key, final Short defaultValue);

  Float getFloat(final String key, final Float defaultValue);

  Integer getInteger(final String key, final Integer defaultValue);

  Long getLong(final String key, final Long defaultValue);

  Double getDouble(final String key, final Double defaultValue);

  String getString(final String key, final String defaultValue);

  Configuration put(final String key, final Boolean value);

  Configuration put(final String key, final Byte value);

  Configuration put(final String key, final Character value);

  Configuration put(final String key, final Short value);

  Configuration put(final String key, final Float value);

  Configuration put(final String key, final Integer value);

  Configuration put(final String key, final Long value);

  Configuration put(final String key, final Double value);

  Configuration put(final String key, final String value);
}
