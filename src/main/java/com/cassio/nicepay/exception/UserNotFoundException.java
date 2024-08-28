package com.cassio.nicepay.exception;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String userId) {
    super("No User found with " + userId);
  }
}
