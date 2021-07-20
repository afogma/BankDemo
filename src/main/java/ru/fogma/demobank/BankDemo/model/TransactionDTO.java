package ru.fogma.demobank.BankDemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TransactionDTO {

    private UUID sourceId;
    private UUID targetId;
    private BigDecimal amount;
}
