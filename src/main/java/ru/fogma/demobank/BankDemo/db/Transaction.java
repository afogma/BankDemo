package ru.fogma.demobank.BankDemo.db;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Data
@Entity
public class Transaction {

    @Id
    private UUID id = randomUUID();
    private UUID sourceAccountId;
    private UUID targetAccountId;
    private Double amount;
    private String sourceAccountOwner;
    private LocalDateTime transactionTime = LocalDateTime.now();
    private Operation operation;

    @Version
    private Integer version;

}
