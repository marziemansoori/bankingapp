package com.marzieh.bankingapp.repositories;

import com.marzieh.bankingapp.entities.Customer;
import com.marzieh.bankingapp.entities.LegalCustomer;
import com.marzieh.bankingapp.entities.RealCustomer;

import java.util.Map;

public interface CustomerRepository {

    Customer saveLegalCustomer(LegalCustomer legalCustomer);

    Customer saveRealCustomer(RealCustomer realCustomer);

    Customer findCustomerById(String id);

    Map<String, Customer> getCustomers();
}
