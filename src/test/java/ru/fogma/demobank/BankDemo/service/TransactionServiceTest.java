package ru.fogma.demobank.BankDemo.service;

import org.junit.jupiter.api.Test;
import ru.fogma.demobank.BankDemo.db.Account;
import ru.fogma.demobank.BankDemo.db.AccountRepository;
import ru.fogma.demobank.BankDemo.db.TransactionRepository;
import ru.fogma.demobank.BankDemo.model.TransactionDTO;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    TransactionRepository transactionRepository = mock(TransactionRepository.class);
    AccountRepository accountRepository = mock(AccountRepository.class);
    TransactionService transactionService = new TransactionService(accountRepository, transactionRepository);

    private final String uuidSource = "43e8a3e9-56ad-4217-87a1-17e6999ddfed";
    private final String uuidTarget = "eafcdcd1-8d74-4096-8d8a-fca03d6aebe6";
    private final UUID sourceUUID = UUID.fromString(uuidSource);
    private final UUID targetUUID = UUID.fromString(uuidTarget);

    private Account getAccountOne() {
        return new Account(sourceUUID, "Petruha Vasechkin", new BigDecimal("88888"), 1);
    }

    private Account getAccountTwo() {
        return new Account(targetUUID,"Vaska Petrushkin", new BigDecimal("22222"), 1);
    }

    private TransactionDTO getDTO() {
        return new TransactionDTO(sourceUUID, targetUUID, new BigDecimal("44444"));
    }

    @Test
    void transfer() {
        TransactionDTO transactionDTO = getDTO();
        System.out.println(getDTO());
//        when(transactionDTO.getSourceId()).thenReturn(UUID.fromString("43e8a3e9-56ad-4217-87a1-17e6999ddfed"));
        Account sourceAcc = accountRepository.getById(transactionDTO.getSourceId());

//        when(transactionDTO.getTargetId()).thenReturn(UUID.fromString("1e7464ea-c357-49ed-9b1f-c9e4c2b633fb"));
        Account targetAcc = accountRepository.getById(transactionDTO.getTargetId());

        transactionService.transfer(transactionDTO);
        System.out.println(sourceAcc);
        System.out.println(targetAcc);

    }

    @Test
    void deposit() {
    }

    @Test
    void withdraw() {
    }

    @Test
    void debit() {
    }

    @Test
    void credit() {
    }
}