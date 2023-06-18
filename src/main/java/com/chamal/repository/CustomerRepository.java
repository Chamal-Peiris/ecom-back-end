package com.chamal.repository;

import com.chamal.model.CustomerDao;
import com.chamal.model.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerDao,Long> {
    CustomerDao findByUserDao(UserDao userDao);
}
