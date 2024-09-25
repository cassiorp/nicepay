package com.cassio.nicepay.client.authorize.dto;

public class Data {
  private Boolean authorization;

  public Data(Boolean authorization) {
    this.authorization = authorization;
  }

  public Data() {
  }

  public Boolean getAuthorization() {
    return authorization;
  }

  public void setAuthorization(Boolean authorization) {
    this.authorization = authorization;
  }
}
