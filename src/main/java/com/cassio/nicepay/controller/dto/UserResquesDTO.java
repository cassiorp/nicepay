package com.cassio.nicepay.controller.dto;

import com.cassio.nicepay.entity.User;
import com.cassio.nicepay.entity.UserType;
import com.cassio.nicepay.validation.Document;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

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
  @NotNull
  private UserType userType;

  public UserResquesDTO(String fullName, String document, String email, String password,
      UserType userType) {
    this.fullName = fullName;
    this.document = document;
    this.email = email;
    this.password = password;
    this.userType = userType;
  }

  public UserResquesDTO() {
  }

  public String getFullName() {
    return fullName;
  }

  public String getDocument() {
    return document;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public UserType getUserType() {
    return userType;
  }

}
