package com.chamal.service.impl;

import com.chamal.dto.CustomerDto;
import com.chamal.model.CustomerDao;
import com.chamal.model.UserDao;
import com.chamal.repository.CustomerRepository;
import com.chamal.service.CustomerService;
import com.chamal.service.JwtUserDetailsService;
import com.chamal.service.exception.DuplicateRecordException;
import com.chamal.service.exception.NotFoundException;
import com.chamal.service.util.EntityDtoConverter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;

    private JwtUserDetailsService jwtUserDetailsService;
    private EntityDtoConverter mapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, JwtUserDetailsService jwtUserDetailsService, EntityDtoConverter mapper) {
        this.customerRepository = customerRepository;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.mapper = mapper;
    }

    @Override
    public CustomerDto getCustomer(Long customerId) {
        Optional<CustomerDao> customerDao = customerRepository.findById(customerId);

        if(!customerDao.isPresent()) throw new NotFoundException("No customer found for the given id");

        return mapper.getCustomerDto(customerDao.get());
    }

    @Override
    public List<CustomerDto> getCustomers() {
        List<CustomerDao> allCustomerList = customerRepository.findAll();
        if(allCustomerList.isEmpty()) throw new NotFoundException("Mo customers available");

      return   allCustomerList.stream().map(customer->mapper.getCustomerDto(customer)).collect(Collectors.toList());

    }

    @Override
    public CustomerDto save(CustomerDto customerDto) {

        CustomerDao savedCustomer = customerRepository.findByUserDao(jwtUserDetailsService.getLoggedUser());

        if(savedCustomer!=null) throw new DuplicateRecordException("A customer record is already available for this user");

        CustomerDao customerDao = new CustomerDao();
        UserDao userDao = jwtUserDetailsService.getLoggedUser();
        customerDao.setUserDao(userDao);
        customerDao.setAddress(customerDto.getAddress());
        customerDao.setFullName(customerDto.getFullName());
        customerDao.setMobile(customerDto.getMobile());
        customerDao.setEmail(customerDto.getEmail());

        return mapper.getCustomerDto(customerRepository.save(customerDao));
    }

    @Override
    public CustomerDto update(CustomerDto customerDto) {
        Optional<CustomerDao> customerDao = customerRepository.findById(customerDto.getId());
        if(!customerDao.isPresent()) throw new NotFoundException("No customer found for the provided id");

        customerDao.get().setFullName(customerDto.getFullName());
        customerDao.get().setAddress(customerDto.getAddress());

        return mapper.getCustomerDto(customerRepository.save(customerDao.get()));

    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }
}
