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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @Test
    public void testTransferMoney() throws AccountNotFoundException, InsufficientFundsException {
        LegalCustomer fromCustomer = new LegalCustomer("1", "John", "1234567890", "0987654321");
        LegalCustomer toCustomer = new LegalCustomer("2", "Sarah", "876543788", "9877654455");
        Account fromAccount = new Account(fromCustomer);
        Account toAccount = new Account(toCustomer);
        fromAccount.setBalance(100);

        when(accountService.findAccountById(fromAccount.getAccountNumber())).thenReturn(fromAccount);
        when(accountService.findAccountById(toAccount.getAccountNumber())).thenReturn(toAccount);

        transactionService.transferMoney(fromAccount.getAccountNumber(), toAccount.getAccountNumber(), 100.0);

        verify(accountService).withdraw(fromAccount, 100.0);
        verify(accountService).deposit(toAccount, 100.0);

        // Verify that the transactions were saved correctly
        verify(transactionRepository, times(1)).saveTransaction(argThat(transaction ->
                transaction.getType() == TransactionType.WITHDRAWAL &&
                        transaction.getAmount() == 100.0 &&
                        transaction.getAccountNumber() == fromAccount.getAccountNumber() &&
                        transaction.getDescription().equals("Transfer money to account number: " + toAccount.getAccountNumber())
        ));

        verify(transactionRepository, times(1)).saveTransaction(argThat(transaction ->
                transaction.getType() == TransactionType.DEPOSIT &&
                        transaction.getAmount() == 100.0 &&
                        transaction.getAccountNumber() == toAccount.getAccountNumber() &&
                        transaction.getDescription().equals("Transfer money from account number: " + fromAccount.getAccountNumber())
        ));
    }

    @Test
    public void testTransferMoney_InsufficientFunds() throws AccountNotFoundException, InsufficientFundsException {
        LegalCustomer fromCustomer = new LegalCustomer("1", "John", "1234567890", "0987654321");
        LegalCustomer toCustomer = new LegalCustomer("2", "Sarah", "876543788", "9877654455");
        Account fromAccount = new Account(fromCustomer);
        Account toAccount = new Account(toCustomer);

        fromAccount.setBalance(50.0); // Set balance to less than transfer amount

        when(accountService.findAccountById(fromAccount.getAccountNumber())).thenReturn(fromAccount);
        when(accountService.findAccountById(toAccount.getAccountNumber())).thenReturn(toAccount);

        // Configure withdraw method to throw InsufficientFundsException when amount is greater than balance
        doAnswer(invocation -> {
            Account account = invocation.getArgument(0);
            double amount = invocation.getArgument(1);
            if (amount > account.getBalance()) {
                throw new InsufficientFundsException("Insufficient funds");
            }
            return null;
        }).when(accountService).withdraw(any(Account.class), anyDouble());

        assertThrows(InsufficientFundsException.class, () -> {
            transactionService.transferMoney(fromAccount.getAccountNumber(), toAccount.getAccountNumber(), 1000.0);
        });

        verify(accountService, never()).withdraw(fromAccount, 100.0);
        verify(accountService, never()).deposit(toAccount, 100.0);
        verify(transactionRepository, never()).saveTransaction(any(Transaction.class));
    }

    @Test
    public void testTransferMoney_AccountNotFound() throws InsufficientFundsException, AccountNotFoundException {
        when(accountService.findAccountById(1)).thenThrow(new AccountNotFoundException("Account not found"));

        assertThrows(AccountNotFoundException.class, () -> {
            transactionService.transferMoney(1, 2, 100.0);
        });

        verify(accountService, never()).withdraw(any(Account.class), anyDouble());
        verify(accountService, never()).deposit(any(Account.class), anyDouble());
        verify(transactionRepository, never()).saveTransaction(any(Transaction.class));
    }

    @Test
    public void testTransferMoney_ThreadSafety() throws InterruptedException, AccountNotFoundException, InsufficientFundsException {
        LegalCustomer fromCustomer = new LegalCustomer("1", "John", "1234567890", "0987654321");
        LegalCustomer toCustomer = new LegalCustomer("2", "Sarah", "876543788", "9877654455");
        Account fromAccount = new Account(fromCustomer);
        Account toAccount = new Account(toCustomer);

        // initial balance
        fromAccount.setBalance(10000.0);
        toAccount.setBalance(5000.0);

        when(accountService.findAccountById(fromAccount.getAccountNumber())).thenReturn(fromAccount);
        when(accountService.findAccountById(toAccount.getAccountNumber())).thenReturn(toAccount);


        doAnswer(invocation -> {
            Account account = invocation.getArgument(0);
            double amount = invocation.getArgument(1);
            account.setBalance(account.getBalance() - amount);
            return null;
        }).when(accountService).withdraw(any(Account.class), anyDouble());

        doAnswer(invocation -> {
            Account account = invocation.getArgument(0);
            double amount = invocation.getArgument(1);
            account.setBalance(account.getBalance() + amount);
            return null;
        }).when(accountService).deposit(any(Account.class), anyDouble());


        int numThreads = 10;
        double transferAmount = 100.0;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int i = 0; i < numThreads; i++) {
            executorService.submit(() -> {
                try {
                    transactionService.transferMoney(fromAccount.getAccountNumber(), toAccount.getAccountNumber(), transferAmount);
                } catch (AccountNotFoundException | InsufficientFundsException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        double expectedFromAccountBalance = 10000.0 - numThreads * transferAmount;
        double expectedToAccountBalance = 5000.0 + numThreads * transferAmount;
        assertEquals(expectedFromAccountBalance, fromAccount.getBalance(), 0.001);
        assertEquals(expectedToAccountBalance, toAccount.getBalance(), 0.001);
    }
}
