package com.marzieh.bankingapp.services;

import com.marzieh.bankingapp.entities.Account;
import com.marzieh.bankingapp.entities.LegalCustomer;
import com.marzieh.bankingapp.entities.Transaction;
import com.marzieh.bankingapp.exception.AccountNotFoundException;
import com.marzieh.bankingapp.exception.InsufficientFundsException;
import com.marzieh.bankingapp.repositories.TransactionRepository;
import com.marzieh.bankingapp.util.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Account mockAccount;

    @BeforeEach
    public void setUp() {
        LegalCustomer mockCustomer = new LegalCustomer("1", "John", "1234567890", "0987654321");
        mockAccount = new Account(mockCustomer);
    }

    @Test
    public void testDepositMoneySuccess() throws AccountNotFoundException {
        when(accountService.findAccountById(mockAccount.getAccountNumber())).thenReturn(mockAccount);
        doNothing().when(accountService).deposit(mockAccount, 500.0);

        Transaction transaction = transactionService.depositMoney(mockAccount.getAccountNumber(), 500.0);

        assertNotNull(transaction);
        assertEquals(TransactionType.DEPOSIT, transaction.getType());
        assertEquals(500.0, transaction.getAmount());
        assertEquals(mockAccount.getAccountNumber(), transaction.getAccountNumber());
        assertEquals("Deposit money", transaction.getDescription());

        verify(accountService, times(1)).findAccountById(mockAccount.getAccountNumber());
        verify(accountService, times(1)).deposit(mockAccount, 500.0);
        verify(transactionRepository, times(1)).saveTransaction(transaction);
    }

    @Test
    public void testDepositMoneyAccountNotFound() throws AccountNotFoundException {
        when(accountService.findAccountById(12345)).thenThrow(new AccountNotFoundException("Account not found"));

        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
            transactionService.depositMoney(12345, 500.0);
        });

        assertEquals("Account not found", exception.getMessage());
        verify(accountService, times(1)).findAccountById(12345);
        verify(accountService, times(0)).deposit(any(Account.class), anyDouble());
        verify(transactionRepository, times(0)).saveTransaction(any(Transaction.class));
    }

    @Test
    public void testWithdrawMoneySuccess() throws AccountNotFoundException, InsufficientFundsException {
        when(accountService.findAccountById(mockAccount.getAccountNumber())).thenReturn(mockAccount);
        doNothing().when(accountService).withdraw(mockAccount, 500.0);

        Transaction transaction = transactionService.withdraw(mockAccount.getAccountNumber(), 500.0);

        assertNotNull(transaction);
        assertEquals(TransactionType.WITHDRAWAL, transaction.getType());
        assertEquals(500.0, transaction.getAmount());
        assertEquals(mockAccount.getAccountNumber(), transaction.getAccountNumber());
        assertEquals("Withdraw money", transaction.getDescription());

        verify(accountService, times(1)).findAccountById(mockAccount.getAccountNumber());
        verify(accountService, times(1)).withdraw(mockAccount, 500.0);
        verify(transactionRepository, times(1)).saveTransaction(transaction);
    }

    @Test
    public void testWithdrawMoneyAccountNotFound() throws AccountNotFoundException {
        when(accountService.findAccountById(12345)).thenThrow(new AccountNotFoundException("Account not found"));

        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
            transactionService.withdraw(12345, 500.0);
        });

        assertEquals("Account not found", exception.getMessage());
        verify(accountService, times(1)).findAccountById(12345);
        verify(accountService, times(0)).deposit(any(Account.class), anyDouble());
        verify(transactionRepository, times(0)).saveTransaction(any(Transaction.class));
    }
}
