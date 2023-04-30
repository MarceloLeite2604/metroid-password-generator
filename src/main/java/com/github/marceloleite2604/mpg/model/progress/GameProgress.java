package com.github.marceloleite2604.mpg.model.progress;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.marceloleite2604.mpg.model.*;
import com.github.marceloleite2604.mpg.model.serdes.ByteAsUnsignedShortSerializer;
import com.github.marceloleite2604.mpg.model.serdes.UnsignedIntegerDeserializer;
import com.github.marceloleite2604.mpg.model.serdes.UnsignedIntegerSerializer;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder(toBuilder = true)
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
  @JsonSerialize(using = UnsignedIntegerSerializer.class)
  private final int gameAge;

  @JsonSerialize(using = ByteAsUnsignedShortSerializer.class)
  private final byte missileCount;

  private final BossStatus ridley;

  private final BossStatus kraid;

  private final boolean armorless;

  public List<PasswordBit> retrievePasswordBits() {
    final List<PasswordBit> passwordBits = new ArrayList<>(items);
    passwordBits.addAll(acquisitions);
    passwordBits.addAll(energyTanks);
    passwordBits.addAll(kills);
    passwordBits.addAll(missileContainers);
    passwordBits.addAll(doors.retrievePasswordBits());
    return passwordBits;
  }
}
