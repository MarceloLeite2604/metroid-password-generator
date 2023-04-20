package com.github.marceloleite2604.mpg.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.util.Assert;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum RedDoor implements PasswordBit {

  LONG_BEAM("Long Beam", (short)2),
  TOURIAN_BRIDGE("Tourian Bridge", (short)3),
  BOMBS("Bombs", (short)5),
  ICE_BEAM_BRINSTAR("Ice Beam Brinstar", (short)7),
  VARIA("Varia", (short)10),
  ICE_BEAM_NORFAIR("Ice Beam Norfair", (short)15),
  HIGH_JUMP_BOOTS("High Jump Boots", (short)23),
  SCREW_ATTACK("Screw Attack", (short)25),
  WAVE_BEAM("Wave Beam", (short)29),
  KRAIDS_LAIR_1("Kraid's Lair 1", (short)32),
  KRAIDS_LAIR_2("Kraid's Lair 2", (short)35),
  KRAIDS_LAIR_3("Kraid's Lair 3", (short)37),
  KRAIDS_LAIR_4("Kraid's Lair 4", (short)38),
  KRAIDS_ROOM("Kraid's Room", (short)41),
  RIDLEYS_LAIR("Ridley's Lair", (short)44),
  TOURIAN_1("Tourian 1", (short)51),
  TOURIAN_2("Tourian 2", (short)52);

  @JsonValue
  private final String value;

  private final short bit;

  public static RedDoor findByValue(String value) {
    Assert.requireNonEmpty(value, "Value cannot be null or empty.");

    for (RedDoor redDoor : values()) {
      if (value.equalsIgnoreCase(redDoor.value)) {
        return redDoor;
      }
    }

    final var message = String.format("Could not find a red door with value \"%s\".", value);
    throw new IllegalArgumentException(message);
  }
}
