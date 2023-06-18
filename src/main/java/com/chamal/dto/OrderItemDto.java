package com.chamal.dto;

import com.chamal.model.OrderDao;
import com.chamal.model.ProductDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto implements Serializable {

    private Long id;

    private OrderDto order;

    private ProductDto product;

    private int quantity;

    private double soldPrice;
}
