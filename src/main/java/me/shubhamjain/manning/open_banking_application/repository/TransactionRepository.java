package me.shubhamjain.manning.open_banking_application.repository;

import java.util.List;
import me.shubhamjain.manning.open_banking_application.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
  @Query("SELECT t FROM Transaction t WHERE t.accountNumber = :accountNumber")
  List<Transaction> findTransactionsByAccountId(@Param("accountNumber") String accountNumber);
}
