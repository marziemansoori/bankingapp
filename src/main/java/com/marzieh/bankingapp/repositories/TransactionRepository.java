package com.marzieh.bankingapp.repositories;

import com.marzieh.bankingapp.entities.Transaction;

public interface TransactionRepository {

    void saveTransaction(Transaction transaction);
}
