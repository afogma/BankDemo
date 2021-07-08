package ru.fogma.demobank.BankDemo.db;

import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {

    private Long sourceAccountId;
    private Long targetAccountId;
    private Double amount;
    private String sourceAccountOwner;
    private LocalDateTime transactionTime;
    private Operation operation;

}
