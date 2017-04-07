package com.zoyi.logstasher.configuration;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

    assertFalse(currentConf.isEmpty());
    assertEquals(currentConf.size(), 3);
    assertEquals(currentConf.getString(key1), value1);
    assertEquals(currentConf.getInteger(key2), newValue2);
    assertEquals(currentConf.getString(key3), newValue3);
  }


  @Test
  public void testGetBooleanWithoutDefaultValue() {
    final Configuration conf = of();
    final String key = "boolean";
    final Boolean value = true;

    assertTrue(conf.isEmpty());
    assertTrue(Objects.isNull(conf.getBoolean(key)));

    conf.put(key, value);

    assertFalse(conf.isEmpty());
    assertEquals(conf.getBoolean(key), value);
  }


  @Test
  public void testGetByteWithoutDefaultValue() {
    final Configuration conf = of();
    final String key = "byte";
    final Integer number = 100;
    final Byte value = Byte.valueOf(number.toString(), 2);

    assertTrue(conf.isEmpty());
    assertNull(conf.getByte(key));

    conf.put(key, value);

    assertFalse(conf.isEmpty());
    assertEquals(conf.getByte(key), value);
  }


  @Test
  public void testGetCharacterWithoutDefaultValue() {
    final Configuration conf = of();
    final String key = "character";
    final Character value = 'c';

    assertTrue(conf.isEmpty());
    assertNull(conf.getCharacter(key));

    conf.put(key, value);

    assertFalse(conf.isEmpty());
    assertEquals(conf.getCharacter(key), value);
  }


  @Test
  public void testGetShortWithoutDefaultValue() {
    final Configuration conf = of();
    final String key = "short";
    final Short value = 127;

    assertTrue(conf.isEmpty());
    assertNull(conf.getShort(key));

    conf.put(key, value);

    assertFalse(conf.isEmpty());
    assertEquals(conf.getShort(key), value);
  }


  @Test
  public void testGetFloatWithoutDefaultValue() {
    final Configuration conf = of();
    final String key = "float";
    final Float value = 0.534f;

    assertTrue(conf.isEmpty());
    assertNull(conf.getFloat(key));

    conf.put(key, value);

    assertFalse(conf.isEmpty());
    assertEquals(conf.getFloat(key), value);
  }


  @Test
  public void testGetIntegerWithoutDefaultValue() {
    final Configuration conf = of();
    final String key = "integer";
    final Integer value = 234234;

    assertTrue(conf.isEmpty());
    assertNull(conf.getInteger(key));

    conf.put(key, value);

    assertFalse(conf.isEmpty());
    assertEquals(conf.getInteger(key), value);
  }


  @Test
  public void testGetLongWithoutDefaultValue() {
    final Configuration conf = of();
    final String key = "long";
    final Long value = 5342342L;

    assertTrue(conf.isEmpty());
    assertNull(conf.getLong(key));

    conf.put(key, value);

    assertFalse(conf.isEmpty());
    assertEquals(conf.getLong(key), value);
  }


  @Test
  public void testGetDoubleWithoutDefaultValue() {
    final Configuration conf = of();
    final String key = "double";
    final Double value = 0.3523423d;

    assertTrue(conf.isEmpty());
    assertNull(conf.getDouble(key));

    conf.put(key, value);

    assertFalse(conf.isEmpty());
    assertEquals(conf.getDouble(key), value);
  }


  @Test
  public void testGetStringWithoutDefaultValue() {
    final Configuration conf = of();
    final String key = "string";
    final String value = "string value";

    assertTrue(conf.isEmpty());
    assertNull(conf.getString(key));

    conf.put(key, value);

    assertFalse(conf.isEmpty());
    assertEquals(conf.getString(key), value);
  }



  @Test
  public void shouldGetWithDefaultValue() {
    final Configuration conf = of();
    final String defaultValue1 = "default";
    final Integer defaultValue2 = 55;
    final Long defaultValue3 = 34234L;

    assertEquals(conf.getString("foo", defaultValue1), defaultValue1);
    assertEquals(conf.getInteger("bar", defaultValue2), defaultValue2);
    assertEquals(conf.getLong("hello world", defaultValue3), defaultValue3);
  }


  @Test
  public void testGetBooleanWithDefaultValue() {
    final Configuration conf = of();
    final String key = "boolean";
    conf.put(key, true);

    final Boolean result = conf.getBoolean(key, false);

    assertNotNull(result);
    assertTrue(result);
  }


  @Test
  public void testGetByteWithDefaultValue() {
    final Configuration conf = of();
    final String key = "byte";
    final Integer number = 100;
    final Byte value = Byte.valueOf(number.toString(), 2);

    conf.put(key, value);

    final Byte result = conf.getByte(key, null);

    assertNotNull(result);
    assertEquals(result, value);
  }


  @Test
  public void testGetCharacterWithDefaultValue() {
    final Configuration conf = of();
    final String key = "character";
    final Character value = 'c';

    conf.put(key, value);

    final Character result = conf.getCharacter(key, null);

    assertNotNull(result);
    assertEquals(result, value);
  }


  @Test
  public void testGetShortWithDefaultValue() {
    final Configuration conf = of();
    final String key = "short";
    final Short value = 10;

    conf.put(key, value);

    final Short result = conf.getShort(key, null);

    assertNotNull(result);
    assertEquals(result, value);
  }


  @Test
  public void getFloatWithDefaultValue() {
    final Configuration conf = of();
    final String key = "float";
    final Float value = 100.0f;

    conf.put(key ,value);

    final Float result = conf.getFloat(key, null);

    assertNotNull(result);
    assertEquals(result, value);
  }


  @Test
  public void testGetIntegerWithDefaultValue() {
    final Configuration conf = of();
    final String key = "integer";
    final Integer value = 555;

    conf.put(key, value);

    final Integer result = conf.getInteger(key, null);

    assertNotNull(result);
    assertEquals(result, value);
  }


  @Test
  public void testGetLongWithDefaultValue() {
    final Configuration conf = of();
    final String key = "long";
    final Long value = 192035L;

    conf.put(key, value);

    final Long result = conf.getLong(key, null);

    assertNotNull(result);
    assertEquals(result, value);
  }

  @Test
  public void testGetDoubleWithDefaultValue() {
    final Configuration conf = of();
    final String key = "double";
    final Double value = 1523.234234d;

    conf.put(key, value);

    final Double result = conf.getDouble(key, null);

    assertNotNull(result);
    assertEquals(result, value);
  }


  @Test
  public void testGetStringWithDefaultValue() {
    final Configuration conf = of();
    final String key = "string";
    final String value = "for test";

    conf.put(key, value);

    final String result = conf.getString(key, null);

    assertNotNull(result);
    assertEquals(result, value);
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

    assertFalse(conf.isEmpty());
    assertEquals(conf.getString(key1, null), value1);
    assertEquals(conf.getDouble(key2, null), value2);
    assertEquals(conf.getLong(key3, null), value3);
    assertEquals(conf.getInteger(key4, null), value4);
    assertEquals(conf.getCharacter(key5, null), value5);
  }


  private Configuration of() {
    return new ConfigurationImpl();
  }
}
