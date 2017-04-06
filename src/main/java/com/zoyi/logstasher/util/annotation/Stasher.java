package com.zoyi.logstasher.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lou on 2017-04-04 15:49
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Stasher {

  String EMPTY = "";

  String value() default EMPTY;

  Name name() default Name.EMPTY;
}
