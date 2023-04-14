package com.github.marceloleite2604.mpg;

import com.github.marceloleite2604.mpg.exception.InvalidProgramOptionsException;
import com.github.marceloleite2604.mpg.mapper.CommandLineToProgramOptionsMapper;
import com.github.marceloleite2604.mpg.options.ProgramOptionsParser;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class App {

  private final ProgramOptionsParser programOptionsParser;

  private void run(String[] args) {
    try {
      programOptionsParser.parse(args);
    } catch (InvalidProgramOptionsException exception) {
      log.error("Exception thrown while parsing options.", exception);
      programOptionsParser.printProgramOptions();
    }
  }

  public static void main(String[] args) {

    try (final var container = SeContainerInitializer.newInstance()
        .addBeanClasses(
            App.class,
            ProgramOptionsParser.class,
            CommandLineToProgramOptionsMapper.class)
        .initialize()) {
      container.select(App.class)
          .get()
          .run(args);
    }
  }
}
