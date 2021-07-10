package ru.fogma.demobank.BankDemo.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Data
public class TransactionDTO {

    private UUID sourceId;
    private UUID targetId;
    private Double amount;
}
