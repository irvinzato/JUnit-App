package org.rivera.junit5app.ejemplos.models;

import java.math.BigDecimal;

public class Cuenta {
  private String person;
  private BigDecimal balance; //Cuando se trabaja con monedas, finanzas, dinero es mejor "BigDecimal"


  public String getPerson() {
    return person;
  }

  public void setPerson(String person) {
    this.person = person;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }
}
