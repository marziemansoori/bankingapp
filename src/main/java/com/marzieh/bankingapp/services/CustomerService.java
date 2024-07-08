package com.marzieh.bankingapp.services;

import com.marzieh.bankingapp.entities.Customer;
import com.marzieh.bankingapp.entities.LegalCustomer;
import com.marzieh.bankingapp.entities.RealCustomer;
import com.marzieh.bankingapp.exception.DuplicateCustomerException;

import java.util.Map;

public interface CustomerService {

    Customer createLegalCustomer(LegalCustomer legalCustomer) throws DuplicateCustomerException;

    Customer createRealCustomer(RealCustomer realCustomer) throws DuplicateCustomerException;

    Customer findCustomerById(String id);

    Map<String, Customer> getCustomers();
}
