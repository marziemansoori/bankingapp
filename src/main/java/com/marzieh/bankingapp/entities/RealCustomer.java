package com.marzieh.bankingapp.entities;

import com.marzieh.bankingapp.util.CustomerType;

public class RealCustomer extends Customer {
    private final String familyName;

    public RealCustomer(String id, String name, String phoneNumber, String familyName) {
        super(id, name, phoneNumber, CustomerType.real);
        this.familyName = familyName;
    }

    public String getFamilyName() {
        return familyName;
    }

    @Override
    public String toString() {
        return super.toString() + ", Family Name: " + familyName;
    }
}
