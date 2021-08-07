package ru.fogma.demobank.BankDemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fogma.demobank.BankDemo.db.*;
import ru.fogma.demobank.BankDemo.model.TransactionDTO;

import java.math.BigDecimal;
import java.util.UUID;

import static java.math.BigDecimal.ZERO;
import static ru.fogma.demobank.BankDemo.db.Operation.*;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public void transfer(TransactionDTO transactionDTO) {
        Account sourceAccount = accountRepository.findById(transactionDTO.getSourceId()).orElse(null);
        Account targetAccount = accountRepository.findById(transactionDTO.getTargetId()).orElse(null);

        if (sourceAccount == null || targetAccount == null) throw new RuntimeException();
        boolean isAmountAvailable = isAmountAvailable(sourceAccount.getBalance(), transactionDTO.getAmount());
        if (!isAmountAvailable) throw new RuntimeException();

        BigDecimal amount = transactionDTO.getAmount();
        var transaction = new Transaction();
        transaction.setSourceAccountId(transactionDTO.getSourceId());
        transaction.setTargetAccountId(transactionDTO.getTargetId());
        transaction.setAmount(amount);
        transaction.setOperation(TRANSFER);
        debit(sourceAccount.getId(), amount);
        credit(targetAccount.getId(), amount);
        transactionRepository.save(transaction);
    }

    public void deposit(UUID id, BigDecimal amount) {
        if (!accountRepository.existsById(id)) throw new RuntimeException();

        Transaction transaction = new Transaction();
        transaction.setTargetAccountId(id);
        transaction.setAmount(amount);
        transaction.setOperation(DEPOSIT);
        transactionRepository.save(transaction);
        credit(id, amount);
    }

    public void withdraw(UUID id, BigDecimal amount) {
        if (!accountRepository.existsById(id)) throw new RuntimeException();

        Transaction transaction = new Transaction();
        transaction.setSourceAccountId(id);
        transaction.setAmount(amount);
        transaction.setOperation(WITHDRAWAL);
        transactionRepository.save(transaction);
        debit(id, amount);
    }

    @Transactional
    public void debit(UUID id, BigDecimal amount) {
        Account account = accountRepository.findById(id).orElseThrow(RuntimeException::new);
        if (!isAmountAvailable(account.getBalance(), amount)) throw new RuntimeException();
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

    @Transactional
    public void credit(UUID id, BigDecimal amount) {
        Account account = accountRepository.findById(id).orElseThrow(RuntimeException::new);
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    private boolean isAmountAvailable(BigDecimal balance, BigDecimal amount) {
        return balance.subtract(amount).compareTo(ZERO) >= 0;
    }
}