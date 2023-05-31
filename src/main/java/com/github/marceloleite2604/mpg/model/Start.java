package com.github.marceloleite2604.mpg.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Start {

  BRINSTAR("Brinstar", Collections.emptySet()),
  NORFAIR("Norfair", Set.of(StartBit.BIT_64)),
  KRAIDS_LAIR("Kraid's Lair", Set.of(StartBit.BIT_65)),
  RIDLEYS_LAIR("Ridley's Lair", Set.of(StartBit.BIT_66)),
  TOURIAN("Tourian", Set.of(StartBit.BIT_64, StartBit.BIT_65));

  @JsonValue
  private final String value;

  private final Set<StartBit> startBits;

  public static Start findByValue(String value) {
    Assert.requireNonEmpty(value, "Value cannot be null or empty.");

    for (Start start : values()) {
      if (value.equalsIgnoreCase(start.value)) {
        return start;
      }
    }

    final var message = String.format("Could not find a start with value \"%s\".", value);
    throw new IllegalArgumentException(message);
  }

  public static Start findByStartBits(Collection<StartBit> startBits) {
    // TODO Should not accept null.
    // Assert.requireNonEmpty(startBits, "Start bits cannot be null or empty.");
    if (startBits.isEmpty()) {
      return Start.BRINSTAR;
    }

    for (Start start : values()) {
      if (start.getStartBits().containsAll(startBits)) {
        return start;
      }
    }

    final var message = String.format("Could not find a start with start bits \"%s\".", startBits);
    throw new IllegalArgumentException(message);
  }

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @Getter
  public enum StartBit implements PasswordBit {
    BIT_64((short) 64),
    BIT_65((short) 65),
    BIT_66((short) 66);

    private final short bit;
  }
}
