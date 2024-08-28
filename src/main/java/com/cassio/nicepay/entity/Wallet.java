package com.cassio.nicepay.entity;

import java.math.BigDecimal;

public class Wallet {

  private BigDecimal balance;

  public Wallet(BigDecimal balance) {
    this.balance = balance;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }
}
