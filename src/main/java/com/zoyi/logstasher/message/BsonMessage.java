package com.zoyi.logstasher.message;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;


/**
 * Created by lloyd on 2017-04-04
 */
public class BsonMessage implements Message<byte[]> {
  public static final String TYPE = BsonMessage.class.getName();


  private final BsonDocument document;


  public BsonMessage(Map<String, Object> data) {
    document =
        BsonDocuments.builder()
                     .put("type", TYPE)
                     .put("body", BsonDocuments.copyOf(data))
                     .build();
  }


  @Override
  public String getType() {
    return TYPE;
  }


  @Override
  public int byteLength() {
    return BsonDocuments.binarySize(this.document);
  }


  @Override
  public byte[] encode() {
    final ByteBuffer buffer =
        ByteBuffer.allocate(byteLength())
                  .order(ByteOrder.LITTLE_ENDIAN);

    BsonDocuments.writeTo(buffer, (BsonDocument) this.document.get("body"));
    buffer.flip();

    return buffer.array();
  }
}
