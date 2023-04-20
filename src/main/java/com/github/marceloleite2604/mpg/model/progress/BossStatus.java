package com.github.marceloleite2604.mpg.model.progress;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Jacksonized
@Getter
public class BossStatus {

  private boolean killed;

  private boolean statueRaised;
}