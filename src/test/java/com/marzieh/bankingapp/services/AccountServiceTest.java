package com.marzieh.bankingapp.services;

import com.marzieh.bankingapp.entities.Account;
import com.marzieh.bankingapp.entities.Customer;
import com.marzieh.bankingapp.entities.LegalCustomer;
import com.marzieh.bankingapp.entities.RealCustomer;
import com.marzieh.bankingapp.exception.AccountNotFoundException;
import com.marzieh.bankingapp.exception.CustomerNotFoundException;
import com.marzieh.bankingapp.exception.InsufficientFundsException;
import com.marzieh.bankingapp.repositories.AccountRepository;
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
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Customer mockCustomer;
    private Account mockAccount;

    @BeforeEach
    public void setUp() {
        mockCustomer = new RealCustomer("1", "Alice", "1234567890", "Smith");
        mockAccount = new Account(mockCustomer);
    }

    @Test
    void createAccount() throws CustomerNotFoundException {

        String existingCustomerId = "456";
        Customer mockCustomer = new LegalCustomer(existingCustomerId, "John", "09090", "123");
        Account mockAccount = new Account(mockCustomer);

        // Mock customerService to return a customer for the given ID
        when(customerService.findCustomerById(existingCustomerId)).thenReturn(mockCustomer);

        // Mock accountRepository to save the account successfully
        when(accountRepository.saveAccount(mockCustomer)).thenReturn(mockAccount);

        // Call the method under test with the existing customer ID
        Account createdAccount = accountService.createAccount(existingCustomerId);

        // Verify that the correct account is returned
        assertThat(createdAccount, is(mockAccount));
    }

    @Test
    public void testCreateAccountCustomerExists() throws CustomerNotFoundException {
        when(customerService.findCustomerById("1")).thenReturn(mockCustomer);
        when(accountRepository.saveAccount(mockCustomer)).thenReturn(mockAccount);

        Account createdAccount = accountService.createAccount("1");

        assertNotNull(createdAccount);
        assertEquals(mockCustomer, createdAccount.getCustomer());
        verify(customerService, times(1)).findCustomerById("1");
        verify(accountRepository, times(1)).saveAccount(mockCustomer);
    }

    @Test
    public void testCreateAccountCustomerNotFound() {
        String nonExistentCustomerId = "123";

        // Mock customerService to return null for the given customer ID
        when(customerService.findCustomerById(nonExistentCustomerId)).thenReturn(null);

        assertThrows(CustomerNotFoundException.class, () -> {
            accountService.createAccount(nonExistentCustomerId);
        });
    }

    @Test
    public void testFindAccountById_AccountNotFound() {
        int nonExistentAccountId = 123;

        // Mock accountRepository to return null for the given ID
        when(accountRepository.findAccountByAccountNumber(nonExistentAccountId)).thenReturn(null);

        // Calling the method under test with the non-existent account ID should throw the exception
        assertThrows(AccountNotFoundException.class, () -> {
            accountService.findAccountById(nonExistentAccountId);
        });
    }

    @Test
    public void testFindAccountById_Success() throws Exception {
        Account mockAccount = new Account(mockCustomer);
        int existingAccountId = mockAccount.getAccountNumber();

        // Mock accountRepository to return an account for the given ID
        when(accountRepository.findAccountByAccountNumber(existingAccountId)).thenReturn(mockAccount);

        // Call the method under test with the existing account ID
        Account foundAccount = accountService.findAccountById(existingAccountId);

        // Verify that the correct account is returned
        assertThat(foundAccount, is(mockAccount));
    }

    @Test
    public void testDeposit_Success() {
        double amount = 100.0;

        accountService.deposit(mockAccount, amount);

        verify(accountRepository, times(1)).deposit(mockAccount, amount);
    }

    @Test
    public void testWithdrawSuccess() throws InsufficientFundsException {
        double amount = 200.0;

        mockAccount.setBalance(amount);
        accountService.withdraw(mockAccount, amount);

        verify(accountRepository, times(1)).withdraw(mockAccount, amount);
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        double amount = 600.0;

        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class, () -> {
            accountService.withdraw(mockAccount, amount);
        });

        assertEquals("Insufficient Fund", exception.getMessage());
        verify(accountRepository, times(0)).withdraw(mockAccount, amount);
    }
}