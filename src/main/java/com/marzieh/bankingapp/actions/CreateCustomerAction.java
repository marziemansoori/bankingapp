package com.marzieh.bankingapp.actions;


import com.marzieh.bankingapp.controllers.CustomerController;
import com.marzieh.bankingapp.exception.DuplicateCustomerException;
import com.marzieh.bankingapp.services.CustomerService;

public class CreateCustomerAction implements Action {
    private final CustomerService customerService;

    public CreateCustomerAction(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void execute() throws DuplicateCustomerException {
        CustomerController controller = new CustomerController(customerService);
        controller.createCustomer();
    }
}
