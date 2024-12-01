package me.shubhamjain.manning.open_banking_application.model;

import jakarta.persistence.Entity;
import java.util.List;

public record TransactionResponse(
    String accountNumber,
    List<TransactionDto> transactions
) { }
