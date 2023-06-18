package com.chamal.repository;

import com.chamal.model.OrderDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderDao,Long> {
}
