package com.github.marceloleite2604.mpg.service;

import com.github.marceloleite2604.mpg.configuration.ObjectMapperWrapper;
import com.github.marceloleite2604.mpg.model.Password;
import com.github.marceloleite2604.mpg.model.progress.GameProgress;
import com.github.marceloleite2604.mpg.options.ProgramOptions;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class EncoderService {

  private final ObjectMapperWrapper objectMapperWrapper;

  private final Password password;

  public void encode(ProgramOptions programOptions) {
    final var gameProgress = readInputFile(programOptions);
    password.encode(gameProgress);
  }

  private GameProgress readInputFile(ProgramOptions programOptions) {
    final var inputFile = new File(programOptions.getFilePath());

    try {
      return objectMapperWrapper.getInstance()
          .readValue(inputFile, GameProgress.class);
    } catch (IOException ioException) {
      final var message = String.format("Exception thrown while reading input file \"%s\".", programOptions.getFilePath());
      throw new IllegalStateException(message, ioException);
    }
  }
}
