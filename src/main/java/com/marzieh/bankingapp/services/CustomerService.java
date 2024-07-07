package com.marzieh.bankingapp.services;

import com.marzieh.bankingapp.entities.Customer;
import com.marzieh.bankingapp.entities.LegalCustomer;
import com.marzieh.bankingapp.entities.RealCustomer;

import java.util.Map;

public interface CustomerService {

    Customer createLegalCustomer(LegalCustomer legalCustomer);

    Customer createRealCustomer(RealCustomer realCustomer);

    Customer findCustomerById(String id);

    Map<String, Customer> getCustomers();
}
