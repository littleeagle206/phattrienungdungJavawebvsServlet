package com.codegym.service;

import com.codegym.model.Customer;

import java.util.List;

/**
 * Service: cac thao tac quan ly khach hang (them, sua, xoa, tim kiem).
 */
public interface CustomerService {
    List<Customer> findAll();

    void save(Customer customer);

    Customer findById(int id);

    void update(int id, Customer customer);

    void remove(int id);
}
