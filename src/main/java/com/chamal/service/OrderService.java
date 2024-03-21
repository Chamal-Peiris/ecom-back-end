package com.chamal.service;

import com.chamal.constant.OrderStatus;
import com.chamal.dto.CustomOrderDto;
import com.chamal.dto.OrderDto;
import com.chamal.model.Order;

import java.util.List;

public interface OrderService {

    OrderDto placeOrder(OrderDto orderDto,Long customerId);

    List<CustomOrderDto> getCustomerOrders(Long customerId, OrderStatus orderStatus);

    OrderDto updateOrderStatus(Long orderId,OrderStatus orderStatus);
}
