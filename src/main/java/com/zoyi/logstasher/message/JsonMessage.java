package com.zoyi.logstasher.message;

import io.vertx.core.json.JsonObject;

import java.util.Map;
import java.util.Objects;


/**
 * Created by lloyd on 2017-04-06
 */
public class JsonMessage implements Message<byte[]> {
  public static final String TYPE = JsonMessage.class.getName();
  public static final String DEFAULT_CHARSET = "UTF-8";


  private final JsonObject document;


  public JsonMessage(Map<String, Object> data) {
    this(new JsonObject().put("type", TYPE).put("body", data));
  }


  public JsonMessage(JsonObject document) {
    this.document = document;
  }


  @Override
  public String toString() {
    return this.document.encode();
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    JsonMessage that = (JsonMessage) o;
    return Objects.equals(document, that.document);
  }


  @Override
  public int hashCode() {
    return Objects.hash(document);
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
    // TODO: cache
    return this.document
        .getJsonObject("body")
        .encode()
        .getBytes(DEFAULT_CHARSET);
  }
}
