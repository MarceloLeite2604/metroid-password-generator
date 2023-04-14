package com.github.marceloleite2604.mpg.options;

import com.github.marceloleite2604.mpg.exception.InvalidProgramOptionsException;
import com.github.marceloleite2604.mpg.mapper.CommandLineToProgramOptionsMapper;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ProgramOptionsParser {

  private static final HelpFormatter HELP_FORMATTER = new HelpFormatter();

  private static final CommandLineParser COMMAND_LINE_PARSER = new DefaultParser();

  private final CommandLineToProgramOptionsMapper commandLineToProgramOptionsMapper;

  public ProgramOptions parse(String... args) {
    try {
      final var commandLine = COMMAND_LINE_PARSER.parse(CliOptions.OPTIONS, args);

      return commandLineToProgramOptionsMapper.mapTo(commandLine)
          .orElseThrow();
    } catch (ParseException parseException) {
      throw new InvalidProgramOptionsException(parseException);
    }
  }

  public void printProgramOptions() {
    HELP_FORMATTER.printHelp("metroid-password-generator", CliOptions.OPTIONS, true);
  }
}
