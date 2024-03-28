package com.chamal.service;

import com.chamal.dto.CustomCustomerRegisterDto;
import com.chamal.dto.CustomerDto;
import com.chamal.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerService {
    CustomerDto getCustomer(Long customerId);
    List<CustomerDto> getCustomers();
    CustomerDto save(CustomerDto customerDto);

    CustomerDto saveCustomerForRegistration(CustomerDto customerDto,User user);

    CustomerDto update(CustomerDto customerDto);
    void deleteCustomer(Long customerId);
    CustomCustomerRegisterDto me();


}
