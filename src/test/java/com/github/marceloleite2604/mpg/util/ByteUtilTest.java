package com.github.marceloleite2604.mpg.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

class ByteUtilTest {

  @Test
  void shouldReturnJoinedBytes() {
    final var input = new byte[]{(byte)0x3F, (byte)0x30, (byte)0x03, (byte)0x3F, (byte)0x00, (byte)0x0F, (byte)0x3C, (byte)0x00};

    final var output = new byte[6];

    Arrays.fill(output, (byte)0x00);

    ByteUtil.join(input, 0, input.length, 6, output, 0);

    System.out.println(ByteUtil.asHexString(output));
  }

  @Test
  void shouldReturnBytesRightRotated() {
    final var input = new byte[]{
        (byte)0x00, (byte)0x11, (byte)0x22, (byte)0x33,
        (byte)0x44, (byte)0x55, (byte)0x66, (byte)0x77,
        (byte)0x88, (byte)0x99, (byte)0xAA, (byte)0xBB,
        (byte)0xCC, (byte)0xDD, (byte)0xEE, (byte)0xFF};

    ByteUtil.rotateRight(input, 0, input.length, 8);

    System.out.println(ByteUtil.asHexString(input));
  }

  @Test
  void shouldReturnBytesLeftRotated() {
    final var input = new byte[]{
        (byte)0x00, (byte)0x11, (byte)0x22, (byte)0x33,
        (byte)0x44, (byte)0x55, (byte)0x66, (byte)0x77,
        (byte)0x88, (byte)0x99, (byte)0xAA, (byte)0xBB,
        (byte)0xCC, (byte)0xDD, (byte)0xEE, (byte)0xFF};

    ByteUtil.rotateLeft(input, 0, input.length, 4);

    System.out.println(ByteUtil.asHexString(input));
  }

}