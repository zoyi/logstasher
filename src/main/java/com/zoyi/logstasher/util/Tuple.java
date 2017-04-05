package com.zoyi.logstasher.util;

/**
 * Created by lou on 2017-04-05 15:09
 */
public class Tuple <V> {
  final V value;

  final TupleType type;

  private Tuple(V value, TupleType type) {
    this.value = value;
    this.type = type;
  }

  private Tuple(V value, Class klass) {
    this(value, TupleType.of(klass));
  }


  /**
   * Create new Tuple
   *
   * @param value The value
   * @param <V> The type of value
   * @return new Tuple
   */
  public static <V> Tuple mkTuple(final V value) {
    return new Tuple(value, value.getClass());
  }


  /**
   * Create new Tuple.
   *
   * Recommend to use {@code mkTuple(value)} instead.
   *
   * @param value The value
   * @param type The Class of value
   * @param <V> The type of value
   * @return new Tuple
   */
  public static <V> Tuple mkTuple(final V value, final Class type) {
    return new Tuple(value, type);
  }


  /**
   * Create new Tuple.
   *
   * Recommend to use {@code mkTuple(value)} instead.
   *
   * @param value The value
   * @param type The TupleType of value
   * @param <V> The type of value
   * @return new Tuple
   */
  public static <V> Tuple mkTuple(final V value, final TupleType type) {
    return new Tuple(value, type);
  }


  public TupleType getType() {
    return type;
  }


  public V getValue() {
    return value;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Tuple)) return false;

    Tuple<?> tuple = (Tuple<?>)o;

    if (!value.equals(tuple.value)) return false;
    return type == tuple.type;
  }


  @Override
  public int hashCode() {
    int result = value.hashCode();
    result = 31 * result + type.getTypeName()
                               .hashCode();
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Tuple{");

    sb.append("value=")
      .append(value)
      .append(", type=")
      .append(type.getTypeName())
      .append('}');

    return sb.toString();
  }
}
