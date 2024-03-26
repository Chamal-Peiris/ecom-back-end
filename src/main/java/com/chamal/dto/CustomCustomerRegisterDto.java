package com.chamal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomCustomerRegisterDto implements Serializable {
    UserDto user;
    CustomerDto customer;
}
