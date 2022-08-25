package org.rivera.junit5app.ejemplos.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rivera.junit5app.ejemplos.exceptions.DineroInsuficienteException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

  @Test
  void testAccountName() {
    Cuenta account = new Cuenta("Irving", new BigDecimal("50000.50"));
    //account.setPerson("Irving");
    String expected = "Irving"; //Valor esperado VS Real o actual
    String real = account.getPerson();

    assertNotNull( real );
    Assertions.assertEquals( expected, real ); //o solo "assertEquals( expected, real );" porque estoy importando todos sus métodos estáticos
    assertTrue(real.equals(expected));
  }
  //Balance(saldo en ingles)
  @Test
  void testAccountBalance() {
    Cuenta account = new Cuenta("Irving", new BigDecimal("50000.50"));
    assertNotNull( account.getBalance() );
    assertEquals( 50000.50, account.getBalance().doubleValue() ); //El BigDecimal lo transformo a double
    assertFalse( account.getBalance().compareTo(BigDecimal.ZERO) < 0 ); //Como es BigDecimal se compara un poco diferente, compareTo regresa 1, 0 o -1 depende el caso
    assertTrue( account.getBalance().compareTo(BigDecimal.ZERO) > 0 ); //Lo mismo con lógica inversa
  }

  @Test
  void testAccountReference() {
    Cuenta account1 = new Cuenta("Irving", new BigDecimal("50000.50"));
    Cuenta account2 = new Cuenta("Irving", new BigDecimal("50000.50"));

    //assertNotEquals( account2, account1 ); //No son iguales los objetos aun que tengan los mismos valores(Si no modifico equals del objeto)
    assertEquals( account2, account1 );  //La idea es que compare por valores del objeto, no por instancia, para eso escribo "equals" en el objeto
  }

  @Test
  void testAccountDebit() {
    Cuenta account = new Cuenta("Irving", new BigDecimal("50000.50"));
    account.debit(new BigDecimal(100)); //Le RESTO esta cantidad a la cuenta

    assertNotNull( account.getBalance() );  //Puede ser buena idea primero revisar que el saldo no sea nulo
    assertEquals( 49900, account.getBalance().intValue() );
    assertEquals( "49900.50", account.getBalance().toPlainString() );
  }

  @Test
  void testAccountCredit() {
    Cuenta account = new Cuenta("Irving", new BigDecimal("50000.50"));
    account.credit(new BigDecimal(100)); //Le SUMO esta cantidad a la cuenta

    assertNotNull( account.getBalance() );  //Puede ser buena idea primero revisar que el saldo no sea nulo
    assertEquals( 50100, account.getBalance().intValue() );
    assertEquals( "50100.50", account.getBalance().toPlainString() );
  }

  @Test
  void testInsufficientMoneyAccountException() {
    Cuenta account = new Cuenta("Irving", new BigDecimal("50000.50"));

    Exception exception = assertThrows( DineroInsuficienteException.class, () -> { //método de JUnit para manejar excepciones
      account.debit(new BigDecimal(55500));
    });
    String expected = "Dinero Insuficiente";
    String actual = exception.getMessage();
    assertEquals( expected, actual );
  }

  @Test
  void testTransferMoneyAccounts() {
    Cuenta accountSource = new Cuenta("Irving", new BigDecimal("50000"));
    Cuenta accountDestiny = new Cuenta("Angeles", new BigDecimal("80000.50"));
    Banco bank = new Banco();
    bank.setName("BBVA");
    bank.transfer(accountSource, accountDestiny, new BigDecimal(1500));
    assertEquals("48500", accountSource.getBalance().toPlainString());
    assertEquals("81500.50", accountDestiny.getBalance().toPlainString());
  }

  @Test
  void testBankAccountsRelationship() {
    Cuenta accountSource = new Cuenta("Irving", new BigDecimal("50000"));
    Cuenta accountDestiny = new Cuenta("Angeles", new BigDecimal("80000.50"));
    Banco bank = new Banco();
    bank.addAccount(accountSource);
    bank.addAccount(accountDestiny);
    bank.setName("BBVA");
    bank.transfer(accountSource, accountDestiny, new BigDecimal(1500));
    assertEquals("48500", accountSource.getBalance().toPlainString());
    assertEquals("81500.50", accountDestiny.getBalance().toPlainString());

    assertEquals( 2, bank.getAccounts().size() );
  }
}