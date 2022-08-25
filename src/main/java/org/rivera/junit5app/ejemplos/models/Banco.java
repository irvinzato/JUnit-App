package org.rivera.junit5app.ejemplos.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Banco {
  private List<Cuenta> accounts;
  private String name;

  public Banco() {
    this.accounts = new ArrayList<>();
  }

  public List<Cuenta> getAccounts() {
    return accounts;
  }

  public void setAccounts(List<Cuenta> accounts) {
    this.accounts = accounts;
  }

  public void addAccount(Cuenta account) {
    accounts.add(account);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void transfer(Cuenta source, Cuenta destiny, BigDecimal amount) { //Transferir(origen-destino-monto)
    source.debit(amount);
    destiny.credit(amount);
  }
}
