package com.github.marceloleite2604.mpg.model.progress;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.marceloleite2604.mpg.model.*;
import com.github.marceloleite2604.mpg.model.serdes.UnsignedIntegerDeserializer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Jacksonized
@Getter
public class GameProgress {

  private final Set<Item> items;

  private final Set<Acquisition> acquisitions;

  private final Set<EnergyTank> energyTanks;

  private final Set<MissileContainer> missileContainers;

  private final Doors doors;

  private final Set<Kill> kills;

  private final Start start;

  @JsonDeserialize(using = UnsignedIntegerDeserializer.class)
  private final int gameAge;

  private final byte missileCount;

  private final BossStatus ridley;

  private final BossStatus kraid;

  private final boolean swimsuit;
}
