package ru.fogma.demobank.BankDemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fogma.demobank.BankDemo.db.Account;
import ru.fogma.demobank.BankDemo.db.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public void addAccount(Account account) {
        accountRepository.save(account);
    }
}
