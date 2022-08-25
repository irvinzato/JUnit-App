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
}