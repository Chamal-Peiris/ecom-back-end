package com.chamal.repository;

import com.chamal.model.CustomerDao;
import com.chamal.model.CustomerProductDao;
import com.chamal.model.ProductDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerProductRepository extends JpaRepository<CustomerProductDao,Long> {
    CustomerProductDao findByCustomerDaoAndProductDao(CustomerDao customerDao, ProductDao productDao);

    List<CustomerProductDao> findByCustomerDao(CustomerDao customerDao);
}
