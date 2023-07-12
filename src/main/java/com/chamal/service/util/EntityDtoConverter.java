package com.chamal.service.util;

import com.chamal.dto.CustomerDto;
import com.chamal.dto.CustomerProductDto;
import com.chamal.dto.ProductDto;
import com.chamal.dto.UserDto;
import com.chamal.model.Customer;
import com.chamal.model.CustomerProduct;
import com.chamal.model.Product;
import com.chamal.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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
}
