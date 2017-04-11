package com.zoyi.logstasher.util.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Scanning annotated classes on package.
 *
 * Annotation type provide generic by reflection.
 *
 * The class that extends this interface needs instance to use generic.
 *
 * @author Loustler(Dongyeon Lee)
 * @since 2017-04-05
 */
public interface AnnotationScanner <T extends Annotation> {
  String SCANNING_PATH        = "com.zoyi.logstasher";



  /**
   * Find Annotated Class using reflection.
   *
   * @return The result found classes using reflection.
   */
  List<Class<?>> getAnnotatedClasses();


  /**
   * Scans all classes accessible from the context class loader which belong
   * to the given package and subpackages.
   *
   * @param packageName
   *            The base package
   * @return The classes
   * @throws RuntimeException if any class not found.
   */
  default List<Class<?>> getClasses(final String packageName) {
    String srcRootPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    List<Class<?>> classes = new ArrayList<Class<?>>();

    if (srcRootPath.endsWith(".jar")) // load classes in jar.
      classes.addAll(AnnotationScannerHelper.loadClassesFromJar(
        srcRootPath,
        SCANNING_PATH.replace('.', '/'))
      );
    else // load classes in class path(current classpath).
      classes.addAll(AnnotationScannerHelper.loadClassFromClassPath(srcRootPath));

    return classes;
  }


  default Type getGenericsClassType(int index) {
    return ((ParameterizedType)getClass()
      .getGenericSuperclass())
      .getActualTypeArguments()[index];
  }
}
