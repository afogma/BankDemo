package ru.fogma.demobank.BankDemo.db;

import lombok.Getter;

@Getter
public enum Operation {

    DEPOSIT(1), WITHDRAWAL(2), TRANSFER(3);
    int id;

    Operation(int id) {
        this.id = id;
    }
}
