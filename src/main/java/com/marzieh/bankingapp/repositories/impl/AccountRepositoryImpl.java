package com.marzieh.bankingapp.repositories.impl;

import com.marzieh.bankingapp.entities.Account;
import com.marzieh.bankingapp.entities.Customer;
import com.marzieh.bankingapp.repositories.AccountRepository;

import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {

    private final List<Account> accounts;

    public AccountRepositoryImpl() {
        accounts = new ArrayList<>();
    }

    @Override
    public Account saveAccount(Customer customer) {
        Account account = new Account(customer);
        accounts.add(account);
        return account;
    }

    @Override
    public Account findAccountByAccountNumber(int accountNumber) {
        return accounts.stream()
                .filter(account -> account.getAccountNumber() == accountNumber)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deposit(Account account2, double amount) {
        accounts.stream()
                .filter(account -> account.getAccountNumber() == account2.getAccountNumber())
                .findFirst()
                .ifPresent(account -> account.setBalance(account.getBalance() + amount));
    }

    @Override
    public void withdraw(Account account2, double amount) {
        accounts.stream()
                .filter(account -> account.getAccountNumber() == account2.getAccountNumber())
                .findFirst()
                .ifPresent(account -> account.setBalance(account.getBalance() - amount));
    }

    @Override
    public List<Account> getAccounts() {
        return accounts;
    }

}
