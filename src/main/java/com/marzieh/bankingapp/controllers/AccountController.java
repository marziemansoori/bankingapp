package com.marzieh.bankingapp.controllers;

import com.marzieh.bankingapp.entities.Account;
import com.marzieh.bankingapp.exception.CustomerNotFoundException;
import com.marzieh.bankingapp.services.AccountService;

import java.util.List;
import java.util.Scanner;

public class AccountController {

    private static final Scanner scanner = new Scanner(System.in);
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // TODO:: add exception handling
    public void createAccount() throws CustomerNotFoundException {
        System.out.print("Enter customer ID for the new bank account: ");
        String customerId = scanner.next();

        Account account = accountService.createAccount(customerId);
        System.out.println("Bank account created: " + account);
    }

    public List<Account> displayAccounts() {
        return accountService.getAccounts();
    }
}
