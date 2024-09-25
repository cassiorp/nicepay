package com.cassio.nicepay.client.notify.dto;

public class Data {

  private String authorization;

  public Data(String authorization) {
    this.authorization = authorization;
  }

  public Data() {
  }

  public String getAuthorization() {
    return authorization;
  }

  public void setAuthorization(String authorization) {
    this.authorization = authorization;
  }
}
