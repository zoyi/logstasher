package com.zoyi.logstasher.configuration;

import com.zoyi.logstasher.util.StringUtil;

/**
 * @author Loustler(Dongyeon Lee)
 * @since 2017-04-07
 */
public class ConfigurationHelper {
  /**
   * Merge two configuration.
   *
   * If they had same specified key, associated value overwrite with newConfiguration's value.
   *
   * If oldConfiguration's key is null, not merge.
   *
   * <pre>{@code
   *  Configuration baseConf = new ConfigurationImpl();
   *  oldConf.put(null, "null value"); // It will ignore by put
   *  oldConf.put("", "empty value"); // It will ignore by put
   *  oldConf.put("foo", "bar");
   *  oldConf.put("hello", "world");
   *
   *  Configuration newConf = new ConfigurationImpl();
   *  newConf.put("foo", "barr");
   *  newConf.put("hello", "world!");
   *
   *  Configuration mergedConf = ConfigurationImple.merge(baseConf, newConf);
   *  mergedConf.getString(null);    // It will return null
   *  mergedConf.getString("");      // It will return null
   *  mergedConf.getString("foo");   // It will return barr
   *  mergedConf.getString("hello"); // It will return world!
   * }</pre>
   *
   * @param oldConfiguration The old configuration will merge.
   * @param newConfiguration The new configuration will merge.
   * @return Merged configuration.
   */
  // TODO: I think this method have to be moved into Configuration class, not in helper class
  public static Configuration merge(
    final Configuration oldConfiguration,
    final Configuration newConfiguration
  ) {
    newConfiguration.entrySet()
                    .stream()
                    .filter(e -> StringUtil.isNotNullOrEmpty(e.getKey()))
                    .forEach(e -> {
                      oldConfiguration.putEntry(e);
                    });

    return oldConfiguration;
  }
}
