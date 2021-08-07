package ru.fogma.demobank.BankDemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fogma.demobank.BankDemo.model.TransactionDTO;
import ru.fogma.demobank.BankDemo.service.TransactionService;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transaction/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransactionDTO transactionDTO) {
        transactionService.transfer(transactionDTO);
        return ResponseEntity.ok("transaction complete");
    }

    @PostMapping("/transaction/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam UUID id, BigDecimal amount) {
        transactionService.withdraw(id, amount);
        return ResponseEntity.ok("transaction complete");
    }

    @PostMapping("/transaction/deposit")
    public ResponseEntity<String> deposit(@RequestParam UUID id, BigDecimal amount) {
        transactionService.deposit(id, amount);
        return ResponseEntity.ok("transaction complete");
    }

}
