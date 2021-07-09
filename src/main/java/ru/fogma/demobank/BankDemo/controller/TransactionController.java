package ru.fogma.demobank.BankDemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fogma.demobank.BankDemo.model.TransactionDTO;
import ru.fogma.demobank.BankDemo.service.TransactionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transaction")
    public ResponseEntity<String> transfer(@RequestBody TransactionDTO transactionDTO) {
        transactionService.transferInit(transactionDTO);
        return ResponseEntity.ok("transaction complete");
    }
}
