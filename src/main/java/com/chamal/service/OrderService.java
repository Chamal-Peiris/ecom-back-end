package com.chamal.service;

import com.chamal.dto.OrderDto;

public interface OrderService {

    OrderDto placeOrder(OrderDto orderDto,Long customerId);
}
