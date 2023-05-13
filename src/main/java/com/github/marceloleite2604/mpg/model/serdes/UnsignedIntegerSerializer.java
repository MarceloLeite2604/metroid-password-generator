package com.github.marceloleite2604.mpg.model.serdes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class UnsignedIntegerSerializer extends StdSerializer<Integer> {

  public UnsignedIntegerSerializer() {
    super(Integer.class);
  }

  @Override
  public void serialize(Integer value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    final var longGameAge = Integer.toUnsignedLong(value);
    jsonGenerator.writeNumber(longGameAge);
  }
}
