package com.marzieh.bankingapp.entities;

import com.marzieh.bankingapp.util.TransactionType;

import java.time.LocalDateTime;

public class Transaction {
    private static int counter = 1000;
    private final int transactionId;
    private final TransactionType type;
    private final double amount;
    private final LocalDateTime date;
    private final int accountNumber;
    private final String description;

    public Transaction(TransactionType type, double amount, int accountNumber, String description) {
        this.transactionId = counter++;
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.accountNumber = accountNumber;
        this.description = description;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", type=" + type +
                ", amount=" + amount +
                ", date=" + date +
                ", accountNumber=" + accountNumber +
                ", description='" + description + '\'' +
                '}';
    }
}

