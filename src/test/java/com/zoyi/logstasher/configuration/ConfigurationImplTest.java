package com.zoyi.logstasher.configuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by lou on 2017-04-05 16:06
 */
public class ConfigurationImplTest {
  private ConfigurationImpl conf;

  @Before
  public void setup() {
    conf = new ConfigurationImpl();
  }

  @After
  public void cleanup() {
    conf.clear();
  }


  @Test
  public void shouldTwoConfigurationMerged() {
    final String key1 = "foo";
    final String key2 = "year";
    final String key3 = "hello";
    final String value1 = "bar";
    final Integer newValue2 = 2017;
    final String newValue3 = "java";

    conf.put(key1, value1)
        .put("", "empty string") // ignore
        .put(null, "null value") // ignore
        .put(key2, 2016)
        .put(key3, "world");

    final Configuration newConf = new ConfigurationImpl().put(key2, newValue2)
                                                         .put(key3, newValue3);

    Configuration mergedConf = ConfigurationHelper.merge(conf, newConf);

    assertThat(mergedConf).isNotEmpty();
    assertThat(mergedConf.size()).isEqualTo(3);
    assertThat(mergedConf.getString(key1)).isEqualTo(value1);
    assertThat(mergedConf.getInteger(key2)).isEqualTo(newValue2);
    assertThat(mergedConf.getString(key3)).isEqualTo(newValue3);
  }


  @Test
  public void testGetBoolean() {
    
  }


  @Test
  public void shouldGetDefaultValue() {
    final String defaultValue1 = "default";
    final Integer defaultValue2 = 55;
    final Long defaultValue3 = 34234L;

    assertThat(conf.getString("foo", defaultValue1)).isEqualTo(defaultValue1);
    assertThat(conf.getInteger("bar", defaultValue2)).isEqualTo(defaultValue2);
    assertThat(conf.getLong("hello world", defaultValue3)).isEqualTo(defaultValue3);
  }


  @Test
  public void shouldValueIsBoolean() {
    final String key = "boolean";
    conf.put(key, true);

    final Boolean result = conf.getBoolean(key, false);

    assertThat(result).isNotNull();
    assertThat(result).isTrue();
  }


  @Test
  public void shouldValueIsByte() {
    final String key = "byte";
    final Integer number = 100;
    final Byte value = Byte.valueOf(number.toString(), 2);

    conf.put(key, value);

    final Byte result = conf.getByte(key, null);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(value);
  }


  @Test
  public void shouldValueIsCharacter() {
    final String key = "character";
    final Character value = 'c';

    conf.put(key, value);

    final Character result = conf.getCharacter(key, null);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(value);
  }


  @Test
  public void shouldValueIsShort() {
    final String key = "short";
    final Short value = 10;

    conf.put(key, value);

    final Short result = conf.getShort(key, null);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(value);
  }


  @Test
  public void shouldValueIsFloat() {
    final String key = "float";
    final Float value = 100.0f;

    conf.put(key ,value);

    final Float result = conf.getFloat(key, null);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(value);
  }


  @Test
  public void shouldValueIsInteger() {
    final String key = "integer";
    final Integer value = 555;

    conf.put(key, value);

    final Integer result = conf.getInteger(key, null);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(value);
  }


  @Test
  public void shouldValueIsLong() {
    final String key = "long";
    final Long value = 192035L;

    conf.put(key, value);

    final Long result = conf.getLong(key, null);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(value);
  }

  @Test
  public void shouldValueIsDouble() {
    final String key = "double";
    final Double value = 1523.234234d;

    conf.put(key, value);

    final Double result = conf.getDouble(key, null);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(value);
  }


  @Test
  public void shouldValueString() {
    final String key = "string";
    final String value = "for test";

    conf.put(key, value);

    final String result = conf.getString(key, null);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(value);
  }

  @Test
  public void shouldSameConf() {
    ConfigurationImpl configuration = new ConfigurationImpl();

    final String key1 = "str";        final String value1 = "for test";
    final String key2 = "double";     final Double value2 = 35.2323d;
    final String key3 = "long";       final Long value3 = 2342342L;
    final String key4 = "integer";    final Integer value4 = 35;
    final String key5 = "character";  final Character value5 = 'c';

    configuration.put(key1, value1)
                 .put(key2, value2)
                 .put(key3, value3)
                 .put(key4, value4)
                 .put(key5, value5);

    ConfigurationImpl conf = new ConfigurationImpl();

    conf.putAll(configuration);

    assertThat(conf).isNotEmpty();
    assertThat(conf.getString(key1, null)).isEqualTo(value1);
    assertThat(conf.getDouble(key2, null)).isEqualTo(value2);
    assertThat(conf.getLong(key3, null)).isEqualTo(value3);
    assertThat(conf.getInteger(key4, null)).isEqualTo(value4);
    assertThat(conf.getCharacter(key5, null)).isEqualTo(value5);
  }
}
