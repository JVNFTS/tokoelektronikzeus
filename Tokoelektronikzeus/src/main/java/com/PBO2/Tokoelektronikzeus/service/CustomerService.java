package com.PBO2.Tokoelektronikzeus.service;

import com.PBO2.Tokoelektronikzeus.model.Customer;
import com.PBO2.Tokoelektronikzeus.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer getById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public List<Customer> cari(String keyword) {
        return customerRepository.findByNamaCustomerContainingIgnoreCase(keyword);
    }

    public void simpan(Customer customer) {
        customerRepository.save(customer);
    }

    public void hapus(Long id) {
        customerRepository.deleteById(id);
    }
}