package com.marzieh.bankingapp.entities;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private static int accountCounter = 1000;
    private final int accountNumber;
    private double balance;
    private final Customer customer;
    private final List<Transaction> transactions;
    private final Lock lock = new ReentrantLock();

    public Account(Customer customer) {
        this.accountNumber = accountCounter++;
        this.customer = customer;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber + ", Balance: " + balance + ", Customer: [" + customer + "]";
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
