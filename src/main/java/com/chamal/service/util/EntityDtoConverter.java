package com.chamal.service.util;

import com.chamal.dto.*;
import com.chamal.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class EntityDtoConverter {

    private ModelMapper mapper;

    public EntityDtoConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public User getUserDao(UserDto userDto){
        return mapper.map(userDto, User.class);
    }
    public UserDto getUserDto(User userDao){
        return mapper.map(userDao, UserDto.class);
    }

    public Customer getCustomerDao(CustomerDto customerDto){
        return mapper.map(customerDto, Customer.class);
    }
    public CustomerDto getCustomerDto(Customer customerDao){
        return mapper.map(customerDao, CustomerDto.class);
    }

    public ProductDto getProductDto(Product productDao){
        return mapper.map(productDao, ProductDto.class);
    }

    public Product getProductDao(ProductDto productDto) {
        return mapper.map(productDto, Product.class);
    }
    public CustomerProduct getCustomerProductDao(CustomerProductDto customerProductDto) {
        return mapper.map(customerProductDto, CustomerProduct.class);
    }
    public CustomerProductDto getCustomerProductDto(CustomerProduct customerProductDao) {
        return mapper.map(customerProductDao, CustomerProductDto.class);
    }

    public Set<OrderItemDto> getOrderItemnDtoList(Set<OrderItem> orderItems){
        Set<OrderItemDto> orderItemDtoList = new HashSet<>();

        for(OrderItem orderItem:orderItems){
            orderItemDtoList.add(mapper.map(orderItem, OrderItemDto.class));
        }
        return orderItemDtoList;
    }
    public OrderDto getOrderDto(Order order) {
        return mapper.map(order, OrderDto.class);
    }
}
