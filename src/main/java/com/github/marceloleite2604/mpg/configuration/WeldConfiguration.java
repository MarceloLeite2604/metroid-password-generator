package com.github.marceloleite2604.mpg.configuration;

import com.github.marceloleite2604.mpg.App;
import com.github.marceloleite2604.mpg.mapper.CommandLineToProgramOptionsMapper;
import com.github.marceloleite2604.mpg.mapper.PasswordBitListByClassMapToGameProgressMapper;
import com.github.marceloleite2604.mpg.model.Password;
import com.github.marceloleite2604.mpg.options.ProgramOptionsParser;
import com.github.marceloleite2604.mpg.service.DecoderService;
import com.github.marceloleite2604.mpg.service.EncoderService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WeldConfiguration {

  private static final Class<?>[] BEAN_CLASSES = {
      App.class,
      ProgramOptionsParser.class,
      CommandLineToProgramOptionsMapper.class,
      ObjectMapperWrapper.class,
      Password.class,
      EncoderService.class,
      PasswordBitListByClassMapToGameProgressMapper.class,
      DecoderService.class,
  };

  public static SeContainer createContainer() {
    return SeContainerInitializer.newInstance()
        .addBeanClasses(BEAN_CLASSES)
        .initialize();
  }
}
