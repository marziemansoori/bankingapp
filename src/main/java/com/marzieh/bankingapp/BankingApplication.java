package com.marzieh.bankingapp;

import com.marzieh.bankingapp.actions.Action;
import com.marzieh.bankingapp.factory.ActionFactory;
import com.marzieh.bankingapp.repositories.AccountRepository;
import com.marzieh.bankingapp.repositories.CustomerRepository;
import com.marzieh.bankingapp.repositories.TransactionRepository;
import com.marzieh.bankingapp.repositories.impl.AccountRepositoryImpl;
import com.marzieh.bankingapp.repositories.impl.CustomerRepositoryImpl;
import com.marzieh.bankingapp.repositories.impl.TransactionRepositoryImpl;
import com.marzieh.bankingapp.services.AccountService;
import com.marzieh.bankingapp.services.CustomerService;
import com.marzieh.bankingapp.services.TransactionService;
import com.marzieh.bankingapp.services.AccountServiceImpl;
import com.marzieh.bankingapp.services.CustomerServiceImpl;
import com.marzieh.bankingapp.services.TransactionServiceImpl;

import java.util.Scanner;

public class BankingApplication {
    private static final Scanner scanner = new Scanner(System.in);

    private static final CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private static final CustomerService customerService = new CustomerServiceImpl(customerRepository);
    private static final AccountRepository accountRepository = new AccountRepositoryImpl();
    private static final TransactionRepository transactionRepository = new TransactionRepositoryImpl();
    private static final AccountService accountService = new AccountServiceImpl(accountRepository, customerService);
    private static final TransactionService transactionService = new TransactionServiceImpl(transactionRepository, accountService);

    public static void main(String[] args) {
        ActionFactory actionFactory = new ActionFactory(customerService, accountService, transactionService);
        boolean exit = false;

        while (!exit) {
            printMainMenu();
            int choice = getChoice();

            if (choice == 0) {
                exit = true;
            } else {
                Action action = actionFactory.getAction(choice);
                if (action != null) {
                    try {
                        action.execute();
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        }
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\nBanking Application Menu:");
        System.out.println("1. Create Customer");
        System.out.println("2. Create Bank Account");
        System.out.println("3. Deposit Money");
        System.out.println("4. Withdraw Money");
        System.out.println("5. Transfer Money");
        System.out.println("6. Display Customers");
        System.out.println("7. Display Accounts");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getChoice() {
        return scanner.nextInt();
    }
}