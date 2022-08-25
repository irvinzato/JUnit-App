package org.rivera.junit5app.ejemplos.models;

import org.rivera.junit5app.ejemplos.exceptions.DineroInsuficienteException;

import java.math.BigDecimal;
//Atajos para crear clase test - "Alt + Enter" o "Alt + Insert" y create test o manual
public class Cuenta {
  private String person;
  private BigDecimal balance; //Cuando se trabaja con monedas, finanzas, dinero es mejor "BigDecimal"
  private Banco bank;

  public Cuenta(String name, BigDecimal balance) {
    this.person = name;
    this.balance = balance; //saldo
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

  public Banco getBank() {
    return bank;
  }

  public void setBank(Banco bank) {
    this.bank = bank;
  }

  //La idea de estos dos métodos nuevos es primero hacerles la prueba y después desarrollarlos aquí
  public void debit(BigDecimal amount) {
    BigDecimal newBalance = this.balance.subtract(amount); //Es inmutable BigDecimal por eso debo asignar, no solo usar
    if( newBalance.compareTo(BigDecimal.ZERO) < 0 ) {
      throw new DineroInsuficienteException("Dinero Insuficiente");
    }
    this.balance = newBalance;
  }

  public void credit(BigDecimal amount) {
    this.balance = this.balance.add(amount);
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
