package com.github.marceloleite2604.mpg.mapper;

import com.github.marceloleite2604.mpg.model.*;
import com.github.marceloleite2604.mpg.model.progress.Doors;
import com.github.marceloleite2604.mpg.model.progress.GameProgress;

import java.util.*;

public class PasswordBitListByClassMapToGameProgressMapper implements Mapper<Map<Class<? extends PasswordBit>, List<PasswordBit>>, GameProgress> {
  @Override
  public Optional<GameProgress> mapTo(Map<Class<? extends PasswordBit>, List<PasswordBit>> passwordBitsByClassMap) {
    final Set<Item> items = retrievePasswordBitsAsSet(passwordBitsByClassMap, Item.class);
    final Set<EnergyTank> energyTanks = retrievePasswordBitsAsSet(passwordBitsByClassMap, EnergyTank.class);
    final Set<Acquisition> acquisitions = retrievePasswordBitsAsSet(passwordBitsByClassMap, Acquisition.class);
    final Set<MissileContainer> missileContainers = retrievePasswordBitsAsSet(passwordBitsByClassMap, MissileContainer.class);
    final Set<Kill> kills = retrievePasswordBitsAsSet(passwordBitsByClassMap, Kill.class);
    final Set<Start.StartBit> startBits = retrievePasswordBitsAsSet(passwordBitsByClassMap, Start.StartBit.class);
    final var start = Start.findByStartBits(startBits);


    final Doors doors = elaborateDoors(passwordBitsByClassMap);

    return Optional.of(GameProgress.builder()
        .items(items)
        .energyTanks(energyTanks)
        .acquisitions(acquisitions)
        .missileContainers(missileContainers)
        .kills(kills)
        .doors(doors)
        .start(start)
        .build());
  }

  private Doors elaborateDoors(Map<Class<? extends PasswordBit>, List<PasswordBit>> passwordBitsByClassMap) {
    final Set<RedDoor> reds = retrievePasswordBitsAsSet(passwordBitsByClassMap, RedDoor.class);
    final Set<YellowDoor> yellows = retrievePasswordBitsAsSet(passwordBitsByClassMap, YellowDoor.class);

    return Doors.builder()
        .reds(reds)
        .yellows(yellows)
        .build();
  }

  @SuppressWarnings("unchecked")
  private <T extends PasswordBit> Set<T> retrievePasswordBitsAsSet(
      Map<Class<? extends PasswordBit>, List<PasswordBit>> passwordBitsByClassMap,
      Class<T> clazz) {
    return new HashSet<>((List<T>) passwordBitsByClassMap.getOrDefault(clazz, Collections.emptyList()));
  }
}
