package com.chamal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {
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

    private String description;

    @Column(name="image_string")
    private String imageBase64String;
}
