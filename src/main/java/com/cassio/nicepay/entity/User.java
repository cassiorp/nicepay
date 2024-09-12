package com.cassio.nicepay.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

  @Id
  private String id;
  private String fullName;
  @Indexed(unique = true)
  private String document;
  @Indexed(unique = true)
  private String email;
  private String password;
  private UserType userType;
  private Wallet wallet;

  public User() {
  }

  public User(String fullName, String document, String email, String password, UserType userType) {
    this.fullName = fullName;
    this.document = document;
    this.email = email;
    this.password = password;
    this.userType = userType;
  }

  public User(String id, String fullName, String document, String email, String password,
      UserType userType) {
    this.id = id;
    this.fullName = fullName;
    this.document = document;
    this.email = email;
    this.password = password;
    this.userType = userType;
  }

  public User(String fullName, String document, String email, String password, UserType userType,
      Wallet wallet) {
    this.fullName = fullName;
    this.document = document;
    this.email = email;
    this.password = password;
    this.userType = userType;
    this.wallet = wallet;
  }

  public User(String id, String fullName, String document, String email, String password,
      UserType userType, Wallet wallet) {
    this.id = id;
    this.fullName = fullName;
    this.document = document;
    this.email = email;
    this.password = password;
    this.userType = userType;
    this.wallet = wallet;
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

  public void setPassword(String password) {
    this.password = password;
  }

  public UserType getUserType() {
    return userType;
  }

  public void setWallet(Wallet wallet) {
    this.wallet = wallet;
  }

  public Wallet getWallet() {
    return wallet;
  }

}
