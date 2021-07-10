package ru.fogma.demobank.BankDemo.db;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Entity
@Data
public class Account {

    @Id
    private UUID id = randomUUID();
    private String accountOwner;
    private Double balance;
    private transient List<Transaction> transactions;

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

}
