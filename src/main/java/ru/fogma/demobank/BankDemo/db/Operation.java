package ru.fogma.demobank.BankDemo.db;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Operation {

    DEPOSIT(1), WITHDRAWAL(2), TRANSFER(3);

    private final int id;
}
