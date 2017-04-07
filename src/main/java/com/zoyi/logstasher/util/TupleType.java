package com.zoyi.logstasher.util;

/**
 * Represent configuration type.
 *
 * @see Tuple
 * @author Loustler(Dongyeon Lee)
 * @since 2017-04-05
 */
public enum TupleType {
  BOOLEAN(Boolean.class.getSimpleName()),
  BYTE(Byte.class.getSimpleName()),
  CHARACTER(Character.class.getSimpleName()),
  SHORT(Short.class.getSimpleName()),
  FLOAT(Float.class.getSimpleName()),
  INTEGER(Integer.class.getSimpleName()),
  LONG(Long.class.getSimpleName()),
  DOUBLE(Double.class.getSimpleName()),
  STRING(String.class.getSimpleName());

  private final String typeName;

  TupleType(final String typeName) {
    this.typeName = typeName;
  }

  public static TupleType of(final Class klass) {
    if (Boolean.class.isAssignableFrom(klass))
      return BOOLEAN;
    else if (Byte.class.isAssignableFrom(klass))
      return BYTE;
    else if (Character.class.isAssignableFrom(klass))
      return CHARACTER;
    else if (Short.class.isAssignableFrom(klass))
      return SHORT;
    else if (String.class.isAssignableFrom(klass))
      return STRING;
    else if (Integer.class.isAssignableFrom(klass))
      return INTEGER;
    else if (Long.class.isAssignableFrom(klass))
      return LONG;
    else if (Double.class.isAssignableFrom(klass))
      return DOUBLE;
    else if (Float.class.isAssignableFrom(klass))
      return FLOAT;
    else
      throw new UnsupportedOperationException(String.format("%s class not supported by tuple.",
                                                            klass.getSimpleName()));
  }


  public String getTypeName() { return typeName; }
}
