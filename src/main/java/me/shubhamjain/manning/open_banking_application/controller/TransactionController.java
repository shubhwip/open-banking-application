package me.shubhamjain.manning.open_banking_application.controller;

import java.util.List;
import me.shubhamjain.manning.open_banking_application.model.Transaction;
import me.shubhamjain.manning.open_banking_application.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1/transactions")
public class TransactionController {

  TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @GetMapping("/{accountNumber}")
  List<Transaction> getTransactionsByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
    return transactionService.findAllByAccountNumber(accountNumber);
  }

}
