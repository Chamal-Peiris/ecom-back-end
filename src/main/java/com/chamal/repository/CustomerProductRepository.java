package com.chamal.repository;

import com.chamal.model.Customer;
import com.chamal.model.CustomerProduct;
import com.chamal.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerProductRepository extends JpaRepository<CustomerProduct,Long> {
    CustomerProduct findByCustomerDaoAndProductDao(Customer customerDao, Product productDao);

    List<CustomerProduct> findByCustomerDao(Customer customerDao);
}
