package com.zoyi.logstasher.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilTest {
  @Test
  public void shouldNotNullWhenNotNull() {
    final String str = "notnull";

    assertTrue(StringUtil.isNotNullOrEmpty(str));
  }


  @Test
  public void shouldBeFalseWhenIsEmpty() {
    final String str = "";

    assertFalse(StringUtil.isNotNullOrEmpty(str));
  }


  @Test
  public void shouldBeFalseWhenNull() {
    final String str = null;

    assertFalse(StringUtil.isNotNullOrEmpty(str));
  }


  @Test
  public void shouldBeTrueWhenEmpty() {
    final String str = "";

    assertTrue(StringUtil.isNullOrEmpty(str));
  }


  @Test
  public void shouldBeTrueWhenNull() {
    final String str = null;

    assertTrue(StringUtil.isNullOrEmpty(str));
  }
}
