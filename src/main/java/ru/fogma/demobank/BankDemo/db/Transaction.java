package ru.fogma.demobank.BankDemo.db;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long sourceAccountId;
    private Long targetAccountId;
    private Double amount;
    private String sourceAccountOwner;
    private LocalDateTime transactionTime;
    private Operation operation;

}
