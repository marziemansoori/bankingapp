package com.marzieh.bankingapp.actions;


import com.marzieh.bankingapp.controllers.TransactionController;
import com.marzieh.bankingapp.exception.AccountNotFoundException;
import com.marzieh.bankingapp.services.TransactionService;

public class DepositMoneyAction implements Action {
    private final TransactionService transactionService;

    public DepositMoneyAction(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void execute() throws AccountNotFoundException {
        TransactionController controller = new TransactionController(transactionService);
        controller.depositMoney();
    }
}
