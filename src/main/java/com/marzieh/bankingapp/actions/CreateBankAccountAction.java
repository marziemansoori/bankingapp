package com.marzieh.bankingapp.actions;


import com.marzieh.bankingapp.controllers.AccountController;
import com.marzieh.bankingapp.exception.CustomerNotFoundException;
import com.marzieh.bankingapp.services.AccountService;

public class CreateBankAccountAction implements Action {
    private final AccountService accountService;

    public CreateBankAccountAction(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void execute() throws CustomerNotFoundException {
        AccountController controller = new AccountController(accountService);
        controller.createAccount();
    }
}
