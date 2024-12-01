package me.shubhamjain.manning.open_banking_application.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDto(
    String type,
    LocalDateTime date,
    String currency,
    BigDecimal amount,
    String merchantName,
    String merchantLogo
) { }
