package ru.fogma.demobank.BankDemo.db;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Entity
@Data
public class Account {

    @Id
    private UUID id = randomUUID();
    private String accountOwner;
    private BigDecimal balance;

    @Version
    private Integer version;

    public Account(String accountOwner, BigDecimal balance) {
        this.accountOwner = accountOwner;
        this.balance = balance;
    }
}
