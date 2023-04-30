package com.github.marceloleite2604.mpg.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Game {

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

  public static final Map<Short, PasswordBit> BITS;

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
}
