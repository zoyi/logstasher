package com.zoyi.logstasher.util;

import com.zoyi.logstasher.Logstashers;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lou on 2017-04-10 18:02
 */
public class FileUtilTest {
  @Test
  public void testGetFiles() {
    final String rootName =
      Logstashers.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    List<File> files =  FileUtil.getClassFiles(rootName);

    assertFalse(files.isEmpty());

    for (File file : files) {
      assertNotNull(file);
    }

  }
}
