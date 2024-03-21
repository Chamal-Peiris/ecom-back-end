package com.chamal.repository;

import com.chamal.constant.OrderStatus;
import com.chamal.model.Customer;
import com.chamal.model.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByCustomer(Customer customer);

    List<Order> findByOrderStatus(OrderStatus orderStatus);

    List<Order> findByCustomerAndOrderStatus(Customer customer,OrderStatus orderStatus);
}
