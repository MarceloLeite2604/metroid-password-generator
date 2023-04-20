package com.github.marceloleite2604.mpg.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import jakarta.inject.Inject;
import lombok.Getter;

@Getter
public class ObjectMapperWrapper {

  private final ObjectMapper instance;

  @Inject
  public ObjectMapperWrapper() {
    this.instance = createObjectMapper();
  }

  private ObjectMapper createObjectMapper() {
    final var objectMapper = new ObjectMapper();
    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.KEBAB_CASE);
    return objectMapper;
  }
}
