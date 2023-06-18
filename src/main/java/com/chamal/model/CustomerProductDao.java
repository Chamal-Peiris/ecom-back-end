package com.chamal.model;

import com.chamal.dto.CustomerProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_product")
public class CustomerProductDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerDao customerDao;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductDao productDao;

    @Column(name = "date_added")
    private Date dateAdded;

    private int quantity;

    private double price;

}
