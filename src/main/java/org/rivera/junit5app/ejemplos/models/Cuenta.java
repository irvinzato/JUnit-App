package org.rivera.junit5app.ejemplos.models;

import java.math.BigDecimal;
//Atajos para crear clase test - "Alt + Enter" o "Alt + Insert" y create test o manual
public class Cuenta {
  private String person;
  private BigDecimal balance; //Cuando se trabaja con monedas, finanzas, dinero es mejor "BigDecimal"

  public Cuenta(String name, BigDecimal balance) {
    this.person = name;
    this.balance = balance;
  }

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

  //Para que equals compare por valores del objeto, no por instancia
  @Override
  public boolean equals(Object obj) {
    if( !(obj instanceof Cuenta) ) {
      return false;
    }
    Cuenta account = (Cuenta) obj;
    if( this.person == null | this.balance == null ) {
      return false;
    }
    return this.person.equals(account.getPerson()) && this.balance.equals(account.getBalance());
  }
}
