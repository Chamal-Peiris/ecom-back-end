package com.chamal.dto;

import com.chamal.constant.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomOrderDto implements Serializable {

    private Long id;
    private String deliveryAddress;
    private Date orderPlacedDate;
    private OrderStatus orderStatus;
    private Double orderTotal;
    private Boolean shipped;
    private Long customerId;
    private Set<OrderItemDto> orderItems;
}
