package com.cassio.nicepay.controller.exception;

import java.time.ZonedDateTime;

public class ApiExceptionSchema {

  private final String message;
  private final Integer status;
  private final String error;
  private final ZonedDateTime timestamp;

  public ApiExceptionSchema(String message, Integer status, String error, ZonedDateTime timestamp) {
    this.message = message;
    this.status = status;
    this.error = error;
    this.timestamp = timestamp;
  }

  public String getMessage() {
    return message;
  }

  public Integer getStatus() {
    return status;
  }

  public String getError() {
    return error;
  }

  public ZonedDateTime getTimestamp() {
    return timestamp;
  }
}