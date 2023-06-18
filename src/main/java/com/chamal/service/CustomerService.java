package com.chamal.service;

import com.chamal.dto.CustomerDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {
    CustomerDto getCustomer(Long customerId);
    List<CustomerDto> getCustomers();
    CustomerDto save(CustomerDto customerDto);

    CustomerDto update(CustomerDto customerDto);
    void deleteCustomer(Long customerId);
}
