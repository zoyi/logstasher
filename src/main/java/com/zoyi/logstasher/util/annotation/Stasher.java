package com.zoyi.logstasher.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Loustler(Dongyeon Lee)
 * @since 2017-04-04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Stasher {

  String EMPTY = "";

  String value() default EMPTY;

  Name name() default Name.EMPTY;
}
