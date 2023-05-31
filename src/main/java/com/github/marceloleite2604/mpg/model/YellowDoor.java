package com.github.marceloleite2604.mpg.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum YellowDoor implements PasswordBit {

  RIDLEYS_ROOM("Ridley's Room", (short)47),
  TOURIAN("Tourian", (short)50);

  @JsonValue
  private final String value;

  private final short bit;
}
