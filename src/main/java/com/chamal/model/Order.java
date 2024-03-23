package com.chamal.model;

import com.chamal.constant.OrderStatus;
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
public class Order {
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

    @Column(name = "full_name")
    private String fullName;


    private String email;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private Set<OrderItem> orderItemDaos=new HashSet<>();
}
