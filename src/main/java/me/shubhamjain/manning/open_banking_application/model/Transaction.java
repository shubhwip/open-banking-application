package me.shubhamjain.manning.open_banking_application.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(
    String type,
    LocalDateTime date,
    String accountNumber,
    String currency,
    BigDecimal amount,
    String merchantName,
    String merchantLogo)
{ }
