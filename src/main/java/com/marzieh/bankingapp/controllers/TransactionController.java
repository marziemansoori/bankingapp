package com.marzieh.bankingapp.controllers;

import com.marzieh.bankingapp.exception.AccountNotFoundException;
import com.marzieh.bankingapp.exception.InsufficientFundsException;
import com.marzieh.bankingapp.services.TransactionService;

import java.util.Scanner;

public class TransactionController {

    private static final Scanner scanner = new Scanner(System.in);

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    public void depositMoney() throws AccountNotFoundException {
        System.out.print("Enter account number: ");
        int accountNumber = scanner.nextInt();
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();

        transactionService.depositMoney(accountNumber, amount);
    }

    public void withdrawMoney() throws AccountNotFoundException, InsufficientFundsException {
        System.out.print("Enter account number: ");
        int accountNumber = scanner.nextInt();
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();

        transactionService.withdraw(accountNumber, amount);

    }

    public void transferMoney() {
        System.out.print("Enter source account number: ");
        int fromAccountNumber = scanner.nextInt();
        System.out.print("Enter destination account number: ");
        int toAccountNumber = scanner.nextInt();
        System.out.print("Enter transfer amount: ");
        double amount = scanner.nextDouble();

        try {
            transactionService.transferMoney(fromAccountNumber, toAccountNumber, amount);
            System.out.println("Transfer successful.");
        } catch (InsufficientFundsException | AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
