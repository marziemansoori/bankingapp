package com.marzieh.bankingapp.exception;

public class InvalidAmountException extends BankingException{
    public InvalidAmountException(String message) {
        super(message);
    }
}
