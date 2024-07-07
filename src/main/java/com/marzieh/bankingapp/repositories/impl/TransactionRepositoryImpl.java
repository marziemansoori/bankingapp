package com.marzieh.bankingapp.repositories.impl;

import com.marzieh.bankingapp.entities.Transaction;
import com.marzieh.bankingapp.repositories.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryImpl implements TransactionRepository {

    private final List<Transaction> transactions;

    public TransactionRepositoryImpl() {
        transactions = new ArrayList<>();
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
