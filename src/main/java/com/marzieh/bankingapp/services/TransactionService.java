package com.marzieh.bankingapp.services;

import com.marzieh.bankingapp.entities.Transaction;
import com.marzieh.bankingapp.exception.AccountNotFoundException;
import com.marzieh.bankingapp.exception.InsufficientFundsException;

public interface TransactionService {

    Transaction depositMoney(int accountNumber, double amount) throws AccountNotFoundException;

    Transaction withdraw(int accountNumber, double amount) throws AccountNotFoundException, InsufficientFundsException;

    void transferMoney(int fromAccountNumber, int toAccountNumber, double amount) throws AccountNotFoundException, InsufficientFundsException;
}
