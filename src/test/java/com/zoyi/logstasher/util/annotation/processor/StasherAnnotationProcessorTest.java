package com.zoyi.logstasher.util.annotation.processor;

import com.zoyi.logstasher.output.tcp.TcpLogstasherImpl;
import com.zoyi.logstasher.util.annotation.Name;
import org.junit.Test;

import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class StasherAnnotationProcessorTest {
  @Test
  public void shouldClassIsNotEmpty() throws Exception {
    final String clazzName = Name.TCP.getName();
    Class<?> clazz = StasherAnnotationProcessor.STASHER_PROCESSOR.getClass(clazzName);

    assertNotNull(clazz);
    assertTrue(TcpLogstasherImpl.class.isAssignableFrom(clazz));
  }

  @Test
  public void nio() throws Exception {
    String pa = AnnotationScanner.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    Path dir = FileSystems.getDefault().getPath(pa + AnnotationScanner.SCANNING_PATH.replace('.', '/'));

    DirectoryStream<Path> stream = Files.newDirectoryStream(dir);

    for (Path path : stream) {
      System.out.println(path.toUri());
      String name = path.getFileName().toString();

      if (name.endsWith(".class"))
        System.out.println(String.format("ClassFile : %s ", name));
      else if (name.lastIndexOf('.') == -1)
        System.out.println(String.format("Directory : %s", name));

    }

    stream.close();
  }
}