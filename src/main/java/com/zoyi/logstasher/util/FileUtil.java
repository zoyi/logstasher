package com.zoyi.logstasher.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Loustler(Dongyeon Lee)
 * @since 2017-04-10
 */
public class FileUtil {
  public static List<File> getClassFiles(String path) {
    List<File> files = new ArrayList<>();

    FileVisitor<Path> simpleFileVisitor = new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult visitFile(
        Path file, BasicFileAttributes attrs
      ) throws IOException {
        files.add(new File(file.toUri()));
        return super.visitFile(file, attrs);
      }
    };

    try {
      FileSystem fileSystem =  FileSystems.getDefault();

      Path rootPath = fileSystem.getPath(path);

      Files.walkFileTree(rootPath, simpleFileVisitor);
    } catch (Exception e) {
      return Collections.emptyList();
    }

    return files;
  }
}
