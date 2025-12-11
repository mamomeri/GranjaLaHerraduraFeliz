package com.GranjaLaHerraduraFeliz.service;

import com.GranjaLaHerraduraFeliz.model.Customer;
import com.GranjaLaHerraduraFeliz.repository.CustomerRepository;
import com.GranjaLaHerraduraFeliz.repository.InMemoryCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para {@link CustomerService}.
 * <p>
 * Refleja el flujo típico de registro de clientes que
 * visitan la granja para alquilar animales.
 */
public class CustomerServiceTest {

    private CustomerRepository customerRepository;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerRepository = new InMemoryCustomerRepository();
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void registerCustomer_assignsIdAndStoresName() {
        // Act
        Customer customer = customerService.registerCustomer("Luis Jinete");

        // Assert
        assertTrue(customer.getId() > 0, "El cliente debería tener un ID generado");
        assertEquals("Luis Jinete", customer.getFullName());
    }

    @Test
    void listAllCustomers_returnsAllRegisteredCustomers() {
        // Arrange
        customerService.registerCustomer("Cliente Uno");
        customerService.registerCustomer("Cliente Dos");

        // Act
        List<Customer> customers = customerService.listAllCustomers();

        // Assert
        assertEquals(2, customers.size(), "Debería listar todos los clientes registrados");
        assertTrue(customers.stream()
                .anyMatch(c -> "Cliente Uno".equals(c.getFullName())));
    }
}
