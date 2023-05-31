package com.github.marceloleite2604.mpg.mapper;

import java.util.Optional;

public interface Mapper<I, O> {

  Optional<O> mapTo(I value);
}
