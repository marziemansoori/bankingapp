package com.marzieh.bankingapp.actions;


import com.marzieh.bankingapp.controllers.CustomerController;
import com.marzieh.bankingapp.entities.Customer;
import com.marzieh.bankingapp.services.CustomerService;

import java.util.Map;

public class DisplayCustomersAction implements Action {
    private final CustomerService customerService;

    public DisplayCustomersAction(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void execute(){
        CustomerController controller = new CustomerController(customerService);
        Map<String, Customer> customers = controller.displayCustomers();
        System.out.println(customers);
    }
}
