package ru.fogma.demobank.BankDemo.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Modifying
    @Query("update Account a set a.balance = :balance where a.accountOwner = :accountOwner")
    public Account updateAccount(Account account);
}
