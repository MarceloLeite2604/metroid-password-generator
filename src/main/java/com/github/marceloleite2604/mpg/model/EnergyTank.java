package com.github.marceloleite2604.mpg.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EnergyTank implements PasswordBit {
  BRINSTAR_1("Brinstar 1", (short)4),
  BRINSTAR_2("Brinstar 2", (short)9),
  BRINSTAR_3("Brinstar 3", (short)12),
  NORFAIR("Norfair", (short)30),
  KRAIDS_LAIR("Kraid's Lair", (short)36),
  KRAIDS_ROOM("Kraid's Room", (short)42),
  RIDLEYS_LAIR("Ridley's Lair", (short)45),
  ROOM_BEHIND_RIDLEY("Room Behind Ridley", (short)48);

  @JsonValue
  private final String value;

  private final short bit;
}
