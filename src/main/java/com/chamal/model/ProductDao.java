package com.chamal.model;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class ProductDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "available_quantity")
    private long availableQuantity;

    @Column(name = "selling_price")
    private double buyingPrice;

    @Column(name = "buying_price")
    private double sellingPrice;
}
