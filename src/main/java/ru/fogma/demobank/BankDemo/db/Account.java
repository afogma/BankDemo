package ru.fogma.demobank.BankDemo.db;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Account {

    @Id
    private UUID id;
    private String accountOwner;
    private Double balance;
    private List<Transaction> transactions;

}
