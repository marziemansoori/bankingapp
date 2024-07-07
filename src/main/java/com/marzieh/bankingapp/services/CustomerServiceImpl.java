package com.marzieh.bankingapp.services;

import com.marzieh.bankingapp.entities.Customer;
import com.marzieh.bankingapp.entities.LegalCustomer;
import com.marzieh.bankingapp.entities.RealCustomer;
import com.marzieh.bankingapp.repositories.CustomerRepository;
import com.marzieh.bankingapp.services.CustomerService;

import java.util.Map;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createLegalCustomer(LegalCustomer legalCustomer) {
        // TODO:; check customer id should be unique
        return customerRepository.saveLegalCustomer(legalCustomer);
    }

    @Override
    public Customer createRealCustomer(RealCustomer realCustomer) {
        // TODO:; check customer id should be unique
        return customerRepository.saveRealCustomer(realCustomer);
    }

    @Override
    public Customer findCustomerById(String id) {
        return customerRepository.findCustomerById(id);
    }

    @Override
    public Map<String, Customer> getCustomers() {
        return customerRepository.getCustomers();
    }
}
