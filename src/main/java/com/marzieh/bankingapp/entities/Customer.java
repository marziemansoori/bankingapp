package com.marzieh.bankingapp.entities;

import com.marzieh.bankingapp.util.CustomerType;

public abstract class Customer {
    private final String id;
    private final String name;
    private final String phoneNumber;

    private final CustomerType type;

    public Customer(String id, String name, String phoneNumber, CustomerType type) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "Customer ID: " + id + ", Name: " + name + ", Phone: " + phoneNumber;
    }

    public CustomerType getType() {
        return type;
    }
}
