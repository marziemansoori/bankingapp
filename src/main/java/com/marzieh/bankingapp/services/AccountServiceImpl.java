package com.marzieh.bankingapp.services;

import com.marzieh.bankingapp.entities.Account;
import com.marzieh.bankingapp.entities.Customer;
import com.marzieh.bankingapp.exception.AccountNotFoundException;
import com.marzieh.bankingapp.exception.CustomerNotFoundException;
import com.marzieh.bankingapp.exception.InsufficientFundsException;
import com.marzieh.bankingapp.repositories.AccountRepository;
import com.marzieh.bankingapp.services.AccountService;
import com.marzieh.bankingapp.services.CustomerService;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;

    public AccountServiceImpl(AccountRepository accountRepository, CustomerService customerService) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
    }

    @Override
    public Account createAccount(String customerId) throws CustomerNotFoundException {
        Customer customer = customerService.findCustomerById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with id of " + customerId + " not found");
        }

        // TODO:: check if customer has a account already

        return accountRepository.saveAccount(customer);
    }

    @Override
    public Account findAccountById(int id) throws AccountNotFoundException {
        Account account = accountRepository.findAccountByAccountNumber(id);

        if (account == null) {
            throw new AccountNotFoundException("Account with number of " + id + " not found");
        }

        return account;
    }

    @Override
    public void deposit(Account account, double amount) {
        accountRepository.deposit(account, amount);
    }

    @Override
    public void withdraw(Account account, double amount) throws InsufficientFundsException {
        if(amount > account.getBalance()) {
            throw new InsufficientFundsException("Insufficient Fund");
        }

        accountRepository.withdraw(account, amount);
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.getAccounts();
    }
}
