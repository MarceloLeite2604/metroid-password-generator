package com.github.marceloleite2604.mpg.model.progress;

import com.github.marceloleite2604.mpg.model.RedDoor;
import com.github.marceloleite2604.mpg.model.YellowDoor;
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
public class Doors {

  private final Set<RedDoor> reds;

  private final Set<YellowDoor> yellows;
}
