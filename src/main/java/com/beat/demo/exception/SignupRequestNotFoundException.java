package com.beat.demo.exception;

public class SignupRequestNotFoundException extends RuntimeException {

  public SignupRequestNotFoundException(String message) {
    super(message);
  }
}
