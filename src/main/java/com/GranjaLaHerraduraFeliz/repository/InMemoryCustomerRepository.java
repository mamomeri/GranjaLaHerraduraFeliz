package com.GranjaLaHerraduraFeliz.repository;

import com.GranjaLaHerraduraFeliz.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCustomerRepository implements CustomerRepository {

    private final List<Customer> customers = new ArrayList<>();
    private int nextId = 1;

    @Override
    public Customer save(Customer customer) {
        if (customer.getId() == 0) {
            customer.setId(nextId++);
            customers.add(customer);
        } else {
            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).getId() == customer.getId()) {
                    customers.set(i, customer);
                    return customer;
                }
            }
            customers.add(customer);
        }
        return customer;
    }

    @Override
    public Customer findById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers);
    }
}
