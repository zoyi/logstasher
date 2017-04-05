package com.zoyi.logstasher.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by lou on 2017-04-05 14:57
 */
public class StringUtilTest {
  @Test
  public void shouldNotNullWhenNotNull() {
    final String str = "notnull";

    assertThat(StringUtil.isNotNullOrEmpty(str)).isTrue();
  }


  @Test
  public void shouldBeFalseWhenIsEmpty() {
    final String str = "";

    assertThat(StringUtil.isNotNullOrEmpty(str)).isFalse();
  }


  @Test
  public void shouldBeFalseWhenNull() {
    final String str = null;

    assertThat(StringUtil.isNotNullOrEmpty(str)).isFalse();
  }


  @Test
  public void shouldBeTrueWhenEmpty() {
    final String str = "";

    assertThat(StringUtil.isNullOrEmpty(str)).isTrue();
  }


  @Test
  public void shouldBeTrueWhenNull() {
    final String str = null;

    assertThat(StringUtil.isNullOrEmpty(str)).isTrue();
  }
}
