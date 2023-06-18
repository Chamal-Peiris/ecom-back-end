package com.chamal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class CustomerDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fullName;

    private String email;

    private String mobile;

    private String address;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserDao userDao;

    @OneToMany(mappedBy = "customer")
    private Set<OrderDao> orderDaos=new HashSet<>();
}
