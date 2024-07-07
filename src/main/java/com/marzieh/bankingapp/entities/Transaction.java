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

    /*

    public void deposit(double amount) throws BankingException {
        if (amount <= 0) {
            throw new BankingException("Deposit amount must be positive.");
        }
        lock.lock();
        try {
            balance += amount;
            transactions.add(new Transaction(TransactionType.DEPOSIT, amount, accountNumber, "Deposit"));
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(double amount) throws BankingException {
        if (amount <= 0) {
            throw new BankingException("Withdrawal amount must be positive.");
        }
        lock.lock();
        try {
            if (balance < amount) {
                throw new InsufficientFundsException("Insufficient funds.");
            }
            balance -= amount;
            transactions.add(new Transaction(TransactionType.WITHDRAWAL, amount, accountNumber, "Withdrawal"));
        } finally {
            lock.unlock();
        }
    }

    public void transferTo(Account destinationAccount, double amount) throws BankingException {
        if (amount <= 0) {
            throw new InvalidAmountException("Transfer amount must be positive.");
        }
        lock.lock();
        try {
            this.withdraw(amount);
            destinationAccount.deposit(amount);
            Transaction transferTransaction = new Transaction(TransactionType.TRANSFER, amount, this.accountNumber, "Transfer to account " + destinationAccount.getAccountNumber());
            this.transactions.add(transferTransaction);
            destinationAccount.getTransactions().add(new Transaction(TransactionType.TRANSFER, amount, destinationAccount.getAccountNumber(), "Transfer from account " + this.accountNumber));
        } finally {
            lock.unlock();
        }
    }


     */
}

