package ru.fogma.demobank.BankDemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fogma.demobank.BankDemo.db.*;
import ru.fogma.demobank.BankDemo.model.TransactionDTO;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ru.fogma.demobank.BankDemo.db.Operation.DEPOSIT;
import static ru.fogma.demobank.BankDemo.db.Operation.WITHDRAWAL;

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
        transaction.setOperation(DEPOSIT);

        withdraw(sourceAccount.get(), transactionDTO.getAmount());
        deposit(targetAccount.get(), transactionDTO.getAmount());

        transactionRepository.save(transaction);
        targetAccount.get().addTransaction(transaction);
        transaction.setOperation(WITHDRAWAL);
        sourceAccount.get().addTransaction(transaction);


    }

    public void transferInit(TransactionDTO transactionDTO) {
        executorService.submit(() -> transfer(transactionDTO));
    }

    private void withdraw(Account account, double amount) {
        account.setBalance((account.getBalance() - amount));
        accountRepository.save(account);
    }

    private void deposit(Account account, double amount) {
        account.setBalance((account.getBalance() + amount));
        accountRepository.save(account);
    }

    private boolean isAmountAvailable(double amount, double balance) {
        return (balance - amount) > 0;
    }

}