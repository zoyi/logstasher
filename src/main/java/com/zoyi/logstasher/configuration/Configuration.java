package com.zoyi.logstasher.configuration;

import com.zoyi.logstasher.util.StringUtil;
import com.zoyi.logstasher.util.Tuple;

import java.util.Map;
import java.util.Objects;

/**
 * @author Junbong
 * @author Loustler(Dongyeon Lee)
 * @since 2017-04-04
 */
public interface Configuration extends Map<String, Tuple> {
  Boolean getBoolean(final String key, final Boolean defaultValue);

  Boolean getBoolean(final String key);

  Byte getByte(final String key, final Byte defaultValue);

  Byte getByte(final String key);

  Character getCharacter(final String key, final Character defaultValue);

  Character getCharacter(final String key);

  Short getShort(final String key, final Short defaultValue);

  Short getShort(final String key);

  Float getFloat(final String key, final Float defaultValue);

  Float getFloat(final String key);

  Integer getInteger(final String key, final Integer defaultValue);

  Integer getInteger(final String key);

  Long getLong(final String key, final Long defaultValue);

  Long getLong(final String key);

  Double getDouble(final String key, final Double defaultValue);

  Double getDouble(final String key);

  String getString(final String key, final String defaultValue);

  String getString(final String key);

  /**
   * Put data into map using key.
   *
   * If key is null or empty string, not perform.
   *
   * @apiNote Key must not be null or empty string.
   * @param key The key to be as key to map.
   * @param value The value to set into map.
   * @return The configuration.
   */
  Configuration put(final String key, final Boolean value);

  /**
   * Work just like {@link Configuration#put(String, Boolean)}
   *
   * @see Configuration#put(String, Boolean)
   */
  Configuration put(final String key, final Byte value);

  /**
   * Work just like {@link Configuration#put(String, Boolean)}
   *
   * @see Configuration#put(String, Boolean)
   */
  Configuration put(final String key, final Character value);

  /**
   * Work just like {@link Configuration#put(String, Boolean)}
   *
   * @see Configuration#put(String, Boolean)
   */
  Configuration put(final String key, final Short value);

  /**
   * Work just like {@link Configuration#put(String, Boolean)}
   *
   * @see Configuration#put(String, Boolean)
   */
  Configuration put(final String key, final Float value);

  /**
   * Work just like {@link Configuration#put(String, Boolean)}
   *
   * @see Configuration#put(String, Boolean)
   */
  Configuration put(final String key, final Integer value);

  /**
   * Work just like {@link Configuration#put(String, Boolean)}
   *
   * @see Configuration#put(String, Boolean)
   */
  Configuration put(final String key, final Long value);

  /**
   * Work just like {@link Configuration#put(String, Boolean)}
   *
   * @see Configuration#put(String, Boolean)
   */
  Configuration put(final String key, final Double value);

  /**
   * Work just like {@link Configuration#put(String, Boolean)}
   *
   * @see Configuration#put(String, Boolean)
   */
  Configuration put(final String key, final String value);


  /**
   * Put entry to configuration.
   *
   * If entry or key of entry is null, then will not perform.
   *
   * @param entry The entry to put into map.
   * @return The Configuration.
   */
  default Configuration putEntry(final Configuration.Entry<String, Tuple> entry) {
    if (Objects.nonNull(entry) && StringUtil.isNotNullOrEmpty(entry.getKey()))
      this.put(entry.getKey(), entry.getValue());

    return this;
  }


  /**
   * Merge another configuration to this.
   *
   * If they had same specified key, associated value overwrite with newConfiguration's value.
   *
   * If this configuration key is null, not merge.
   *
   * <pre>{@code
   *  Configuration currentConf = new ConfigurationImpl();
   *  currentConf.put(null, "null value"); // It will ignore by put
   *  currentConf.put("", "empty value"); // It will ignore by put
   *  currentConf.put("foo", "bar");
   *  currentConf.put("hello", "world");
   *
   *  Configuration newConf = new ConfigurationImpl();
   *  newConf.put("foo", "barr");
   *  newConf.put("hello", "world!");
   *
   *  Configuration mergedConf = currentConf.merge(baseConf, newConf);
   *  mergedConf.getString(null);    // It will return null
   *  mergedConf.getString("");      // It will return null
   *  mergedConf.getString("foo");   // It will return barr
   *  mergedConf.getString("hello"); // It will return world!
   * }</pre>
   *
   * @param newConfiguration The new configuration will merge with this.
   * @return Merged configuration.
   */
  default Configuration merge(
    final Configuration newConfiguration
  ) {
    for (Entry<String, Tuple> e : newConfiguration.entrySet()) {
      if (StringUtil.isNotNullOrEmpty(e.getKey())) {
        putEntry(e);
      }
    }

    return this;
  }
}
