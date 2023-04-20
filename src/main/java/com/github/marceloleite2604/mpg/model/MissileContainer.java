package com.github.marceloleite2604.mpg.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.util.Assert;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MissileContainer implements PasswordBit {
  BRINSTAR_1("Brinstar 1", (short)1),
  BRINSTAR_2("Brinstar 2", (short)8),
  NORFAIR_1("Norfair 1", (short)13),
  NORFAIR_2("Norfair 2", (short)14),
  NORFAIR_3("Norfair 3", (short)16),
  NORFAIR_4("Norfair 4", (short)17),
  NORFAIR_5("Norfair 5", (short)18),
  NORFAIR_6("Norfair 6", (short)19),
  NORFAIR_7("Norfair 7", (short)20),
  NORFAIR_8("Norfair 8", (short)21),
  NORFAIR_9("Norfair 9", (short)22),
  NORFAIR_10("Norfair 10", (short)27),
  NORFAIR_11("Norfair 11", (short)28),
  NORFAIR_12("Norfair 12", (short)31),
  KRAIDS_LAIR_1("Kraid's Lair 1", (short)33),
  KRAIDS_LAIR_2("Kraid's Lair 2", (short)34),
  KRAIDS_LAIR_3("Kraid's Lair 3", (short)39),
  KRAIDS_LAIR_4("Kraid's Lair 4", (short)40),
  RIDLEYS_LAIR_1("Ridley's Lair 1", (short)43),
  RIDLEYS_LAIR_2("Ridley's Lair 2", (short)46),
  RIDLEYS_LAIR_3("Ridley's Lair 3", (short)49);

  @JsonValue
  private final String value;

  private final short bit;

  public static MissileContainer findByValue(String value) {
    Assert.requireNonEmpty(value, "Value cannot be null or empty.");

    for (MissileContainer missileContainer : values()) {
      if (value.equalsIgnoreCase(missileContainer.value)) {
        return missileContainer;
      }
    }

    final var message = String.format("Could not find a missile container with value \"%s\".", value);
    throw new IllegalArgumentException(message);
  }
}
