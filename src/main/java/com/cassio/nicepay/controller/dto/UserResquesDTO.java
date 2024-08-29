package com.cassio.nicepay.controller.dto;

import com.cassio.nicepay.controller.validation.Document;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserResquesDTO {

  @NotBlank
  private String fullName;
  @NotBlank
  @Document
  private String document;
  @Email
  private String email;
  @NotBlank
  private String password;

  public UserResquesDTO(String fullName, String document, String email, String password) {
    this.fullName = fullName;
    this.document = document;
    this.email = email;
    this.password = password;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getDocument() {
    return document;
  }

  public void setDocument(String document) {
    this.document = document;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
