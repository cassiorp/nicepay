package com.cassio.nicepay.exception;

public class BusinessUserTransferException extends RuntimeException {

  public BusinessUserTransferException(String userId) {
    super("Business user cannot transfer: " + userId);
  }
}
