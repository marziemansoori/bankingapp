package com.marzieh.bankingapp.actions;


import com.marzieh.bankingapp.controllers.TransactionController;
import com.marzieh.bankingapp.exception.AccountNotFoundException;
import com.marzieh.bankingapp.exception.InsufficientFundsException;
import com.marzieh.bankingapp.services.TransactionService;

public class WithdrawMoneyAction implements Action {
    private final TransactionService transactionService;

    public WithdrawMoneyAction(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void execute() throws AccountNotFoundException, InsufficientFundsException {
        TransactionController controller = new TransactionController(transactionService);
        controller.withdrawMoney();
    }
}
