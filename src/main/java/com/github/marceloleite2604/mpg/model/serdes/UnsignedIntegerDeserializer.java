package com.github.marceloleite2604.mpg.model.serdes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;

import java.io.IOException;

public class UnsignedIntegerDeserializer extends StdDeserializer<Integer> {

  public UnsignedIntegerDeserializer(){
    super(Integer.class);
  }

  @Override
  public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    final var node = jsonParser.getCodec()
        .readTree(jsonParser);
    if (!node.isValueNode()) {
      final var message = String.format("Expecting value for \"%s\" node.", jsonParser.currentName());
      throw new IllegalStateException(message);
    }

    switch (node.numberType()) {
      case LONG -> {
        final var value = ((LongNode) node).longValue();
        return (int) value;
      }
      case INT -> {
        return ((IntNode) node).intValue();
      }
      default -> {
        final var message = String.format("Expecting an integer value for \"%s\" node.", jsonParser.currentName());
        throw new IllegalStateException(message);
      }
    }
  }
}
