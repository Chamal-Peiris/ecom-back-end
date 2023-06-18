package com.chamal.repository;

import com.chamal.model.OrderItemDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemDao,Long> {
}
