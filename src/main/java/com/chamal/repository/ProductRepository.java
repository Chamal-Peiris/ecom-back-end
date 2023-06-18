package com.chamal.repository;

import com.chamal.model.ProductDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductDao,Long> {
}
