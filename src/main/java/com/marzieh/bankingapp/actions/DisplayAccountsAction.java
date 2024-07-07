package com.marzieh.bankingapp.actions;


import com.marzieh.bankingapp.controllers.AccountController;
import com.marzieh.bankingapp.entities.Account;
import com.marzieh.bankingapp.services.AccountService;

import java.util.List;

public class DisplayAccountsAction implements Action {
    private final AccountService accountService;

    public DisplayAccountsAction(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void execute(){
        AccountController controller = new AccountController(accountService);
        List<Account> accounts = controller.displayAccounts();
        System.out.println(accounts);
    }
}
