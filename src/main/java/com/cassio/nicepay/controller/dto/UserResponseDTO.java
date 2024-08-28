package com.cassio.nicepay.controller.dto;

import com.cassio.nicepay.entity.User;
import com.cassio.nicepay.entity.UserType;

public class UserResponseDTO {
  private String id;
  private String fullName;
  private String document;
  private String email;
  private String password;
  private UserType userType;

  public UserResponseDTO(String id, String fullName, String document, String email,
      String password,
      UserType userType) {
    this.id = id;
    this.fullName = fullName;
    this.document = document;
    this.email = email;
    this.password = password;
    this.userType = userType;
  }

  public UserResponseDTO() {
  }

  public String getId() {
    return id;
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
