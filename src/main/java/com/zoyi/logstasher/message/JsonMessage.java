package com.zoyi.logstasher.message;

import io.vertx.core.json.JsonObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Objects;


/**
 * @author Junbong
 * @since 2017-04-06
 */
public class JsonMessage implements Message<byte[]> {
  public static final String TYPE = JsonMessage.class.getName();
  public static final String DEFAULT_CHARSET = "UTF-8";


  private final JsonObject document;


  public JsonMessage() {
    this(null);
  }


  public JsonMessage(Map<String, Object> data) {
    this.document = new JsonObject().put("type", TYPE);

    // Initialize body
    getDocument().put("body", data != null ? data : new JsonObject());

    // Timestamp
    if (!containsKey("@timestamp") && !containsKey("timestamp")) {
      setTimestamp(LocalDateTime.now());
    }
  }


  @Override
  public String toString() {
    return getDocument().encode();
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    JsonMessage that = (JsonMessage) o;
    return Objects.equals(this.getDocument(), that.getDocument());
  }


  @Override
  public int hashCode() {
    return Objects.hash(getDocument());
  }


  @Override
  public String getType() {
    return TYPE;
  }


  @Override
  public int getByteLength() {
    try {
      return encode().length;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  public byte[] encode() throws Exception {
    if (!containsKey("@timestamp") && !containsKey("timestamp")) {
      setTimestamp(LocalDateTime.now());
    }

    return getBody()
        .encode()
        .getBytes(DEFAULT_CHARSET);
  }


  protected JsonObject getDocument() {
    return this.document;
  }


  protected JsonObject getBody() {
    return getDocument().getJsonObject("body");
  }


  public boolean containsKey(String key) {
    return getBody().containsKey(key);
  }


  @Override
  public Message<byte[]> setTimestamp(Instant instant) {
    Objects.requireNonNull(instant);
    return this.setTimestamp(
        LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
  }


  @Override
  public Message<byte[]> setTimestamp(LocalDateTime localDateTime) {
    Objects.requireNonNull(localDateTime);
    getBody().put("@timestamp", localDateTime.toString());
    return this;
  }


  @Override
  public Message<byte[]> put(String key, Boolean value) {
    getBody().put(key, value);
    return this;
  }


  @Override
  public Message<byte[]> put(String key, Character value) {
    getBody().put(key, value);
    return this;
  }


  @Override
  public Message<byte[]> put(String key, Short value) {
    getBody().put(key, value);
    return this;
  }


  @Override
  public Message<byte[]> put(String key, Float value) {
    getBody().put(key, value);
    return this;
  }


  @Override
  public Message<byte[]> put(String key, Integer value) {
    getBody().put(key, value);
    return this;
  }


  @Override
  public Message<byte[]> put(String key, Long value) {
    getBody().put(key, value);
    return this;
  }


  @Override
  public Message<byte[]> put(String key, Double value) {
    getBody().put(key, value);
    return this;
  }


  @Override
  public Message<byte[]> put(String key, CharSequence value) {
    getBody().put(key, value);
    return this;
  }


  @Override
  public Message<byte[]> put(String key, String value) {
    getBody().put(key, value);
    return this;
  }


  @Override
  public Message<byte[]> put(String key, Object value) {
    getBody().put(key, value);
    return this;
  }
}
