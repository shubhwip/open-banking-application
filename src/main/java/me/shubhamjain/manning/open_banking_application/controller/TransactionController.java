package me.shubhamjain.manning.open_banking_application.controller;

import java.util.List;
import me.shubhamjain.manning.open_banking_application.model.Transaction;
import me.shubhamjain.manning.open_banking_application.model.TransactionResponse;
import me.shubhamjain.manning.open_banking_application.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/api/v1/transactions"))
public class TransactionController {

  TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @GetMapping("/{accountNumber}")
  ResponseEntity<TransactionResponse> getTransactionsByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
    return ResponseEntity.ok().body(transactionService.findAllByAccountNumber(accountNumber));
  }

}
