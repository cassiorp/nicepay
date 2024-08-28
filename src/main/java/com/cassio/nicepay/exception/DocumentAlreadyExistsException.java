package com.cassio.nicepay.exception;

public class DocumentAlreadyExistsException extends RuntimeException {

  public DocumentAlreadyExistsException(String document) {
    super("Document already exists: " + document);
  }
}
