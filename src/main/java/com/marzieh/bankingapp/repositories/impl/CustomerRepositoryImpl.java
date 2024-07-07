package com.marzieh.bankingapp.repositories.impl;

import com.marzieh.bankingapp.entities.Customer;
import com.marzieh.bankingapp.entities.LegalCustomer;
import com.marzieh.bankingapp.entities.RealCustomer;
import com.marzieh.bankingapp.repositories.CustomerRepository;

import java.util.HashMap;
import java.util.Map;

public class CustomerRepositoryImpl implements CustomerRepository {

    private final Map<String, Customer> customers;

    public CustomerRepositoryImpl() {
        this.customers = new HashMap<>();
    }

    @Override
    public synchronized Customer saveLegalCustomer(LegalCustomer legalCustomer) {
        return customers.put(legalCustomer.getId(), legalCustomer);
    }

    @Override
    public synchronized Customer saveRealCustomer(RealCustomer realCustomer) {
        return customers.put(realCustomer.getId(), realCustomer);
    }

    @Override
    public synchronized Customer findCustomerById(String id) {
        return customers.get(id);
    }

    @Override
    public Map<String, Customer> getCustomers() {
        return customers;
    }

}
