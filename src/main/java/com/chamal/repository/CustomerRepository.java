package com.chamal.repository;

import com.chamal.model.Customer;
import com.chamal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findByUserDao(User userDao);
}
