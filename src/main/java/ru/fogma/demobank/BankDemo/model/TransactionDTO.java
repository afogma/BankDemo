package ru.fogma.demobank.BankDemo.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@Data
@RequiredArgsConstructor
public class TransactionDTO {

    private UUID sourceId;
    private UUID targetId;
    private BigDecimal amount;

    public TransactionDTO(UUID id, UUID id1, BigDecimal bigDecimal) {
    }
}
