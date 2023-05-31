package com.github.marceloleite2604.mpg.service;

import com.github.marceloleite2604.mpg.configuration.ObjectMapperWrapper;
import com.github.marceloleite2604.mpg.model.Game;
import com.github.marceloleite2604.mpg.model.Password;
import com.github.marceloleite2604.mpg.model.progress.BossStatus;
import com.github.marceloleite2604.mpg.model.progress.GameProgress;
import com.github.marceloleite2604.mpg.options.ProgramOptions;
import com.github.marceloleite2604.mpg.util.ByteUtil;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class EncoderService {

  private static final Random RANDOM = new SecureRandom();

  private final ObjectMapperWrapper objectMapperWrapper;

  public Password encode(ProgramOptions programOptions) {
    final var gameProgress = readInputFile(programOptions);

    final var password = elaboratePasswordData(gameProgress);

    elaboratePasswordCharacters(password);

    log.debug("Password characters: {}", password.formattedCharacters());

    return password;
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

  private Password elaboratePasswordData(GameProgress gameProgress) {

    final var password = new Password();

    gameProgress.retrievePasswordBits()
        .forEach(passwordBit -> activateBit(password, passwordBit.getBit()));

    gameProgress.getStart()
        .getStartBits()
        .forEach(passwordBit -> activateBit(password, passwordBit.getBit()));

    if (gameProgress.isArmorless()) {
      password.getData()[8] |= 0x80;
    }

    password.getData()[10] = gameProgress.getMissileCount();

    final var gameAge = gameProgress.getGameAge();

    final var gameAgeBytes = ByteBuffer.allocate(Integer.BYTES)
        .putInt(Integer.reverse(gameAge))
        .array();

    System.arraycopy(gameAgeBytes, 0, password.getData(), 11, Integer.BYTES);

    checkBossStatusBits(password, gameProgress.getRidley(), 4);
    checkBossStatusBits(password, gameProgress.getKraid(), 6);

    elaborateAndSetRotationTimes(password);

    calculateAndSetChecksum(password);

    log.debug("Password data after define checksum: {}", password.dataAsHex());

    rotateBits(password);

    log.debug("Password data after bit rotation: {}", password.dataAsHex());

    return password;
  }

  private void elaborateAndSetRotationTimes(Password password) {
    final var totalBits = 8 * password.getData().length;
    final var rotationTimes = (RANDOM.nextInt() & 0xff) % totalBits;
    password.getData()[Password.ROTATION_BYTE_INDEX] = (byte) rotationTimes;
  }

  private void activateBit(Password password, short passwordBit) {
    final var bit = (short) (passwordBit % (short) 8);
    final var pByte = (short) (passwordBit / (short) 8);
    password.getData()[pByte] |= (0x01 << bit);
  }

  private void checkBossStatusBits(Password password, BossStatus bossStatus, int startBit) {
    if (bossStatus.isStatueRaised()) {
      password.getData()[15] |= 0x01 << startBit;
    }

    if (bossStatus.isKilled()) {
      password.getData()[15] |= 0x02 << startBit;
    }
  }

  private void calculateAndSetChecksum(Password password) {
    byte checksum = ByteUtil.calculateChecksum(password.getData(), Password.StateBytes.START_INDEX, Password.StateBytes.END_INDEX + 1);

    password.getData()[Password.CHECKSUM_BYTE_INDEX] = checksum;
  }

  private void rotateBits(Password password) {
    final var rotationTimes = password.getData()[Password.ROTATION_BYTE_INDEX];
    log.debug("Will rotate bits {} times.", rotationTimes);

    ByteUtil.rotateRight(password.getData(), Password.StateBytes.START_INDEX, Password.StateBytes.END_INDEX, rotationTimes);
  }

  private void elaboratePasswordCharacters(Password password) {

    final var alphaBytes = new byte[Password.ALPHABET_SIZE];
    ByteUtil.split(password.getData(), 0, password.getData().length, 6, alphaBytes, 0);
    log.debug("Alphabet bytes: {}", ByteUtil.asHexString(alphaBytes));

    final var characters = translateBytesIntoAlphabetCharacters(alphaBytes);

    password.setCharacters(characters);
  }

  private String translateBytesIntoAlphabetCharacters(byte[] alphaBytes) {
    final var charactersStringBuilder = new StringBuilder();
    for (byte alphaByte : alphaBytes) {
      final var character = Game.ALPHABET.entrySet()
          .stream()
          .filter(entry -> entry.getValue()
              .equals((short) alphaByte))
          .findFirst()
          .orElseThrow(() -> new IllegalStateException(String.format("Alphabet value %d does not exist.", (short) alphaByte)))
          .getKey();
      charactersStringBuilder.append(character);
    }
    return charactersStringBuilder.toString();
  }
}
