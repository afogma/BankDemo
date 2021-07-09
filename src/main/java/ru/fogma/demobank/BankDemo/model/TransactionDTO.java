package ru.fogma.demobank.BankDemo.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TransactionDTO {

    private Long sourceId;
    private Long targetId;
    private Double amount;
}
