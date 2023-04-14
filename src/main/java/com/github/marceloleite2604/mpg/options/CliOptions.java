package com.github.marceloleite2604.mpg.options;

import lombok.experimental.UtilityClass;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;

@UtilityClass
public class CliOptions {

  public static final Option ENCODE = Option.builder()
      .option("e")
      .longOpt("encode")
      .required(true)
      .hasArg(false)
      .desc("Encode file content into a password.")
      .build();

  public static final Option INPUT_FILE = Option.builder()
      .option("i")
      .longOpt("input-file")
      .required(false)
      .hasArg(true)
      .desc("Input file to be encoded.")
      .build();

  public static final Option DECODE = Option.builder()
      .option("d")
      .longOpt("decode")
      .required(true)
      .hasArg(false)
      .desc("Decode password.")
      .build();

  public static final Option PASSWORD = Option.builder()
      .option("p")
      .longOpt("password")
      .required(false)
      .hasArg(true)
      .desc("password to be decoded.")
      .build();

  public static final Option OUTPUT_FILE = Option.builder()
      .option("o")
      .longOpt("output-file")
      .required(false)
      .hasArg(true)
      .desc("File to output the decoded password.")
      .build();

  public static final Options OPTIONS;

  static {
    final var operationOptionGroup = new OptionGroup().addOption(CliOptions.ENCODE)
        .addOption(CliOptions.DECODE);
    operationOptionGroup.setRequired(true);

    OPTIONS = new Options().addOptionGroup(operationOptionGroup)
        .addOption(INPUT_FILE)
        .addOption(PASSWORD)
        .addOption(OUTPUT_FILE);
  }

}
