package com.marzieh.bankingapp.services;

import com.marzieh.bankingapp.entities.Account;
import com.marzieh.bankingapp.entities.Transaction;
import com.marzieh.bankingapp.exception.AccountNotFoundException;
import com.marzieh.bankingapp.exception.InsufficientFundsException;
import com.marzieh.bankingapp.repositories.TransactionRepository;
import com.marzieh.bankingapp.util.TransactionType;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Override
    public synchronized Transaction depositMoney(int accountNumber, double amount) throws AccountNotFoundException {
        Account account = getAccount(accountNumber);

        Transaction transaction = new Transaction(TransactionType.DEPOSIT, amount, accountNumber, "Deposit money");
        accountService.deposit(account, amount);
        transactionRepository.saveTransaction(transaction);

        System.out.println("Deposit successful. New balance: " + account.getBalance());
        return transaction;
    }

    @Override
    public Transaction withdraw(int accountNumber, double amount) throws AccountNotFoundException, InsufficientFundsException {
        Account account = getAccount(accountNumber);

        Transaction transaction = new Transaction(TransactionType.WITHDRAWAL, amount, accountNumber, "Withdraw money");
        accountService.withdraw(account, amount);
        transactionRepository.saveTransaction(transaction);

        System.out.println("Withdrawal successful. New balance: " + account.getBalance());
        return transaction;
    }

    @Override
    public void transferMoney(int fromAccountNumber, int toAccountNumber, double amount) throws AccountNotFoundException, InsufficientFundsException {
        Account fromAccount = getAccount(fromAccountNumber);
        Account toAccount = getAccount(toAccountNumber);

        synchronized (fromAccount) {
            synchronized (toAccount) {
                Transaction transaction = new Transaction(TransactionType.WITHDRAWAL, amount, fromAccountNumber, "Transfer money to account number: " + toAccount.getAccountNumber());
                accountService.withdraw(fromAccount, amount);
                transactionRepository.saveTransaction(transaction);

                Transaction toAccountTransaction = new Transaction(TransactionType.DEPOSIT, amount, toAccountNumber, "Transfer money from account number: " + fromAccount.getAccountNumber());
                accountService.deposit(toAccount, amount);
                transactionRepository.saveTransaction(toAccountTransaction);
            }
        }
    }

    private Account getAccount(int accountNumber) throws AccountNotFoundException {
        Account account = accountService.findAccountById(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account with account number of " + accountNumber + " not found");
        }
        return account;
    }
}
