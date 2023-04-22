package com.github.marceloleite2604.mpg.model.progress;

import com.github.marceloleite2604.mpg.model.PasswordBit;
import com.github.marceloleite2604.mpg.model.RedDoor;
import com.github.marceloleite2604.mpg.model.YellowDoor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Jacksonized
@Getter
public class Doors {

  private final Set<RedDoor> reds;

  private final Set<YellowDoor> yellows;

  public List<PasswordBit> retrievePasswordBits() {
    return Stream.<PasswordBit>concat(reds.stream(), yellows.stream())
        .toList();
  }
}
