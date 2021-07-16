package ru.fogma.demobank.BankDemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public void transfer(TransactionDTO transactionDTO) {
        Account sourceAccount = accountRepository.findById(transactionDTO.getSourceId()).orElse(null);
        Account targetAccount = accountRepository.findById(transactionDTO.getTargetId()).orElse(null);

        if (sourceAccount == null || targetAccount == null) throw new RuntimeException();
        boolean isAmountAvailable = isAmountAvailable(transactionDTO.getAmount(), sourceAccount.getBalance());
        if (!isAmountAvailable) throw new RuntimeException();

        var transaction = new Transaction();
        transaction.setSourceAccountId(transactionDTO.getSourceId());
        transaction.setTargetAccountId(transactionDTO.getTargetId());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setSourceAccountOwner(sourceAccount.getAccountOwner());
        transaction.setOperation(TRANSFER);
        transactionRepository.save(transaction);
    }

    public void debit(UUID id, BigDecimal amount) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) throw new RuntimeException();
        if (account.getBalance().subtract(amount).compareTo(ZERO) < 0) throw new RuntimeException();

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

    public void credit(UUID id, BigDecimal amount) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) throw new RuntimeException();
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    private boolean isAmountAvailable(BigDecimal amount, BigDecimal balance) {
        return balance.subtract(amount).compareTo(ZERO) >= 0;
    }
}