package com.chamal.dto;

import com.chamal.model.OrderDao;
import com.chamal.model.UserDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto implements Serializable {
    private Long id;
    private String fullName;
    private String email;
    private String mobile;
    private String address;

}
