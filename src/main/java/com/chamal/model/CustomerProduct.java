package com.chamal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_product")
public class CustomerProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customerDao;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productDao;

    @Column(name = "date_added")
    private Date dateAdded;

    private int quantity;

    private double price;

}
