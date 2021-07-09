package ru.fogma.demobank.BankDemo.db;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Transaction {

    @Id
    private UUID id;
    private Long sourceAccountId;
    private Long targetAccountId;
    private Double amount;
    private String sourceAccountOwner;
    private LocalDateTime transactionTime;
    private Operation operation;

}
