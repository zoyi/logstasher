package com.zoyi.logstasher.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by lou on 2017-04-05 16:43
 */
public class TupleTest {
  @Test
  public void shouldTypeIsBoolean() {
    final Boolean value = true;
    Tuple<Boolean> tuple = Tuple.mkTuple(value);

    assertThat(tuple.getValue()).isEqualTo(value);
    assertThat(tuple.getType()).isEqualTo(TupleType.BOOLEAN);
  }


  @Test
  public void shouldTypeIsByte() {
    final Byte value = Byte.valueOf("1011011", 2);
    Tuple<Byte> tuple = Tuple.mkTuple(value);

    assertThat(tuple.getValue()).isEqualTo(value);
    assertThat(tuple.getType()).isEqualTo(TupleType.BYTE);
  }


  @Test
  public void shouldTypeIsCharacter() {
    final Character value = 'c';
    Tuple<Character> tuple = Tuple.mkTuple(value);

    assertThat(tuple.getValue()).isEqualTo(value);
    assertThat(tuple.getType()).isEqualTo(TupleType.CHARACTER);
  }


  @Test
  public void shouldTypeIsShort() {
    final Short value = 127;
    Tuple<Short> tuple = Tuple.mkTuple(value);

    assertThat(tuple.getValue()).isEqualTo(value);
    assertThat(tuple.getType()).isEqualTo(TupleType.SHORT);
  }


  @Test
  public void shouldTypeIsFloat() {
    final Float value = 0.2343f;
    Tuple<Float> tuple = Tuple.mkTuple(value);

    assertThat(tuple.getValue()).isEqualTo(value);
    assertThat(tuple.getType()).isEqualTo(TupleType.FLOAT);
  }


  @Test
  public void shouldTypeIsInteger() {
    final Integer value = 55;
    Tuple<Integer> tuple = Tuple.mkTuple(value);

    assertThat(tuple.getValue()).isEqualTo(value);
    assertThat(tuple.getType()).isEqualTo(TupleType.INTEGER);
  }


  @Test
  public void shouldTypeIsLong() {
    final Long value = 52342L;
    Tuple<Long> tuple = Tuple.mkTuple(value);

    assertThat(tuple.getValue()).isEqualTo(value);
    assertThat(tuple.getType()).isEqualTo(TupleType.LONG);
  }


  @Test
  public void shouldTypeIsDouble() {
    final Double value = 0.2342342d;
    Tuple<Double> tuple = Tuple.mkTuple(value);

    assertThat(tuple.getValue()).isEqualTo(value);
    assertThat(tuple.getType()).isEqualTo(TupleType.DOUBLE);
  }


  @Test
  public void shouldTypeIsString() {
    final String value = "string";
    Tuple<String> tuple = Tuple.mkTuple(value);

    assertThat(tuple.getValue()).isEqualTo(value);
    assertThat(tuple.getType()).isEqualTo(TupleType.STRING);
  }
}
