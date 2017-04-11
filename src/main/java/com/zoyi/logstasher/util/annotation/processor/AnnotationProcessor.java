package com.zoyi.logstasher.util.annotation.processor;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Loustler(Dongyeon Lee)
 * @since 2017-04-04
 */
abstract class AnnotationProcessor<T extends Annotation> implements AnnotationScanner<T> {
  private static final Map<String, Class<?>> ANNOTATED_CLASS_MAP = new ConcurrentHashMap<>();


  public AnnotationProcessor() {
    initialize();
  }


  public void initialize() {
    getAnnotatedClasses().forEach(this::addClass);
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


  /**
   * Retrieve the list of class annotated by {@code T}.
   *
   * @return The List of annotated classes.
   */
  @SuppressWarnings("unchecked")
  public List<Class<?>> getAnnotatedClasses() {
    Class<T> annotationClass = (Class<T>) getGenericsClassType(0);
    List<Class<?>> classes = getClasses(SCANNING_PATH);

    return classes.stream()
                  .filter(c -> c.isAnnotationPresent(annotationClass))
                  .collect(Collectors.toList());
  }


  /**
   * Associates the specified {@code klass} with the specified {@code key} in the map.
   *
   * @param key key with which the specified class to be associated.
   * @param klass class to be associated with the specified key
   * @throws NullPointerException if the specified key or value is null
   */
  void put(final String key, final Class<?> klass) {
    Objects.requireNonNull(key);
    Objects.requireNonNull(klass);

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