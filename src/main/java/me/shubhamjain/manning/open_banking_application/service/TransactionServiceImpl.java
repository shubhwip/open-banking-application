package me.shubhamjain.manning.open_banking_application.service;

import java.util.List;
import me.shubhamjain.manning.open_banking_application.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

  @Override
  public List<Transaction> findAllByAccountNumber(String accountNumer) {
    return List.of(
        new Transaction(null, null, null, null, null, null, null),
        new Transaction(null, null, null, null, null, null, null),
        new Transaction(null, null, null, null, null, null, null)
    );
  }
}
