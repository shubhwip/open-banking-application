package me.shubhamjain.manning.open_banking_application.service;

import me.shubhamjain.manning.open_banking_application.model.TransactionResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionServiceImplTest {

  TransactionService transactionService;

  @BeforeEach
  void setUp() {
    transactionService = new TransactionServiceImpl();
  }

  @Test
  void givenAValidAccountNumber_whenFindAllByAccountNumberIsCalled_thenReturnsTransactions() {
    // Arrange
    String accountNumber = "mockAccountNumber";
    // Act
    TransactionResponse transactions = transactionService.findAllByAccountNumber(accountNumber);
    // Assert
    Assertions.assertEquals(3, transactions.transactions().size());
  }
}
