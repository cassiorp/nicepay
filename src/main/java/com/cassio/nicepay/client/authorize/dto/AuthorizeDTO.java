package com.cassio.nicepay.client.authorize.dto;

public class AuthorizeDTO {
  private Status status;
  private Data data;

  public AuthorizeDTO(Status status, Data data) {
    this.status = status;
    this.data = data;
  }

  public AuthorizeDTO() {
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Data getData() {
    return data;
  }

  public void setData(Data data) {
    this.data = data;
  }
}
