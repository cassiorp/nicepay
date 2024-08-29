package com.cassio.nicepay.exception;

public class InsufficientBalanceException extends RuntimeException {

  public InsufficientBalanceException(String userId) {
    super("Insufficient balance for user: " + userId);
  }
}
