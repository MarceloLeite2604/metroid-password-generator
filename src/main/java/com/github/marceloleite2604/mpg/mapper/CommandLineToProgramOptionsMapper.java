package com.github.marceloleite2604.mpg.mapper;

import com.github.marceloleite2604.mpg.exception.InvalidProgramOptionsException;
import com.github.marceloleite2604.mpg.options.CliOptions;
import com.github.marceloleite2604.mpg.options.Operation;
import com.github.marceloleite2604.mpg.options.ProgramOptions;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.apache.commons.cli.CommandLine;

import java.io.File;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CommandLineToProgramOptionsMapper implements Mapper<CommandLine, ProgramOptions> {

  @Override
  public Optional<ProgramOptions> mapTo(CommandLine commandLine) {
    if (commandLine == null) {
      return Optional.empty();
    }

    final var operation = retrieveOperation(commandLine);

    if (Operation.ENCODE.equals(operation)) {
      return createOptionalEncodeProgramOptions(commandLine);
    }

    return createOptionalDecodeProgramOptions(commandLine);
  }

  private Optional<ProgramOptions> createOptionalDecodeProgramOptions(CommandLine commandLine) {
    if (!commandLine.hasOption(CliOptions.PASSWORD)) {
      final var message = String.format("Option \"--%s\" must be used along with \"--%s\" to define the password value.",
          CliOptions.DECODE.getLongOpt(),
          CliOptions.PASSWORD.getLongOpt());
      throw new InvalidProgramOptionsException(message);
    }

    final var password = commandLine.getOptionValue(CliOptions.PASSWORD);

    var programOptionsBuilder = ProgramOptions.builder()
        .operation(Operation.DECODE)
        .password(password);

    if (commandLine.hasOption(CliOptions.OUTPUT_FILE)) {
      final var filePath = commandLine.getOptionValue(CliOptions.OUTPUT_FILE);
      programOptionsBuilder = programOptionsBuilder.filePath(filePath);
    }

    final var programOptions = programOptionsBuilder.build();

    return Optional.of(programOptions);
  }

  private Optional<ProgramOptions> createOptionalEncodeProgramOptions(CommandLine commandLine) {

    if (!commandLine.hasOption(CliOptions.INPUT_FILE)) {
      final var message = String.format("Option \"--%s\" must be used along with \"--%s\" to define the input file.",
          CliOptions.ENCODE.getLongOpt(),
          CliOptions.INPUT_FILE.getLongOpt());
      throw new InvalidProgramOptionsException(message);
    }

    final var inputFile = commandLine.getOptionValue(CliOptions.INPUT_FILE);

    final var file = new File(inputFile);
    if (!file.exists() || file.isDirectory() || !file.canRead()) {
      final var message = String.format("Cannot read file \"%s\".", inputFile);
      throw new InvalidProgramOptionsException(message);
    }

    final var programOptions = ProgramOptions.builder()
        .operation(Operation.ENCODE)
        .filePath(inputFile)
        .build();

    return Optional.of(programOptions);
  }

  private Operation retrieveOperation(CommandLine commandLine) {
    if (commandLine.hasOption(CliOptions.ENCODE)) {
      return Operation.ENCODE;
    } else if (commandLine.hasOption(CliOptions.DECODE)) {
      return Operation.DECODE;
    } else {
      final var message = String.format("The program must receive either \"--%s\" or \"--%s\" option.",
          CliOptions.ENCODE.getLongOpt(),
          CliOptions.DECODE.getLongOpt());
      throw new InvalidProgramOptionsException(message);
    }
  }
}
