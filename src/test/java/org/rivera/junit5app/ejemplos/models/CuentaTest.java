package org.rivera.junit5app.ejemplos.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

  @Test
  void testAccountName() {
    Cuenta account = new Cuenta("Irving", new BigDecimal("50000.50"));
    //account.setPerson("Irving");
    String expected = "Irving"; //Valor esperado VS Real o actual
    String real = account.getPerson();
    Assertions.assertEquals( expected, real ); //o solo "assertEquals( expected, real );" porque estoy importando todos sus métodos estáticos
    assertTrue(real.equals(expected));
  }

  @Test
  void testAccountBalance() {
    Cuenta account = new Cuenta("Irving", new BigDecimal("50000.50"));
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
}