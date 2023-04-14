package com.github.marceloleite2604.mpg.mapper;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public interface Mapper<I, O> {

  Optional<O> mapTo(I value);

  default Optional<I> mapFrom(O value) {
    throw new UnsupportedOperationException("Method not implemented.");
  }

  default Collection<O> mapAllTo(Collection<I> values) {

    if (values == null) {
      return Collections.emptyList();
    }

    return values.stream()
        .map(this::mapTo)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
  }

  default Collection<I> mapAllFrom(Collection<O> values) {

    if (values == null) {
      return Collections.emptyList();
    }

    return values.stream()
        .map(this::mapFrom)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
  }
}
