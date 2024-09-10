package com.cassio.nicepay.controller.dto;

import com.cassio.nicepay.entity.UserType;
import com.cassio.nicepay.entity.Wallet;

public class UserResponseDTO {

  private String id;
  private String fullName;
  private String document;
  private String email;
  private UserType userType;
  private Wallet wallet;

  public UserResponseDTO(String id, String fullName, String document, String email,
      UserType userType, Wallet wallet) {
    this.id = id;
    this.fullName = fullName;
    this.document = document;
    this.email = email;
    this.userType = userType;
    this.wallet = wallet;
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

  public UserType getUserType() {
    return userType;
  }

  public Wallet getWallet() {
    return wallet;
  }
}
