package me.shubhamjain.manning.open_banking_application.service;

import java.util.List;
import me.shubhamjain.manning.open_banking_application.model.Transaction;
import me.shubhamjain.manning.open_banking_application.model.TransactionDto;
import me.shubhamjain.manning.open_banking_application.model.TransactionResponse;
import me.shubhamjain.manning.open_banking_application.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  TransactionRepository transactionRepository;

  @Override
  public TransactionResponse findAllByAccountNumber(String id) {
    List<Transaction> transactions = transactionRepository.findTransactionsByAccountId(id);
    return new TransactionResponse(id,
        transactions.stream().map(transaction -> new TransactionDto(
            transaction.getType(),
            transaction.getDate(),
            transaction.getCurrency(),
            transaction.getAmount(),
            transaction.getMerchantName(),
            transaction.getMerchantLogo()
        )).toList());
  }

}
