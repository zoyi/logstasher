package com.zoyi.logstasher;

import com.zoyi.logstasher.configuration.Configuration;
import com.zoyi.logstasher.configuration.ConfigurationImpl;
import com.zoyi.logstasher.util.annotation.Stasher;

import java.util.Objects;

import static com.zoyi.logstasher.util.annotation.processor.StasherAnnotationProcessor.STASHER_PROCESSOR;

/**
 * @author Junbong
 * @author Loustler(Dongyeon Lee)
 * @since 2017-04-04
 */
public final class Logstashers {
  /**
   * Create Logstasher using name.
   * <p>
   * Require configuration is not null.
   * <p>
   * When Configuration is {@code null}, throws {@link NullPointerException}.
   *
   * @param name          The name is {@link Stasher#name()} or {@link Class#getSimpleName()}
   * @param configuration The configuration configure to Logstasher.
   * @return The new Logstasher.
   * @throws NullPointerException When specified configuration is {@code null}.
   * @see Configuration
   */
  public static Logstasher create(
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
   * Create Logstasher using name.
   * <p>
   *  Call {@link #create(String, Configuration)} with empty configuration as parameter.
   * </p>
   *
   * @see Configuration
   * @param name  The name is {@link Stasher#name()} or {@link Class#getSimpleName()}
   * @return The new Logstasher.
   */
  public static Logstasher create(final String name) {
    return create(name, new ConfigurationImpl());
  }
}