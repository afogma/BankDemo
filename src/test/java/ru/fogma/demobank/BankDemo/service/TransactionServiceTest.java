package ru.fogma.demobank.BankDemo.service;

import org.junit.jupiter.api.Test;
import ru.fogma.demobank.BankDemo.db.Account;
import ru.fogma.demobank.BankDemo.db.AccountRepository;
import ru.fogma.demobank.BankDemo.db.TransactionRepository;
import ru.fogma.demobank.BankDemo.model.TransactionDTO;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    TransactionRepository transactionRepository = mock(TransactionRepository.class);
    AccountRepository accountRepository = mock(AccountRepository.class);
    TransactionService transactionService = new TransactionService(accountRepository, transactionRepository);

    private final UUID sourceUUID = UUID.fromString("43e8a3e9-56ad-4217-87a1-17e6999ddfed");
    private final UUID targetUUID = UUID.fromString("eafcdcd1-8d74-4096-8d8a-fca03d6aebe6");



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

    @Test
    public void should_throw_optimistic_lock_exception() {
        Account account = getAccountOne();
//        when(cabinetRepo.findByNumber(333)).thenThrow(new CabinetAlreadyExistException());
//        assertThrows(CabinetAlreadyExistException.class, () -> cabinetRepo.findByNumber(cabinet.getNumber()));



    }


    private Account getAccountOne() {
        return new Account(sourceUUID, "Petruha Vasechkin", new BigDecimal("88888"), 1);
    }

    private Account getAccountTwo() {
        return new Account(targetUUID, "Vaska Petrushkin", new BigDecimal("22222"), 1);
    }

    private TransactionDTO getTransactionDTO() {
        return new TransactionDTO(sourceUUID, targetUUID, new BigDecimal("44444"));
    }
}
