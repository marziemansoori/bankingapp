package com.marzieh.bankingapp.factory;

import com.marzieh.bankingapp.actions.*;
import com.marzieh.bankingapp.services.*;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private final Map<Integer, Action> actions = new HashMap<>();

    public ActionFactory(CustomerService customerService, AccountService accountService, TransactionService transactionService) {
        actions.put(1, new CreateCustomerAction(customerService));
        actions.put(2, new CreateBankAccountAction(accountService));
        actions.put(3, new DepositMoneyAction(transactionService));
        actions.put(4, new WithdrawMoneyAction(transactionService));
        actions.put(5, new TransferMoneyAction(transactionService));
        actions.put(6, new DisplayCustomersAction(customerService));
        actions.put(7, new DisplayAccountsAction(accountService));
    }

    public Action getAction(int choice) {
        return actions.get(choice);
    }
}

