package com.zoyi.logstasher.util.annotation.processor;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by lou on 2017-04-04 19:59
 */
abstract class AnnotationProcessor<T extends Annotation> implements AnnotationScanner<T> {
  private static final Map<String, Class<?>> ANNOTATED_CLASS_MAP = new ConcurrentHashMap<>();


  public AnnotationProcessor() {
    initialize();
  }


  public void initialize() {
    getAnnotatedClasses().stream()
                         .forEach(klass -> addClass(klass));
  }


  /**
   * Retrieve annotated class by name.
   *
   * The name default is simple name.
   *
   * But annotation has name, it will be the name.
   *
   * @see Class#getSimpleName()
   * @see com.zoyi.logstasher.util.annotation.Name
   *
   * @param name The name when retrieve class
   * @return The annotated class.
   */
  public static Class<?> getClass(final String name) {
    return (ANNOTATED_CLASS_MAP.get(name));
  }


  public List<Class<?>> getAnnotatedClasses() {
    Class<T> annotationClass = (Class<T>)getGenericsClassType(0);
    List<Class<?>> classes = getClasses(SCANNING_PATH);

    return classes.stream()
                  .filter(c -> c.isAnnotationPresent(annotationClass))
                  .collect(Collectors.toList());
  }


  void put(final String key, final Class<?> klass) {
    if (Objects.isNull(key))
      throw new NullPointerException("Key is not null.");

    ANNOTATED_CLASS_MAP.put(key, klass);
  }


  /**
   * Add annotated class to Map.
   *
   * Required check class has annotation.
   *
   * When class has annotation name type, map's key is.
   *
   * If annotation class(T) has no name, then class' simple name to be map's key.
   *
   * @param klass The class will be added to Map as value.
   */
  abstract protected void addClass(final Class<?> klass);
}