package com.marzieh.bankingapp.services;

import com.marzieh.bankingapp.entities.Account;
import com.marzieh.bankingapp.exception.AccountNotFoundException;
import com.marzieh.bankingapp.exception.CustomerNotFoundException;
import com.marzieh.bankingapp.exception.InsufficientFundsException;

import java.util.List;

public interface AccountService {

    Account createAccount(String customerId) throws CustomerNotFoundException;
    Account findAccountById(int id) throws AccountNotFoundException;
    void deposit(Account account, double amount);
    void withdraw(Account account, double amount) throws InsufficientFundsException;

    List<Account> getAccounts();
}
