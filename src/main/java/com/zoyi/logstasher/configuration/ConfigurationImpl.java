package com.zoyi.logstasher.configuration;

import com.zoyi.logstasher.util.Tuple;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by lou on 2017-04-05 15:04
 */
public class ConfigurationImpl implements Configuration {
  private final Map<String, Tuple> conf = new HashMap<>();


  public ConfigurationImpl() {}


  @Override
  public Boolean getBoolean(final String key, final Boolean defaultValue) {
    return getOrDefault0(key, defaultValue);
  }

  @Override
  public Byte getByte(final String key, final Byte defaultValue) {
    return getOrDefault0(key , defaultValue);
  }


  @Override
  public Character getCharacter(final String key, final Character defaultValue) {
    return getOrDefault0(key, defaultValue);
  }


  @Override
  public Short getShort(final String key, final Short defaultValue) {
    return getOrDefault0(key, defaultValue);
  }


  @Override
  public Float getFloat(final String key, final Float defaultValue) {
    return getOrDefault0(key, defaultValue);
  }


  @Override
  public Integer getInteger(final String key, final Integer defaultValue) {
    return getOrDefault0(key, defaultValue);
  }


  @Override
  public Long getLong(final String key, final Long defaultValue) {
    return getOrDefault0(key, defaultValue);
  }


  @Override
  public Double getDouble(final String key, final Double defaultValue) {
    return getOrDefault0(key, defaultValue);
  }


  @Override
  public String getString(final String key, final String defaultValue) {
    return getOrDefault0(key, defaultValue);
  }


  @Override
  public Configuration put(final String key, final Boolean value) {
    return put0(key, value);
  }


  @Override
  public Configuration put(String key, Byte value) {
    return put0(key, value);
  }


  @Override
  public Configuration put(final String key, final Character value) {
    return put0(key, value);
  }


  @Override
  public Configuration put(final String key, final Short value) {
    return put0(key, value);
  }


  @Override
  public Configuration put(final String key, final Float value) {
    return put0(key, value);
  }


  @Override
  public Configuration put(final String key, final Integer value) {
    return put0(key, value);
  }


  @Override
  public Configuration put(final String key, final Long value) {
    return put0(key, value);
  }


  @Override
  public Configuration put(final String key, final Double value) {
    return put0(key, value);
  }


  @Override
  public Configuration put(final String key, final String value) {
    return put0(key, value);
  }


  @Override
  public int size() {
    return conf.size();
  }


  @Override
  public boolean isEmpty() {
    return conf.isEmpty();
  }


  @Override
  public boolean containsKey(final Object key) {
    return conf.containsKey(key);
  }


  @Override
  public boolean containsValue(final Object value) {
    return conf.containsValue(value);
  }


  @Override
  public Tuple get(final Object key) {
    return conf.get(key);
  }


  @Override
  public Tuple put(final String key, final Tuple value) {
    return conf.put(key, value);
  }


  @Override
  public Tuple remove(final Object key) {
    return conf.remove(key);
  }


  @Override
  public void putAll(
    final Map<? extends String, ? extends Tuple> m
  ) {
    conf.putAll(m);
  }


  @Override
  public void clear() {
    conf.clear();
  }


  @Override
  public Set<String> keySet() {
    return conf.keySet();
  }


  @Override
  public Collection<Tuple> values() {
    return conf.values();
  }


  @Override
  public Set<Entry<String, Tuple>> entrySet() {
    return conf.entrySet();
  }


  private <V> V getOrDefault0(final Object key, final V v) {
    Tuple<V> tuple;

    return (Objects.nonNull((tuple = ((Tuple<V>)conf.get(key)))) || containsKey(key))
           ? tuple.getValue()
           : v;
  }


  private <V> Configuration put0(final String key, final V v) {
    conf.put(key, Tuple.mkTuple(v));

    return this;
  }
}
