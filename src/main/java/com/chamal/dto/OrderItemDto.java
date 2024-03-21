package com.chamal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto implements Serializable {

    private Long id;

    private ProductDto product;

    private int quantity;

    private double soldPrice;
}
