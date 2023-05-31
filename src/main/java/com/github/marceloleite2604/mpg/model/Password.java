package com.github.marceloleite2604.mpg.model;

import com.github.marceloleite2604.mpg.util.ByteUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;

@Getter
public class Password {

  public static final int DATA_SIZE = 18;

  public static final int ALPHABET_SIZE = 24;

  public static final int ROTATION_BYTE_INDEX = 16;

  public static final int CHECKSUM_BYTE_INDEX = 17;

  public Password() {
    this.data = new byte[DATA_SIZE];
    Arrays.fill(this.data, (byte) 0x0);

    this.characters = Strings.EMPTY;

//    this.alphabet = new byte[ALPHABET_SIZE];
//    Arrays.fill(this.alphabet, (byte) 0x0);
  }

  private final byte[] data;

  @Setter
  private String characters;

//  private final byte[] alphabet;

//  public byte[] getStateBytes() {
//    return Arrays.copyOfRange(data, 0, STATE_BYTES_SIZE);
//  }

//  public void setStateBytes(byte[] stateBytes) {
//    System.arraycopy(stateBytes, 0, data, 0, STATE_BYTES_SIZE);
//  }

  public String dataAsHex() {
    return ByteUtil.asHexString(data);
  }

//  public String alphabetAsHex() {
//    return ByteUtil.asHexString(alphabet);
//  }

  public String formattedCharacters() {
    return String.join(" ", characters.split("(?<=\\G.{6})"));
  }

  @Override
  public String toString() {
    return "{ data: " + dataAsHex() + " characters: " + formattedCharacters() + " }";
  }

  @UtilityClass
  public static class StateBytes {
    public static final int START_INDEX = 0;
    public static final int END_INDEX = 16; // End exclusive

    public static final int SIZE = END_INDEX - START_INDEX;
  }
}
