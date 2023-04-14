package com.github.marceloleite2604.mpg.options;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProgramOptions {

  private final Operation operation;

  private final String filePath;

  private final String password;
}
