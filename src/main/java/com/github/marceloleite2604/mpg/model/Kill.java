package com.github.marceloleite2604.mpg.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.util.Assert;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Kill implements PasswordBit {

  ZEBETITE_1("Zebetite 1", (short)53),
  ZEBETITE_2("Zebetite 2", (short)54),
  ZEBETITE_3("Zebetite 3", (short)55),
  ZEBETITE_4("Zebetite 4", (short)56),
  ZEBETITE_5("Zebetite 5", (short)57),
  MOTHER_BRAIN("Mother Brain", (short)58);

  @JsonValue
  private final String value;

  private final short bit;

  public static Kill findByValue(String value) {
    Assert.requireNonEmpty(value, "Value cannot be null or empty.");

    for (Kill kill : values()) {
      if (value.equalsIgnoreCase(kill.value)) {
        return kill;
      }
    }

    final var message = String.format("Could not find a kill with value \"%s\".", value);
    throw new IllegalArgumentException(message);
  }
}
