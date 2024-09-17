package com.cassio.nicepay.entity;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transfers")
public class Transfer {

  @Id
  private String id;
  private BigDecimal value;
  private String payer;
  private String payee;
  private Situation situation;

  public Transfer() {
  }

  public Transfer(BigDecimal value, String payer, String payee) {
    this.value = value;
    this.payer = payer;
    this.payee = payee;
  }

  public Transfer(String id, BigDecimal value, String payer, String payee, Situation situation) {
    this.id = id;
    this.value = value;
    this.payer = payer;
    this.payee = payee;
    this.situation = situation;
  }

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public String getPayer() {
    return payer;
  }

  public void setPayer(String payer) {
    this.payer = payer;
  }

  public String getPayee() {
    return payee;
  }

  public void setPayee(String payee) {
    this.payee = payee;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Situation getSituation() {
    return situation;
  }

  public void setSituation(Situation situation) {
    this.situation = situation;
  }
}
