package com.zoyi.logstasher.configuration;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by lou on 2017-04-05 16:06
 */
public class ConfigurationImplTest {
  @Test
  public void shouldTwoConfigurationMerged() {
    final String key1 = "foo";
    final String key2 = "year";
    final String key3 = "hello";
    final String value1 = "bar";
    final Integer newValue2 = 2017;
    final String newValue3 = "java";

    final Configuration currentConf = of().put(key1, value1)
        .put("", "empty string") // ignore
        .put(null, "null value") // ignore
        .put(key2, 2016)
        .put(key3, "world");

    final Configuration newConf = new ConfigurationImpl().put(key2, newValue2)
                                                         .put(key3, newValue3);

    currentConf.merge(newConf);

    assertThat(currentConf).isNotEmpty();
    assertThat(currentConf.size()).isEqualTo(3);
    assertThat(currentConf.getString(key1)).isEqualTo(value1);
    assertThat(currentConf.getInteger(key2)).isEqualTo(newValue2);
    assertThat(currentConf.getString(key3)).isEqualTo(newValue3);
  }


  @Test
  public void testGetBooleanWithoutDefaultValue() {
    final Configuration conf = of();
    final String key = "boolean";
    final Boolean value = true;

    assertThat(conf).isEmpty();
    assertThat(conf.getBoolean(key)).isNull();

    conf.put(key, value);

    assertThat(conf).isNotEmpty();
    assertThat(conf.getBoolean(key)).isEqualTo(value);
  }


  @Test
  public void testGetByteWithoutDefaultValue() {
    final Configuration conf = of();
    final String key = "byte";
    final Integer number = 100;
    final Byte value = Byte.valueOf(number.toString(), 2);

    assertThat(conf).isEmpty();
    assertThat(conf.getByte(key)).isNull();

    conf.put(key, value);

    assertThat(conf).isNotEmpty();
    assertThat(conf.getByte(key)).isEqualTo(value);
  }


  @Test
  public void testGetCharacterWithoutDefaultValue() {
    final Configuration conf = of();
    final String key = "character";
    final Character value = 'c';

    assertThat(conf).isEmpty();
    assertThat(conf.getCharacter(key)).isNull();

    conf.put(key, value);

    assertThat(conf).isNotEmpty();
    assertThat(conf.getCharacter(key)).isEqualTo(value);
  }


  @Test
  public void testGetShortWithoutDefaultValue() {
    final Configuration conf = of();
    final String key = "short";
    final Short value = 127;

    assertThat(conf).isEmpty();
    assertThat(conf.getShort(key)).isNull();

    conf.put(key, value);

    assertThat(conf).isNotEmpty();
    assertThat(conf.getShort(key)).isEqualTo(value);
  }


  @Test
  public void testGetFloatWithoutDefaultValue() {
    final Configuration conf = of();
    final String key = "float";
    final Float value = 0.534f;

    assertThat(conf).isEmpty();
    assertThat(conf.getFloat(key)).isNull();

    conf.put(key, value);

    assertThat(conf).isNotEmpty();
    assertThat(conf.getFloat(key)).isEqualTo(value);
  }


  @Test
  public void testGetIntegerWithoutDefaultValue() {
    final Configuration conf = of();
    final String key = "integer";
    final Integer value = 234234;

    assertThat(conf).isEmpty();
    assertThat(conf.getInteger(key)).isNull();

    conf.put(key, value);

    assertThat(conf).isNotEmpty();
    assertThat(conf.getInteger(key)).isEqualTo(value);
  }


  @Test
  public void testGetLongWithoutDefaultValue() {
    final Configuration conf = of();
    final String key = "long";
    final Long value = 5342342L;

    assertThat(conf).isEmpty();
    assertThat(conf.getLong(key)).isNull();

    conf.put(key, value);

    assertThat(conf).isNotEmpty();
    assertThat(conf.getLong(key)).isEqualTo(value);
  }


  @Test
  public void testGetDoubleWithoutDefaultValue() {
    final Configuration conf = of();
    final String key = "double";
    final Double value = 0.3523423d;

    assertThat(conf).isEmpty();
    assertThat(conf.getDouble(key)).isNull();

    conf.put(key, value);

    assertThat(conf).isNotEmpty();
    assertThat(conf.getDouble(key)).isEqualTo(value);
  }


  @Test
  public void testGetStringWithoutDefaultValue() {
    final Configuration conf = of();
    final String key = "string";
    final String value = "string value";

    assertThat(conf).isEmpty();
    assertThat(conf.getString(key)).isNull();

    conf.put(key, value);

    assertThat(conf).isNotEmpty();
    assertThat(conf.getString(key)).isEqualTo(value);
  }



  @Test
  public void shouldGetWithDefaultValue() {
    final Configuration conf = of();
    final String defaultValue1 = "default";
    final Integer defaultValue2 = 55;
    final Long defaultValue3 = 34234L;

    assertThat(conf.getString("foo", defaultValue1)).isEqualTo(defaultValue1);
    assertThat(conf.getInteger("bar", defaultValue2)).isEqualTo(defaultValue2);
    assertThat(conf.getLong("hello world", defaultValue3)).isEqualTo(defaultValue3);
  }


  @Test
  public void testGetBooleanWithDefaultValue() {
    final Configuration conf = of();
    final String key = "boolean";
    conf.put(key, true);

    final Boolean result = conf.getBoolean(key, false);

    assertThat(result).isNotNull();
    assertThat(result).isTrue();
  }


  @Test
  public void testGetByteWithDefaultValue() {
    final Configuration conf = of();
    final String key = "byte";
    final Integer number = 100;
    final Byte value = Byte.valueOf(number.toString(), 2);

    conf.put(key, value);

    final Byte result = conf.getByte(key, null);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(value);
  }


  @Test
  public void testGetCharacterWithDefaultValue() {
    final Configuration conf = of();
    final String key = "character";
    final Character value = 'c';

    conf.put(key, value);

    final Character result = conf.getCharacter(key, null);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(value);
  }


  @Test
  public void testGetShortWithDefaultValue() {
    final Configuration conf = of();
    final String key = "short";
    final Short value = 10;

    conf.put(key, value);

    final Short result = conf.getShort(key, null);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(value);
  }


  @Test
  public void getFloatWithDefaultValue() {
    final Configuration conf = of();
    final String key = "float";
    final Float value = 100.0f;

    conf.put(key ,value);

    final Float result = conf.getFloat(key, null);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(value);
  }


  @Test
  public void testGetIntegerWithDefaultValue() {
    final Configuration conf = of();
    final String key = "integer";
    final Integer value = 555;

    conf.put(key, value);

    final Integer result = conf.getInteger(key, null);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(value);
  }


  @Test
  public void testGetLongWithDefaultValue() {
    final Configuration conf = of();
    final String key = "long";
    final Long value = 192035L;

    conf.put(key, value);

    final Long result = conf.getLong(key, null);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(value);
  }

  @Test
  public void testGetDoubleWithDefaultValue() {
    final Configuration conf = of();
    final String key = "double";
    final Double value = 1523.234234d;

    conf.put(key, value);

    final Double result = conf.getDouble(key, null);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(value);
  }


  @Test
  public void testGetStringWithDefaultValue() {
    final Configuration conf = of();
    final String key = "string";
    final String value = "for test";

    conf.put(key, value);

    final String result = conf.getString(key, null);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(value);
  }

  @Test
  public void testPutAll() {
    ConfigurationImpl configuration = new ConfigurationImpl();

    final String key1 = "str";        final String value1     = "for test";
    final String key2 = "double";     final Double value2     = 35.2323d;
    final String key3 = "long";       final Long value3       = 2342342L;
    final String key4 = "integer";    final Integer value4    = 35;
    final String key5 = "character";  final Character value5  = 'c';

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


  private Configuration of() {
    return new ConfigurationImpl();
  }
}
