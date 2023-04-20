package com.github.marceloleite2604.mpg.model;

import com.github.marceloleite2604.mpg.model.progress.BossStatus;
import com.github.marceloleite2604.mpg.model.progress.GameProgress;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Password {

  private static final int BYTES_TO_ROTATE = 16;

  private static final Random RANDOM = new SecureRandom();

  public static final Map<String, Short> ALPHABET = Map.ofEntries(
      Map.entry("0", (short) 0),
      Map.entry("1", (short) 1),
      Map.entry("2", (short) 2),
      Map.entry("3", (short) 3),
      Map.entry("4", (short) 4),
      Map.entry("5", (short) 5),
      Map.entry("6", (short) 6),
      Map.entry("7", (short) 7),
      Map.entry("8", (short) 8),
      Map.entry("9", (short) 9),
      Map.entry("A", (short) 10),
      Map.entry("B", (short) 11),
      Map.entry("C", (short) 12),
      Map.entry("D", (short) 13),
      Map.entry("E", (short) 14),
      Map.entry("F", (short) 15),
      Map.entry("G", (short) 16),
      Map.entry("H", (short) 17),
      Map.entry("I", (short) 18),
      Map.entry("J", (short) 19),
      Map.entry("K", (short) 20),
      Map.entry("L", (short) 21),
      Map.entry("M", (short) 22),
      Map.entry("N", (short) 23),
      Map.entry("O", (short) 24),
      Map.entry("P", (short) 25),
      Map.entry("Q", (short) 26),
      Map.entry("R", (short) 27),
      Map.entry("S", (short) 28),
      Map.entry("T", (short) 29),
      Map.entry("U", (short) 30),
      Map.entry("V", (short) 31),
      Map.entry("W", (short) 32),
      Map.entry("X", (short) 33),
      Map.entry("Y", (short) 34),
      Map.entry("Z", (short) 35),
      Map.entry("a", (short) 36),
      Map.entry("b", (short) 37),
      Map.entry("c", (short) 38),
      Map.entry("d", (short) 39),
      Map.entry("e", (short) 40),
      Map.entry("f", (short) 41),
      Map.entry("g", (short) 42),
      Map.entry("h", (short) 43),
      Map.entry("i", (short) 44),
      Map.entry("j", (short) 45),
      Map.entry("k", (short) 46),
      Map.entry("l", (short) 47),
      Map.entry("m", (short) 48),
      Map.entry("n", (short) 49),
      Map.entry("o", (short) 50),
      Map.entry("p", (short) 51),
      Map.entry("q", (short) 52),
      Map.entry("r", (short) 53),
      Map.entry("s", (short) 54),
      Map.entry("t", (short) 55),
      Map.entry("u", (short) 56),
      Map.entry("v", (short) 57),
      Map.entry("w", (short) 58),
      Map.entry("x", (short) 59),
      Map.entry("y", (short) 60),
      Map.entry("z", (short) 61),
      Map.entry("?", (short) 62),
      Map.entry("-", (short) 63),
      Map.entry(" ", (short) 255)
  );

  private static final Map<Short, PasswordBit> BITS;

  static {
    List<PasswordBit> passwordBits = new LinkedList<>(List.of(Item.values()));

    passwordBits.addAll(List.of(Acquisition.values()));
    passwordBits.addAll(List.of(EnergyTank.values()));
    passwordBits.addAll(List.of(Kill.values()));
    passwordBits.addAll(List.of(MissileContainer.values()));
    passwordBits.addAll(List.of(RedDoor.values()));
    passwordBits.addAll(List.of(Start.StartBit.values()));
    passwordBits.addAll(List.of(YellowDoor.values()));

    BITS = passwordBits.stream()
        .collect(Collectors.toMap(PasswordBit::getBit, Function.identity()));
  }

  public String encode(GameProgress gameProgress) {
    byte[] passwordBytes = checkPasswordBytes(gameProgress);

    printBytes(passwordBytes);
    return null;
  }

  private byte[] checkPasswordBytes(GameProgress gameProgress) {

    final var passwordBytes = new byte[]{
        0x0, 0x0, 0x0, 0x0,
        0x0, 0x0, 0x0, 0x0,
        0x0, 0x0, 0x0, 0x0,
        0x0, 0x0, 0x0, 0x0,
        0x0, 0x0};

    checkPasswordBits(passwordBytes, gameProgress);

    gameProgress.getStart()
        .getStartBits()
        .forEach(passwordBit -> activateBit(passwordBytes, passwordBit.getBit()));

    if (gameProgress.isSwimsuit()) {
      passwordBytes[8] |= 0x01;
    }

    passwordBytes[10] = gameProgress.getMissileCount();

    final var gameAge = gameProgress.getGameAge();

    for (int index = 0; index < Integer.BYTES; index++) {
      passwordBytes[11 + index] = (byte) (gameAge >> 8 * index);
    }

    checkBossStatusBits(passwordBytes, gameProgress.getRidley(), 2);
    checkBossStatusBits(passwordBytes, gameProgress.getKraid(), 0);

    calculateAndSetChecksum(passwordBytes);

    printBytes(passwordBytes);

    rotateBits(passwordBytes);

    printBytes(passwordBytes);

    final var alphabetBytes = splitBits(passwordBytes);

    printBytes(alphabetBytes);

    final var stringBuilder = new StringBuilder();
    for (byte alphabetByte : alphabetBytes) {
      final var character = ALPHABET.entrySet()
          .stream()
          .filter(entry -> entry.getValue()
              .equals((short) alphabetByte))
          .findFirst()
          .orElseThrow(() -> new IllegalStateException(String.format("Alphabet value %d does not exist.", (short)alphabetByte)))
          .getKey();
      stringBuilder.append(character);
    }

    System.out.println(stringBuilder);

    return passwordBytes;
  }

  private void printBytes(byte[] bytes) {
    List<String> bytesAsString = new ArrayList<>();
    for (byte passwordByte : bytes) {
      bytesAsString.add(String.format("%02x", passwordByte));
    }

    System.out.println(String.join(" ", bytesAsString));
  }

  private void rotateBits(byte[] passwordBytes) {
    final var totalBits = 8 * passwordBytes.length;

    final var rotationByteArray = new byte[]{0x0};
    RANDOM.nextBytes(rotationByteArray);

    final var rotationTimes = rotationByteArray[0] % totalBits;
    System.out.println(String.format("Will rotate bits %02x times.", rotationTimes));

    final var bytesToRotate = Arrays.copyOfRange(passwordBytes, 0, BYTES_TO_ROTATE);

    for (int rotationIterator = 0; rotationIterator < rotationTimes; rotationIterator++) {
      byte msb = (byte)((bytesToRotate[BYTES_TO_ROTATE - 1] & 0x01) << 7);

      for (int byteIterator = 0; byteIterator < BYTES_TO_ROTATE; byteIterator++) {
        byte nextMsb = (byte)((bytesToRotate[byteIterator] & 0x01) << 7);
        byte rotatedByte = (byte)((bytesToRotate[byteIterator] & 0xff) >> 1);
        rotatedByte |= msb;
        bytesToRotate[byteIterator] = rotatedByte;
        msb = nextMsb;
      }
    }

    System.arraycopy(bytesToRotate, 0, passwordBytes, 0, BYTES_TO_ROTATE);
  }

  private byte[] splitBits(byte[] passwordBytes) {
    final var totalBits = passwordBytes.length * 8;
    final var totalBytesResult = totalBits / 6;
    final var result = new byte[totalBytesResult];

    byte resultByte = 0x0;
    for (int bitIterator = 0; bitIterator < totalBits; bitIterator++) { // 6
      final var byteIndex = bitIterator / 8; // 0
      final var bitIndex = bitIterator % 8; // 0 1 2 3 4 5  6  7 0 1 2 3  4  5  6  7
                                            // 2 3 4 5 6 7  2  3 4 5 6 7  2  3  4  5
                                            // 2 2 2 2 2 2 -4 -4 4 4 4 4 -2 -2 -2 -2
                                            // 2 2 2 2 2 2 8 8

      final var bit = (passwordBytes[byteIndex] & 0xff) & (0x80 >> bitIndex);

      final var resultBitPosition = (bitIterator % 6) + 2;
      final var bitDiff = resultBitPosition - bitIndex;
      if (bitDiff > 0) {
        resultByte |= bit >> bitDiff;
      } else {
        resultByte |= bit << -bitDiff;
      }

      if (resultBitPosition == 7) {
        result[bitIterator / 6] = resultByte;
        resultByte = 0x0;
      }
    }

    return result;
  }

  private void calculateAndSetChecksum(byte[] passwordBytes) {
    byte checksum = 0;

    for (int index = 0; index < 16; index++) {
      checksum += passwordBytes[index];
    }

    passwordBytes[17] = checksum;
  }

  private void checkPasswordBits(byte[] passwordBytes, GameProgress gameProgress) {
    final List<PasswordBit> gameProgressPasswordBits = new ArrayList<>(gameProgress.getItems());
    gameProgressPasswordBits.addAll(gameProgress.getAcquisitions());
    gameProgressPasswordBits.addAll(gameProgress.getEnergyTanks());
    gameProgressPasswordBits.addAll(gameProgress.getKills());
    gameProgressPasswordBits.addAll(gameProgress.getMissileContainers());
    gameProgressPasswordBits.addAll(gameProgress.getDoors()
        .getReds());
    gameProgressPasswordBits.addAll(gameProgress.getDoors()
        .getYellows());

    gameProgressPasswordBits.forEach(passwordBit -> activateBit(passwordBytes, passwordBit.getBit()));
  }

  private void checkBossStatusBits(byte[] passwordBytes, BossStatus bossStatus, int startBit) {
    if (bossStatus.isStatueRaised()) {
      passwordBytes[15] |= 0x01 << startBit;
    }

    if (bossStatus.isKilled()) {
      passwordBytes[15] |= 0x02 << startBit;
    }
  }

  private void activateBit(byte[] passwordBytes, short passwordBit) {
    final var bit = (short) (passwordBit % (short) 8);
    final var pByte = (short) (passwordBit / (short) 8);
    passwordBytes[pByte] |= (0x80 >> bit);
  }
}
