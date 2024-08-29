package com.cassio.nicepay.controller.dto;

import com.cassio.nicepay.entity.Situation;
import java.math.BigDecimal;

public class TransferResponseDTO {

  private String id;
  private BigDecimal value;
  private String payer;
  private String payee;
  private Situation situation;

  public TransferResponseDTO(String id, BigDecimal value, String payer, String payee,
      Situation situation) {
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
