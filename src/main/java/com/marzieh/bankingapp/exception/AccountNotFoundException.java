package com.marzieh.bankingapp.exception;

public class AccountNotFoundException extends NotFoundException{
    public AccountNotFoundException(String message) {
        super(message);
    }
}
