package com.github.marceloleite2604.mpg.util;

import com.github.marceloleite2604.mpg.model.Password;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ByteUtil {

  public static String asHexString(byte[] bytes) {
    final List<String> bytesAsString = new ArrayList<>();
    for (byte aByte : bytes) {
      bytesAsString.add(String.format("%02x", aByte));
    }

    return String.join(" ", bytesAsString);
  }

  public static void rotateRight(byte[] bytes, int startPos, int endPos, int rotationTimes) {
    for (int rotationIterator = 0; rotationIterator < rotationTimes; rotationIterator++) {
      byte msb = (byte) ((bytes[endPos - 1] & 0x01) << 7);

      for (int byteIterator = startPos; byteIterator < endPos; byteIterator++) {
        byte nextMsb = (byte) ((bytes[byteIterator] & 0x01) << 7);
        byte rotatedByte = (byte) ((bytes[byteIterator] & 0xff) >> 1);
        rotatedByte |= msb;
        bytes[byteIterator] = rotatedByte;
        msb = nextMsb;
      }
    }
  }

  public static void split(byte[] input, int startPos, int endPos, int size, byte[] output) {
    if (size > 8 ) {
      throw new IllegalArgumentException("Bytes size cannot be greater than 8.");
    }

    final var totalBits = (endPos - startPos) * 8;
    final var totalBytesResult = (int)Math.ceil((double)totalBits / (double)size);

    if (output.length < totalBytesResult) {
      throw  new IllegalArgumentException("Output bytes length is lower than the total bytes generated.");
    }

    final var bitOffset = 8 - size;

    byte resultByte = 0x0;
    for (int bitIterator = 0; bitIterator < totalBits; bitIterator++) {
      final var byteIndex = bitIterator / 8;
      final var bitIndex = bitIterator % 8;

      final var bit = (input[startPos + byteIndex] & 0xff) & (0x80 >> bitIndex);

      final var resultBitPosition = (bitIterator % size) + bitOffset;
      final var bitDiff = resultBitPosition - bitIndex;

      if (bitDiff > 0) {
        resultByte |= bit >> bitDiff;
      } else {
        resultByte |= bit << -bitDiff;
      }

      if (resultBitPosition == 7) {
        output[bitIterator / size] = resultByte;
        resultByte = 0x0;
      }
    }
  }
}
