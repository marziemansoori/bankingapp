package com.marzieh.bankingapp.controllers;

import com.marzieh.bankingapp.entities.Customer;
import com.marzieh.bankingapp.entities.LegalCustomer;
import com.marzieh.bankingapp.entities.RealCustomer;
import com.marzieh.bankingapp.exception.DuplicateCustomerException;
import com.marzieh.bankingapp.services.CustomerService;

import java.util.Map;
import java.util.Scanner;


public class CustomerController {

    private static final Scanner scanner = new Scanner(System.in);
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void createCustomer() throws DuplicateCustomerException {

        // TODO:: add validation for types
        System.out.print("Enter customer type (1 for Real, 2 for Legal): ");
        int type = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        System.out.print("Enter customer ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter customer phone number: ");
        String phoneNumber = scanner.nextLine();

        if (type == 1) {
            createRealCustomer(id, name, phoneNumber);
        } else if (type == 2) {
            createLegalCustomer(id, name, phoneNumber);
        } else {
            System.out.println("Invalid customer type.");
        }

    }

    private void createLegalCustomer(String id, String name, String phoneNumber) throws DuplicateCustomerException {
        System.out.print("Enter customer fax number: ");
        String faxNumber = scanner.nextLine();

        LegalCustomer legalCustomer = new LegalCustomer(id, name, phoneNumber, faxNumber);
        customerService.createLegalCustomer(legalCustomer);
        System.out.println(legalCustomer);
    }

    private void createRealCustomer(String id, String name, String phoneNumber) throws DuplicateCustomerException {
        System.out.print("Enter customer family name: ");
        String familyName = scanner.nextLine();

        RealCustomer realCustomer = new RealCustomer(id, name, phoneNumber, familyName);
        customerService.createRealCustomer(realCustomer);
        System.out.println(realCustomer);
    }

    public Map<String, Customer> displayCustomers() {
        return customerService.getCustomers();
    }
}
