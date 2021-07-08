package ru.fogma.demobank.BankDemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fogma.demobank.BankDemo.db.AccountRepository;
import ru.fogma.demobank.BankDemo.db.TransactionRepository;

// https://github.com/pauldragoslav/Spring-boot-Banking/blob/main/src/main/java/com/example/paul/services/TransactionService.java
// https://github.com/hendisantika/springboot-bank-account/blob/master/src/main/java/com/hendisantika/springbootbankaccount/domain/UserTransaction.java

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;



}
