package com.github.marceloleite2604.mpg.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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
      byte lsb = (byte) ((bytes[endPos - 1] & 0x01) << 7);

      for (int byteIterator = startPos; byteIterator < endPos; byteIterator++) {
        byte nextLsb = (byte) ((bytes[byteIterator] & 0x01) << 7);
        byte rotatedByte = (byte) ((bytes[byteIterator] & 0xff) >> 1);
        rotatedByte |= lsb;
        bytes[byteIterator] = rotatedByte;
        lsb = nextLsb;
      }
    }
  }

  public static void rotateLeft(byte[] bytes, int startPos, int endPos, int rotationTimes) {
    for (int rotationCount = 0; rotationCount < rotationTimes; rotationCount++) {
      byte msb = (byte) ((bytes[0] & 0x80) >> 7);

      for (int byteIterator = endPos - 1; byteIterator >= startPos; byteIterator--) {
        byte nextMsb = (byte) ((bytes[byteIterator] & 0x80) >> 7);
        byte rotatedByte = (byte) ((bytes[byteIterator] & 0xff) << 1);
        rotatedByte |= msb;
        bytes[byteIterator] = rotatedByte;
        msb = nextMsb;
      }
    }
  }

  public static void split(byte[] input, int inputStartIndex, int inputEndIndex, int size, byte[] output, int outputStartIndex) {
    if (size > 8 ) {
      throw new IllegalArgumentException("Bytes size cannot be greater than 8.");
    }

    final var totalBits = (inputEndIndex - inputStartIndex) * 8;
    final var totalBytesResult = (int)Math.ceil((double)totalBits / (double)size);

    if ((output.length - outputStartIndex) < totalBytesResult) {
      throw  new IllegalArgumentException("Output bytes length is lower than the total bytes generated.");
    }

    final var bitOffset = 8 - size;

    byte resultByte = 0x0;
    for (int bitIterator = 0; bitIterator < totalBits; bitIterator++) {
      final var byteIndex = bitIterator / 8;
      final var bitIndex = bitIterator % 8;

      final var bit = (input[inputStartIndex + byteIndex] & 0xff) & (0x80 >> bitIndex);

      final var resultBitPosition = (bitIterator % size) + bitOffset;
      final var bitDiff = resultBitPosition - bitIndex;

      if (bitDiff > 0) {
        resultByte |= bit >> bitDiff;
      } else {
        resultByte |= bit << -bitDiff;
      }

      if (resultBitPosition == 7) {
        final var outputIndex = outputStartIndex + (bitIterator / size);
        output[outputIndex] = resultByte;
        resultByte = 0x0;
      }
    }
  }

  public static void join(byte[] input, int inputStartIndex, int inputEndIndex, int size, byte[] output, int outputStartIndex) {

    if (size > 8 ) {
      throw new IllegalArgumentException("Bytes size cannot be greater than 8.");
    }

    final var totalInputBytesToRead = (inputEndIndex - inputStartIndex);

    if ((inputStartIndex + totalInputBytesToRead) > input.length) {
      throw new IllegalArgumentException("Total bytes to read from input exceeds the array size.");
    }

    final var totalBitsToRead = (inputEndIndex - inputStartIndex) * size;
    final var totalOutputBytesToWrite = totalBitsToRead / 8;

    if ((outputStartIndex + totalOutputBytesToWrite) > output.length) {
      throw new IllegalArgumentException("Total bytes to write into output exceeds the array size.");
    }

    final var bitOffset = 8 - size;

    byte resultByte = 0x0;
    for (int bitIndex = 0; bitIndex < totalBitsToRead; bitIndex++) {
      final var inputByteIndex = inputStartIndex + (bitIndex/size);
      final var inputBitIndex = bitOffset + (bitIndex % size);

      final var bit = (byte) ((input[inputStartIndex + inputByteIndex] & 0xff) & (0x80 >> inputBitIndex ));

      final var outputBitPosition = (bitIndex % 8);
      final var bitDiff = outputBitPosition - inputBitIndex;

      if (bitDiff > 0) {
        resultByte |= bit >> bitDiff;
      } else {
        resultByte |= bit << -bitDiff;
      }

      if (outputBitPosition == 7) {
        final var outputIndex = outputStartIndex + (bitIndex / 8);
        output[outputIndex] = resultByte;
        resultByte = 0x0;
      }
    }
  }

  public byte calculateChecksum(byte[] input, int startIndex, int endIndex) {
    return (byte)IntStream.range(startIndex, endIndex)
        .map(value -> input[value])
        .reduce(0, Integer::sum);
  }
}
