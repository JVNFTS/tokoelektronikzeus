package com.PBO2.Tokoelektronikzeus.service;

import com.PBO2.Tokoelektronikzeus.model.Customer;
import com.PBO2.Tokoelektronikzeus.repository.CustomerRepository;
import com.PBO2.Tokoelektronikzeus.repository.PenjualanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PenjualanRepository penjualanRepository;

    public CustomerService(CustomerRepository customerRepository,
                           PenjualanRepository penjualanRepository) {
        this.customerRepository = customerRepository;
        this.penjualanRepository = penjualanRepository;
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

    public void tambah(Customer customer) {
        if (customerRepository.existsByNamaCustomerIgnoreCase(customer.getNamaCustomer())) {
            throw new RuntimeException("Nama customer '" + customer.getNamaCustomer() + "' sudah terdaftar.");
        }
        customerRepository.save(customer);
    }

    public void update(Customer customer) {
        if (customerRepository.existsByNamaCustomerIgnoreCaseAndIdNot(
                customer.getNamaCustomer(), customer.getId())) {
            throw new RuntimeException("Nama customer '" + customer.getNamaCustomer() + "' sudah digunakan customer lain.");
        }
        customerRepository.save(customer);
    }

    public void simpan(Customer customer) {
        customerRepository.save(customer);
    }

    public void hapus(Long id) {
        if (penjualanRepository.existsByCustomer_Id(id)) {
            throw new RuntimeException("Customer tidak dapat dihapus karena memiliki riwayat transaksi.");
        }
        customerRepository.deleteById(id);
    }
}
