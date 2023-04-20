package com.github.marceloleite2604.mpg;

import com.github.marceloleite2604.mpg.configuration.WeldConfiguration;
import com.github.marceloleite2604.mpg.exception.InvalidProgramOptionsException;
import com.github.marceloleite2604.mpg.options.Operation;
import com.github.marceloleite2604.mpg.options.ProgramOptionsParser;
import com.github.marceloleite2604.mpg.service.EncoderService;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class App {

  private final ProgramOptionsParser programOptionsParser;

  private final EncoderService encoderService;

  private void run(String[] args) {
    try {
      final var programOptions = programOptionsParser.parse(args);

      if (Objects.requireNonNull(programOptions.getOperation()) == Operation.ENCODE) {
        encoderService.encode(programOptions);
      } else {
        System.out.println("Decoding service still being written.");
      }

    } catch (InvalidProgramOptionsException exception) {
      log.error("Exception thrown while parsing options.", exception);
      programOptionsParser.printProgramOptions();
    }
  }

  public static void main(String[] args) {

    try (final var container = WeldConfiguration.createContainer()) {
      container.select(App.class)
          .get()
          .run(args);
    }
  }
}
