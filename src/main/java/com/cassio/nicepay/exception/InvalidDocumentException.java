package com.cassio.nicepay.exception;

public class InvalidDocumentException extends RuntimeException {

  public InvalidDocumentException() {
    super("Invalid document, use cpf or cnpj format");
  }
}
