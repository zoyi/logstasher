package com.zoyi.logstasher.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Junbong
 * @since 0.1.4
 */
public class ArgumentParser {
  private static final Pattern PATTERN =
      Pattern.compile("--(\\w+)=(.*)");


  public static Map<String, Object> parse(String[] args) {
    final Map<String, Object> result = new HashMap<>();

    for (String token : args) {
      Matcher matcher;

      if ((matcher=PATTERN.matcher(token)).matches()) {
        final String key = matcher.group(1);
        final String value = matcher.group(2);

        result.put(key,
            value.matches("\\d+")
                ? Integer.parseInt(value)
                : value);
      }
    }

    return result;
  }
}
