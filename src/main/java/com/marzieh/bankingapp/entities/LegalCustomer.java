package com.marzieh.bankingapp.entities;

import com.marzieh.bankingapp.util.CustomerType;

public class LegalCustomer extends Customer {
    private final String faxNumber;

    public LegalCustomer(String id, String name, String phoneNumber, String faxNumber) {
        super(id, name, phoneNumber, CustomerType.legal);
        this.faxNumber = faxNumber;
    }

    public String getFaxNumber() {
        return this.faxNumber;
    }

    @Override
    public String toString() {
        return super.toString() + ", Fax: " + faxNumber;
    }
}
