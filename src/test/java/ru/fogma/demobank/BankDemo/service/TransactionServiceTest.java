package ru.fogma.demobank.BankDemo.service;

import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.fogma.demobank.BankDemo.db.Account;
import ru.fogma.demobank.BankDemo.db.AccountRepository;
import ru.fogma.demobank.BankDemo.db.AccountRepositoryTest;
import ru.fogma.demobank.BankDemo.db.TransactionRepository;
import ru.fogma.demobank.BankDemo.model.TransactionDTO;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    ExecutorService executor = Executors.newFixedThreadPool(10);


    TransactionRepository transactionRepository = mock(TransactionRepository.class);
    AccountRepository accountRepository = mock(AccountRepository.class);
    TransactionService transactionService = new TransactionService(accountRepository, transactionRepository);

    private final UUID sourceUUID = UUID.fromString("43e8a3e9-56ad-4217-87a1-17e6999ddfed");
//    private final UUID sourceUUID = UUID.fromString("43923e92-5d18-49de-91a8-0fb28bfa0d08");
    private final UUID targetUUID = UUID.fromString("eafcdcd1-8d74-4096-8d8a-fca03d6aebe6");
//    private final UUID targetUUID = UUID.fromString("b9661cce-b839-4d78-be7a-fc865480fedc");


    @Test
    void transfer() {
        TransactionDTO transactionDTO = getTransactionDTO();
        Account source = getAccountOne();
        Account target = getAccountTwo();
        when(accountRepository.findById(getAccountOne().getId())).thenReturn(Optional.of(source));
        when(accountRepository.findById(getAccountTwo().getId())).thenReturn(Optional.of(target));

        transactionService.transfer(new TransactionDTO(sourceUUID, targetUUID, new BigDecimal("44444")));
    }

    @Test
    void deposit() {
    }

    @Test
    void withdraw() {
    }

    @Test
    void debit() {
        Account acc = getAccountOne();
        when(accountRepository.findById(acc.getId())).thenReturn(Optional.of(acc));
        transactionService.debit(acc.getId(), new BigDecimal("33333"));
        BigDecimal amount = new BigDecimal("55555");
        assertEquals(acc.getBalance(), amount);
    }

    @Test
    void credit() {
        Account acc = getAccountTwo();
        when(accountRepository.findById(acc.getId())).thenReturn(Optional.of(acc));
        transactionService.credit(acc.getId(), new BigDecimal("11111"));
        BigDecimal amount = new BigDecimal("33333");
        assertEquals(acc.getBalance(), amount);
    }

    private Account getAccountOne() {
        return  new Account("Petruha Vasechkin", new BigDecimal("88888"));
    }

    private Account getAccountTwo() {
        return new Account("Vaska Petrushkin", new BigDecimal("22222"));
    }

    private TransactionDTO getTransactionDTO() {
        return new TransactionDTO(sourceUUID, targetUUID, new BigDecimal("1"));
    }

    private void insertAccounts() {
        accountRepository.save(getAccountOne());
        accountRepository.save(getAccountTwo());
        accountRepository.flush();
    }
}
