package com.github.marceloleite2604.mpg.exception;

public class InvalidProgramOptionsException extends RuntimeException {
  public InvalidProgramOptionsException(String message) {
    super(message);
  }

  public InvalidProgramOptionsException(Throwable cause) {
    super(cause);
  }
}
