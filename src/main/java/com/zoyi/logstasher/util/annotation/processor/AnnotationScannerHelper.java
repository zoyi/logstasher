package com.zoyi.logstasher.util.annotation.processor;

import com.zoyi.logstasher.util.FileUtil;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by lou on 2017-04-11 14:16
 */
class AnnotationScannerHelper {
  static final String CLASS_FILE_EXTENSION = ".class";

  static List<Class<?>> loadClassesFromJar(final String jarPath, final CharSequence matchingPath) {
    List<Class<?>> classes = new ArrayList<>();

    try {
      JarFile jarFile = new JarFile(jarPath);
      Enumeration<JarEntry> jarEntries = jarFile.entries();

      URLClassLoader cl = URLClassLoader.newInstance(
        new URL[]{new URL("jar:file:" + jarPath + "!/")}
      );

      while (jarEntries.hasMoreElements()) {
        JarEntry jarEntry = jarEntries.nextElement();

        if (jarEntry.isDirectory() ||
            !jarEntry.toString().contains(matchingPath) ||
            !jarEntry.getName()
                     .endsWith(CLASS_FILE_EXTENSION)) {
          continue;
        }

        String className = jarEntry.getName()
                                   .substring(0, jarEntry.getName()
                                                         .length() - 6)
                                   .replace('/', '.');
        classes.add(cl.loadClass(className));
      }
    } catch (Exception e) {
      return Collections.EMPTY_LIST;
    }

    return classes;
  }

  static List<Class<?>> loadClassFromClassPath(final String classPath) {
    List<Class<?>> classes = new ArrayList<>();

    List<File> files = FileUtil.getClassFiles(classPath);

    try {
      for (File directory : files) {
        String _classPath = directory.toURI()
                                     .getPath()
                                     .replace('/', '.');

        _classPath = _classPath.substring(_classPath.indexOf(AnnotationScanner.SCANNING_PATH),
                                          _classPath.lastIndexOf(CLASS_FILE_EXTENSION));

        classes.add(Class.forName(_classPath));
      }
    } catch (ClassNotFoundException e) {
      return Collections.EMPTY_LIST;
    }

    return classes;
  }
}
