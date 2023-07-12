package com.chamal.dto;

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
    private String shippingAddress;
    private double orderTotal;
    private boolean shipped;
    private CustomerDto customerDto;

    public OrderDto(Order orderDao, CustomerDto customerDto) {
        this.id = orderDao.getId();
        this.orderPlacedDate = orderDao.getOrderPlacedDate();
        this.shippingAddress = orderDao.getShippingAddress();
        this.orderTotal = orderDao.getOrderTotal();
        this.shipped = orderDao.isShipped();
        this.customerDto = customerDto;
    }
}
