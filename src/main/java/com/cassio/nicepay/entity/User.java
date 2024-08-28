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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public UserType getUserType() {
    return userType;
  }

  public void setUserType(UserType userType) {
    this.userType = userType;
  }
}
