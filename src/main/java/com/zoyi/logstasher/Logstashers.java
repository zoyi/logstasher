package com.zoyi.logstasher;

import com.zoyi.logstasher.configuration.Configuration;
import com.zoyi.logstasher.configuration.ConfigurationImpl;
import com.zoyi.logstasher.util.annotation.Stasher;

import java.util.Objects;

import static com.zoyi.logstasher.util.annotation.processor.StasherProcessor.STASHER_PROCESSOR;

/**
 * @author Junbong
 * @author Loustler(Dongyeon Lee)
 * @since 2017-04-04
 */
public final class Logstashers {
  /**
   * Get Logstasher using name.
   *
   * Require configuration is not null.
   *
   * When Configuration is null, throws NullPointerException.
   *
   * @see Configuration
   * @see Stasher#name()
   * @see Class#getSimpleName()
   * @see com.zoyi.logstasher.util.annotation.Name
   * @param name The name is Stasher#name() or Class#simpleName()
   * @param configuration The configuration configure to Logstasher.
   * @return The new Logstasher.
   * @throws NullPointerException
   */
  public static Logstasher newLogstasher(
      final String name,
      final Configuration configuration
  ) {
    Objects.requireNonNull(configuration, "Configuration not null.");

    Class<?> klass = STASHER_PROCESSOR.getClass(name);
    Logstasher instance = null;

    try {
      instance = (Logstasher)klass.cast(klass.newInstance());
      instance.initialize(configuration);
    } catch (IllegalAccessException | InstantiationException e) {
      throw new RuntimeException("Cannot create instance", e);
    }

    return instance;
  }


  /**
   * Get Logstasher using name.
   *
   * The Configuration to be empty configuration.
   *
   * @see Configuration
   * @see Stasher#name()
   * @see com.zoyi.logstasher.util.annotation.Name
   * @see Class#getSimpleName()
   * @param name The name is Stasher#name() or Class#simpleName()
   * @return The new Logstasher.
   */
  public static Logstasher newLogstasher(final String name) {
    return newLogstasher(name, new ConfigurationImpl());
  }
}