package org.rivera.junit5app.ejemplos.models;

import org.junit.jupiter.api.*;
import org.rivera.junit5app.ejemplos.exceptions.DineroInsuficienteException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {
  Cuenta account;   //Variable reutilizable para muchos de mis métodos(Ciclo de vida - Parecido a Jazmine)

  @BeforeAll
  static void beforeAll() {
    System.out.println("Antes de todos los métodos");
  }

  @BeforeEach //Antes de cada método - Muy parecido a Pruebas Unitarias en Angular
  void initMethodTest() {
    this.account = new Cuenta("Irving", new BigDecimal("50000.50"));
    System.out.println("Inicializando método");
  }

  @AfterEach  //Después de cada método
  void finishMethodTest() {
    System.out.println("Finalizando método");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("Después de todos los métodos");
  }

  @Test
  @DisplayName("Prueba que la persona de la cuenta sea igual a una en especifico")  //Notación para dar un nombre en específico a prueba
  void testAccountName() {
    //Cuenta account = new Cuenta("Irving", new BigDecimal("50000.50")); //Ahora la inicializo con "BeforeEach"

    String expected = "Irving"; //Valor esperado VS Real o actual
    String real = account.getPerson();

    //El parámetro que pongo como expresión lamba es opcional, sirve para personalizar el mensaje de error
    assertNotNull( real, () -> "El parámetro 'persona' no puede ser nulo" );
    Assertions.assertEquals( expected, real, () -> "Los valores no son iguales" ); //o solo "assertEquals( expected, real );" porque estoy importando todos sus métodos estáticos
    assertTrue(real.equals(expected));
  }
  //Balance(saldo en ingles)
  @Test
  @DisplayName("Prueba que el saldo sea igual a uno en especifico y no sea menor a 0")
  void testAccountBalance() {
    //Primero hace BeforeEach
    assertNotNull( account.getBalance() );
    assertEquals( 50000.50, account.getBalance().doubleValue() ); //El BigDecimal lo transformo a double
    assertFalse( account.getBalance().compareTo(BigDecimal.ZERO) < 0 ); //Como es BigDecimal se compara un poco diferente, compareTo regresa 1, 0 o -1 depende el caso
    assertTrue( account.getBalance().compareTo(BigDecimal.ZERO) > 0 ); //Lo mismo con lógica inversa
  }

  @Test
  @DisplayName("Prueba que dos cuentas sean iguales utilizando equals")
  void testAccountReference() {
    Cuenta account1 = new Cuenta("Irving", new BigDecimal("50000.50"));
    Cuenta account2 = new Cuenta("Irving", new BigDecimal("50000.50"));

    //assertNotEquals( account2, account1 ); //No son iguales los objetos aun que tengan los mismos valores(Si no modifico equals del objeto)
    assertEquals( account2, account1 );  //La idea es que compare por valores del objeto, no por instancia, para eso escribo "equals" en el objeto
  }

  @Test
  @Disabled //Notación para deshabilitar prueba por alguna razón(Queda documentado)
  void testAccountDebit() {

    account.debit(new BigDecimal(100)); //Le RESTO esta cantidad a la cuenta
    assertNotNull( account.getBalance() );  //Puede ser buena idea primero revisar que el saldo no sea nulo
    assertEquals( 49900, account.getBalance().intValue() );
    assertEquals( "49900.50", account.getBalance().toPlainString() );
  }

  @Test
  void testAccountCredit() {

    account.credit(new BigDecimal(100)); //Le SUMO esta cantidad a la cuenta

    assertNotNull( account.getBalance() );  //Puede ser buena idea primero revisar que el saldo no sea nulo
    assertEquals( 50100, account.getBalance().intValue() );
    assertEquals( "50100.50", account.getBalance().toPlainString() );
  }

  @Test
  void testInsufficientMoneyAccountException() {

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
    //"assertAll()" lo puedo usar cuando tengo demasiadas pruebas y quiero que se ejecuten todas. Si falla más de una mostrara el reporte de todas
    assertAll( () -> assertEquals("48500", accountSource.getBalance().toPlainString()) ,
             () -> assertEquals("81500.50", accountDestiny.getBalance().toPlainString()) ,
             () -> assertEquals( 2, bank.getAccounts().size() ) ,
             () -> assertEquals( "BBVA", accountSource.getBank().getName() ), //Relación Bidireccional(La agregué en método de añadir Cuenta en Banco)
             () -> assertEquals( "Irving", bank.getAccounts()       //Uso API Stream para encontrar una persona
                     .stream().filter( c -> c.getPerson().equals("Irving") )
                     .findFirst().get().getPerson() ),
             () -> assertTrue(bank.getAccounts().stream()
                     .anyMatch(c -> c.getPerson().equals("Angeles") ))
            );
  }
}