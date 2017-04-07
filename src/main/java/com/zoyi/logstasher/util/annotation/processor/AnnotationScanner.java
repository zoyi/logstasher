package com.zoyi.logstasher.util.annotation.processor;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
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
    String SCANNING_PATH = "com.zoyi.logstasher";
    String CLASS_FILE_EXTENTION = ".class";

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
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    String path = packageName.replace('.', '/');
    List<Class<?>> classes = new ArrayList<Class<?>>();

    try {
      Enumeration<URL> resources = classLoader.getResources(path);
      List<File> dirs = new ArrayList<File>();
      while (resources.hasMoreElements()) {
        URI uri = new URI(resources.nextElement()
                                   .toString());
        dirs.add(new File(uri.getPath()));
      }
      for (File directory : dirs) {
        classes.addAll(findClasses(directory, packageName));
      }
    } catch (IOException | URISyntaxException | ClassNotFoundException e) {
      throw new RuntimeException("Can not find any class");
    }

    return classes;
  }


  /**
   * Recursive method used to find all classes in a given directory and
   * sub-dirs.
   *
   * @param directory
   *            The base directory
   * @param packageName
   *            The package name for classes found inside the base directory
   * @return The classes
   * @throws ClassNotFoundException
   */
  default List<Class<?>> findClasses(final File directory, final String packageName)
    throws ClassNotFoundException {
    List<Class<?>> classes = new ArrayList<Class<?>>();
    if (!directory.exists() || directory.getPath().toLowerCase().contains("test")) {
      return classes;
    }

    File[] files = directory.listFiles();
    for (File file : files) {
      final String fqcn = packageName + "." + file.getName();

      if (file.isDirectory()) {
        classes.addAll(findClasses(file, fqcn)); // recursive search
      } else if (file.getName().endsWith(CLASS_FILE_EXTENTION)) { // check class file
        classes.add(Class.forName(fqcn.substring(0, fqcn.length() - CLASS_FILE_EXTENTION.length())));
      }
    }
    return classes;
  }


  default Type getGenericsClassType(int index) {
    return ((ParameterizedType)getClass()
      .getGenericSuperclass())
      .getActualTypeArguments()[index];
  }
}
