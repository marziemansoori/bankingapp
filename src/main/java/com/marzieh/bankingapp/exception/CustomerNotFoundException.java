package com.marzieh.bankingapp.exception;

public class CustomerNotFoundException extends NotFoundException{
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
