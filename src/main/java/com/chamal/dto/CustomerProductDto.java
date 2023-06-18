package com.chamal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProductDto implements Serializable {
    private Long id;
    private Long customerId;
    private Long productId;
    private Date dateAdded;
    private int quantity;
    private Double price;

}
