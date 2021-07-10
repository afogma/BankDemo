package ru.fogma.demobank.BankDemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fogma.demobank.BankDemo.db.Account;
import ru.fogma.demobank.BankDemo.service.AccountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/add")
    public void addAccount(@RequestBody Account account) {
        accountService.addAccount(account);
    }


}
