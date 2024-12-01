package me.shubhamjain.manning.open_banking_application.service;

import me.shubhamjain.manning.open_banking_application.model.TransactionResponse;

public interface TransactionService {
  TransactionResponse findAllByAccountNumber(String accountNumber);
}
