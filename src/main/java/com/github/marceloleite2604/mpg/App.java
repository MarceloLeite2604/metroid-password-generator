package com.github.marceloleite2604.mpg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.marceloleite2604.mpg.configuration.ObjectMapperWrapper;
import com.github.marceloleite2604.mpg.configuration.WeldConfiguration;
import com.github.marceloleite2604.mpg.exception.InvalidProgramOptionsException;
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

        try {
          System.out.println(objectMapperWrapper.getInstance()
              .writerWithDefaultPrettyPrinter()
              .writeValueAsString(gameProgress));
        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }
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
