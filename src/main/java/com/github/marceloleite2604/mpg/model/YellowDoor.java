package com.github.marceloleite2604.mpg.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.util.Assert;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum YellowDoor implements PasswordBit {

  RIDLEYS_ROOM("Ridley's Room", (short)47),
  TOURIAN("Tourian", (short)50);

  @JsonValue
  private final String value;

  private final short bit;

  public static YellowDoor findByValue(String value) {
    Assert.requireNonEmpty(value, "Value cannot be null or empty.");

    for (YellowDoor yellowDoor : values()) {
      if (value.equalsIgnoreCase(yellowDoor.value)) {
        return yellowDoor;
      }
    }

    final var message = String.format("Could not find a yellow door with value \"%s\".", value);
    throw new IllegalArgumentException(message);
  }
}
