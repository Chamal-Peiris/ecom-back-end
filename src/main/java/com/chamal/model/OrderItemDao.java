package com.chamal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "order_item")
public class OrderItemDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderDao order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductDao product;

    private int quantity;

    private double soldPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderDao getOrder() {
        return order;
    }

    public void setOrder(OrderDao order) {
        this.order = order;
    }

    public ProductDao getProduct() {
        return product;
    }

    public void setProduct(ProductDao product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(double soldPrice) {
        this.soldPrice = soldPrice;
    }
}
