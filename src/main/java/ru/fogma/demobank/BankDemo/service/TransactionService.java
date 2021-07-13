package ru.fogma.demobank.BankDemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fogma.demobank.BankDemo.db.*;
import ru.fogma.demobank.BankDemo.model.TransactionDTO;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.math.BigDecimal.ZERO;
import static ru.fogma.demobank.BankDemo.db.Operation.*;

// https://github.com/pauldragoslav/Spring-boot-Banking/blob/main/src/main/java/com/example/paul/services/TransactionService.java
// https://github.com/hendisantika/springboot-bank-account/blob/master/src/main/java/com/hendisantika/springbootbankaccount/domain/UserTransaction.java

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void transfer(TransactionDTO transactionDTO) {
        Optional<Account> sourceAccount = accountRepository.findById(transactionDTO.getSourceId());
        Optional<Account> targetAccount = accountRepository.findById(transactionDTO.getTargetId());

        if (sourceAccount.isEmpty() || targetAccount.isEmpty()) throw new RuntimeException();
        boolean isAmountAvailable = isAmountAvailable(transactionDTO.getAmount(), sourceAccount.get().getBalance());
        if (!isAmountAvailable) throw new RuntimeException();

        var transaction = new Transaction();
        transaction.setSourceAccountId(transactionDTO.getSourceId());
        transaction.setTargetAccountId(transactionDTO.getTargetId());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setSourceAccountOwner(sourceAccount.get().getAccountOwner());
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