package com.marzieh.bankingapp.actions;


import com.marzieh.bankingapp.controllers.TransactionController;
import com.marzieh.bankingapp.services.TransactionService;

public class TransferMoneyAction implements Action {
    private final TransactionService transactionService;

    public TransferMoneyAction(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void execute() {
        TransactionController controller = new TransactionController(transactionService);
        controller.transferMoney();
    }
}
