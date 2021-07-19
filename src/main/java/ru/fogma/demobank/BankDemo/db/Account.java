package ru.fogma.demobank.BankDemo.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    private UUID id = randomUUID();
    private String accountOwner;
    private BigDecimal balance;

    @Version
    private Integer version;

}
