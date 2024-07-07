package com.marzieh.bankingapp.services;

import com.marzieh.bankingapp.entities.Customer;
import com.marzieh.bankingapp.entities.LegalCustomer;
import com.marzieh.bankingapp.entities.RealCustomer;
import com.marzieh.bankingapp.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private LegalCustomer mockLegalCustomer;
    private RealCustomer mockRealCustomer;

    @BeforeEach
    public void setUp() {
        mockLegalCustomer = new LegalCustomer("1", "John", "1234567890", "0987654321");
        mockRealCustomer = new RealCustomer("1", "John", "1234567890", "Doe");
    }

    @Test
    public void testCreateLegalCustomer() {
        when(customerRepository.saveLegalCustomer(mockLegalCustomer)).thenReturn(mockLegalCustomer);

        Customer createdCustomer = customerService.createLegalCustomer(mockLegalCustomer);

        assertNotNull(createdCustomer);
        assertEquals(mockLegalCustomer, createdCustomer);
        verify(customerRepository, times(1)).saveLegalCustomer(mockLegalCustomer);
    }

    @Test
    public void testCreateRealCustomer() {
        when(customerRepository.saveRealCustomer(mockRealCustomer)).thenReturn(mockRealCustomer);

        Customer createdCustomer = customerService.createRealCustomer(mockRealCustomer);

        assertNotNull(createdCustomer);
        assertEquals(mockRealCustomer, createdCustomer);
        verify(customerRepository, times(1)).saveRealCustomer(mockRealCustomer);
    }


    @Test
    public void testFindCustomerById() {
        String customerId = "1";
        when(customerRepository.findCustomerById(customerId)).thenReturn(mockLegalCustomer);

        Customer foundCustomer = customerService.findCustomerById(customerId);

        assertNotNull(foundCustomer);
        assertEquals(mockLegalCustomer, foundCustomer);
        verify(customerRepository, times(1)).findCustomerById(customerId);
    }

    @Test
    public void testFindCustomerByIdNotFound() {
        String customerId = "2";
        when(customerRepository.findCustomerById(customerId)).thenReturn(null);

        Customer foundCustomer = customerService.findCustomerById(customerId);

        assertNull(foundCustomer);
        verify(customerRepository, times(1)).findCustomerById(customerId);
    }

}
