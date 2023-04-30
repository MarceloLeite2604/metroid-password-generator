package com.github.marceloleite2604.mpg.model.serdes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ByteAsUnsignedShortSerializer extends StdSerializer<Byte> {

  protected ByteAsUnsignedShortSerializer() {
    super(Byte.class);
  }

  @Override
  public void serialize(Byte value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    final var shortValue = (short)(value & 0x00ff);
    jsonGenerator.writeNumber(shortValue);
  }
}
