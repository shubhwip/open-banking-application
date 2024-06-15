package me.shubhamjain.manning.open_banking_application.service;

import java.util.List;
import me.shubhamjain.manning.open_banking_application.model.Transaction;

public interface TransactionService {
  List<Transaction> findAllByAccountNumber(String accountNumer);
}
