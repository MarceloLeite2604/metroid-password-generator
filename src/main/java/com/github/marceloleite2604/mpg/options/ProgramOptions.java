package com.github.marceloleite2604.mpg.options;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProgramOptions {

  private final Operation operation;

  private final String filePath;

  private final String password;
}
