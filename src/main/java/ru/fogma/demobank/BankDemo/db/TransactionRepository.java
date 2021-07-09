package ru.fogma.demobank.BankDemo.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    void save(Transaction transaction, LockModeType pessimisticWrite);
}
