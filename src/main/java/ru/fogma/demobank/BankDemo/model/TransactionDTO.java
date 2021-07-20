package ru.fogma.demobank.BankDemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@Data
@AllArgsConstructor
public class TransactionDTO {

    private UUID sourceId;
    private UUID targetId;
    private BigDecimal amount;

}
