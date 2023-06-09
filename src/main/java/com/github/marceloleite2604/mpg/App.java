package com.github.marceloleite2604.mpg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.marceloleite2604.mpg.configuration.ObjectMapperWrapper;
import com.github.marceloleite2604.mpg.configuration.WeldConfiguration;
import com.github.marceloleite2604.mpg.exception.InvalidProgramOptionsException;
import com.github.marceloleite2604.mpg.model.progress.GameProgress;
import com.github.marceloleite2604.mpg.options.Operation;
import com.github.marceloleite2604.mpg.options.ProgramOptionsParser;
import com.github.marceloleite2604.mpg.service.DecoderService;
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

  private final DecoderService decoderService;

  private final ObjectMapperWrapper objectMapperWrapper;

  @SuppressWarnings("java:S106")
  private void run(String[] args) {
    try {
      final var programOptions = programOptionsParser.parse(args);

      if (Objects.requireNonNull(programOptions.getOperation()) == Operation.ENCODE) {
        final var password = encoderService.encode(programOptions);

        System.out.println("Password characters are: " + password.formattedCharacters());
      } else {
        final var gameProgress = decoderService.decode(programOptions);

        printGameProcess(gameProgress);
      }

    } catch (InvalidProgramOptionsException exception) {
      log.error("Exception thrown while parsing options.", exception);
      programOptionsParser.printProgramOptions();
    }
  }

  @SuppressWarnings("java:S106")
  private void printGameProcess(GameProgress gameProgress) {
    try {
      System.out.println(objectMapperWrapper.getInstance()
          .writerWithDefaultPrettyPrinter()
          .writeValueAsString(gameProgress));
    } catch (JsonProcessingException jsonProcessingException) {
      throw new IllegalStateException("Exception thrown while printing the game progress.", jsonProcessingException);
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
