package com.zoyi.logstasher.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TupleTest {
  @Test
  public void shouldTypeIsBoolean() {
    final Boolean value = true;
    Tuple<Boolean> tuple = Tuple.mkTuple(value);

    assertEquals(tuple.getValue(), value);
    assertEquals(tuple.getType(), TupleType.BOOLEAN);
  }


  @Test
  public void shouldTypeIsByte() {
    final Byte value = Byte.valueOf("1011011", 2);
    Tuple<Byte> tuple = Tuple.mkTuple(value);

    assertEquals(tuple.getValue(), value);
    assertEquals(tuple.getType(), TupleType.BYTE);
  }


  @Test
  public void shouldTypeIsCharacter() {
    final Character value = 'c';
    Tuple<Character> tuple = Tuple.mkTuple(value);

    assertEquals(tuple.getValue(), value);
    assertEquals(tuple.getType(), TupleType.CHARACTER);
  }


  @Test
  public void shouldTypeIsShort() {
    final Short value = 127;
    Tuple<Short> tuple = Tuple.mkTuple(value);

    assertEquals(tuple.getValue(), value);
    assertEquals(tuple.getType(), TupleType.SHORT);
  }


  @Test
  public void shouldTypeIsFloat() {
    final Float value = 0.2343f;
    Tuple<Float> tuple = Tuple.mkTuple(value);

    assertEquals(tuple.getValue(), value);
    assertEquals(tuple.getType(), TupleType.FLOAT);
  }


  @Test
  public void shouldTypeIsInteger() {
    final Integer value = 55;
    Tuple<Integer> tuple = Tuple.mkTuple(value);

    assertEquals(tuple.getValue(), value);
    assertEquals(tuple.getType(), TupleType.INTEGER);
  }


  @Test
  public void shouldTypeIsLong() {
    final Long value = 52342L;
    Tuple<Long> tuple = Tuple.mkTuple(value);

    assertEquals(tuple.getValue(), value);
    assertEquals(tuple.getType(), TupleType.LONG);
  }


  @Test
  public void shouldTypeIsDouble() {
    final Double value = 0.2342342d;
    Tuple<Double> tuple = Tuple.mkTuple(value);

    assertEquals(tuple.getValue(), value);
    assertEquals(tuple.getType(), TupleType.DOUBLE);
  }


  @Test
  public void shouldTypeIsString() {
    final String value = "string";
    Tuple<String> tuple = Tuple.mkTuple(value);

    assertEquals(tuple.getValue(), value);
    assertEquals(tuple.getType(), TupleType.STRING);
  }
}
