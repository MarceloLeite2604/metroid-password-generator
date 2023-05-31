package com.github.marceloleite2604.mpg.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Item implements PasswordBit {
  MARU_MARI("Maru Mari", (short)0),
  BOMBS("Bombs", (short)6),
  VARIA("Varia", (short)11),
  HIGH_JUMP_BOOTS("High Jump Boots", (short)24),
  SCREW_ATTACK("Screw Attack", (short)26);

  @JsonValue
  private final String value;

  private final short bit;
}
