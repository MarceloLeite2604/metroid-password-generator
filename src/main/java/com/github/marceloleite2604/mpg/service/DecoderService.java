package com.github.marceloleite2604.mpg.service;

import com.github.marceloleite2604.mpg.mapper.PasswordBitListByClassMapToGameProgressMapper;
import com.github.marceloleite2604.mpg.model.Game;
import com.github.marceloleite2604.mpg.model.Password;
import com.github.marceloleite2604.mpg.model.PasswordBit;
import com.github.marceloleite2604.mpg.model.progress.BossStatus;
import com.github.marceloleite2604.mpg.model.progress.GameProgress;
import com.github.marceloleite2604.mpg.options.ProgramOptions;
import com.github.marceloleite2604.mpg.util.ByteUtil;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DecoderService {

  private final PasswordBitListByClassMapToGameProgressMapper passwordBitListByClassMapToGameProgressMapper;

  public GameProgress decode(ProgramOptions programOptions) {

    final var password = programOptions.getPassword();

    final var formattedCharacters = password.replace(" ", "");

    final var alphabetByteObjects = Arrays.stream(formattedCharacters.split("(?!^)"))
        .map(character -> Optional.ofNullable(Game.ALPHABET.get(character))
            .orElseThrow(() -> {
              final var message = String.format("Could not find a password alphabet characters matching \"%s\".", character);
              return new IllegalArgumentException(message);
            }))
        .map(Short::byteValue)
        .toList();

    final var alphabetBytes = new byte[Password.ALPHABET_SIZE];

    for (int index = 0; index < alphabetByteObjects.size(); index++) {
      alphabetBytes[index] = alphabetByteObjects.get(index);
    }

    final var dataBytes = new byte[Password.DATA_SIZE];

    for (int index = 1; index < Password.DATA_SIZE; index++) {
      if (dataBytes[index] == (byte) 0xff) {
        dataBytes[index - 1] = (byte) (dataBytes[index - 1] | 0x03);
        dataBytes[index] = Game.ALPHABET.get("-")
            .byteValue();
      }
    }

    ByteUtil.join(alphabetBytes, 0, alphabetBytes.length, 6, dataBytes, 0);

    final int rotationTimes = dataBytes[Password.ROTATION_BYTE_INDEX] & 0xff;

    ByteUtil.rotateLeft(dataBytes, Password.StateBytes.START_INDEX, Password.StateBytes.END_INDEX, rotationTimes);

    byte checksum = ByteUtil.calculateCheckSum(dataBytes, Password.StateBytes.START_INDEX, Password.StateBytes.END_INDEX);

    if (checksum != dataBytes[Password.CHECKSUM_BYTE_INDEX]) {
      throw new IllegalStateException("Password checksum does not match.");
    }

    final Map<Class<? extends PasswordBit>, List<PasswordBit>> passwordBitsByType = IntStream.range(0, (Password.StateBytes.END_INDEX * 8) - 1)
        .mapToObj(index -> {
          final var bitIndex = index % 8;
          final var byteIndex = index / 8;

          if (((dataBytes[byteIndex] & 0xff) & 0x80 >> bitIndex) == 0) {
            return null;
          }

          return Game.BITS.get((short) index);
        })
        .filter(Objects::nonNull)
        .collect(Collectors.groupingBy(PasswordBit::getClass));

    var gameProgress = passwordBitListByClassMapToGameProgressMapper.mapTo(passwordBitsByType)
        .orElseThrow();

    final var armorless = (dataBytes[8] & 0x01) != 0;
    final var missileCount = dataBytes[10];

    final var gameAge = (
        dataBytes[11] << 24 |
        dataBytes[12] << 16 |
        dataBytes[13] << 8 |
        dataBytes[14]);

    // The game age least significant byte overflows at 208, so anything greater than that is invalid.
    final var gameAgeLsb = (dataBytes[14] & 0xff);
    if (gameAgeLsb > 208) {
      final var message = String.format("Game age value least significant byte is invalid: %d (%s)", gameAgeLsb, Integer.toBinaryString(gameAgeLsb));
      throw new IllegalStateException(message);
    }

    checkBossStatusBits(dataBytes[15], 0x8);

    final var ridley = checkBossStatusBits(dataBytes[15], 3);
    final var kraid = checkBossStatusBits(dataBytes[15], 1);

    gameProgress = gameProgress.toBuilder()
        .armorless(armorless)
        .missileCount(missileCount)
        .gameAge(gameAge)
        .ridley(ridley)
        .kraid(kraid)
        .build();

    return gameProgress;
  }

  private BossStatus checkBossStatusBits(byte value, int bitIndex) {
    var bitMask = 0x1 << bitIndex;
    final var killed = (value & bitMask) == bitMask;
    bitMask >>= 1;
    final var statueRaised = (value & bitMask) == bitMask;
    return BossStatus.builder()
        .killed(killed)
        .statueRaised(statueRaised)
        .build();
  }

}
