package com.marzieh.bankingapp.services;

import com.marzieh.bankingapp.entities.Account;
import com.marzieh.bankingapp.entities.LegalCustomer;
import com.marzieh.bankingapp.entities.Transaction;
import com.marzieh.bankingapp.exception.AccountNotFoundException;
import com.marzieh.bankingapp.repositories.TransactionRepository;
import com.marzieh.bankingapp.util.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private Transaction mockTransaction;

    @BeforeEach
    public void setUp() {
        LegalCustomer mockCustomer = new LegalCustomer("1", "John", "1234567890", "0987654321");
        mockAccount = new Account(mockCustomer);
        mockTransaction = new Transaction(TransactionType.DEPOSIT, 500.0, mockAccount.getAccountNumber(), "Deposit money");
    }

    // TODO:: fix test
    @Disabled
    public void testDepositMoneySuccess() throws AccountNotFoundException {
        when(accountService.findAccountById(mockAccount.getAccountNumber())).thenReturn(mockAccount);
        doNothing().when(accountService).deposit(mockAccount, 500.0);
        doNothing().when(transactionRepository).saveTransaction(mockTransaction);

        Transaction transaction = transactionService.depositMoney(mockAccount.getAccountNumber(), 500.0);

        assertNotNull(transaction);
        assertEquals(TransactionType.DEPOSIT, transaction.getType());
        assertEquals(500.0, transaction.getAmount());
        assertEquals(12345, transaction.getAccountNumber());
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
}
