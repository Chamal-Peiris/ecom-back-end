package com.chamal.dto;

import com.chamal.constant.OrderStatus;
import com.chamal.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {

    private Long id;
    private Date orderPlacedDate;
    private String address;
    private double orderTotal;
    private boolean shipped;
    private String fullName;
    private String email;
    private CustomerDto customerDto;
    private OrderStatus orderStatus;

    public OrderDto(Order orderDao, CustomerDto customerDto) {
        this.id = orderDao.getId();
        this.orderPlacedDate = orderDao.getOrderPlacedDate();
        this.address = orderDao.getShippingAddress();
        this.orderTotal = orderDao.getOrderTotal();
        this.shipped = orderDao.isShipped();
        this.customerDto = customerDto;
        this.orderStatus=orderDao.getOrderStatus();
        this.fullName=orderDao.getFullName();
        this.email=orderDao.getEmail();
    }
}
