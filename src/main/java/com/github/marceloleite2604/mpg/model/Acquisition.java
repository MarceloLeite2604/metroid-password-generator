package com.github.marceloleite2604.mpg.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.util.Assert;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Acquisition implements PasswordBit {
  BOMBS("Bombs", (short)72),
  HIGH_JUMP_BOOTS("High Jump Boots", (short)73),
  LONG_BEAM("Long Beam", (short)74),
  SCREW_ATTACK("Screw Attack", (short)75),
  MARU_MARI("Maru Mari", (short)76),
  VARIA("Varia", (short)77),
  WAVE_BEAM("Wave Beam", (short)78),
  ICE_BEAM("Ice Beam", (short)79);

  @JsonValue
  private final String value;

  private final short bit;

  public static Acquisition findByValue(String value) {
    Assert.requireNonEmpty(value, "Value cannot be null or empty.");

    for (Acquisition item : values()) {
      if (value.equalsIgnoreCase(item.value)) {
        return item;
      }
    }

    final var message = String.format("Could not find an item acquisition with value \"%s\".", value);
    throw new IllegalArgumentException(message);
  }
}
