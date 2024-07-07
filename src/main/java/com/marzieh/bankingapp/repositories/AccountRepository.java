package com.marzieh.bankingapp.repositories;

import com.marzieh.bankingapp.entities.Account;
import com.marzieh.bankingapp.entities.Customer;

import java.util.List;

public interface AccountRepository {

    Account saveAccount(Customer customer);
    Account findAccountByAccountNumber(int id);

    void deposit(Account account, double amount);

    void withdraw(Account account, double amount);

    List<Account> getAccounts();
}
