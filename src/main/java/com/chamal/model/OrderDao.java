package com.chamal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class OrderDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_placed_date")
    private Date orderPlacedDate;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "order_total")
    private double orderTotal;

    private boolean shipped;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerDao customer;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private Set<OrderItemDao> orderItemDaos=new HashSet<>();
}
