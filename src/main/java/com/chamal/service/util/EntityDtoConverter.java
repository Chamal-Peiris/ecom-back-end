package com.chamal.service.util;

import com.chamal.dto.CustomerDto;
import com.chamal.dto.CustomerProductDto;
import com.chamal.dto.ProductDto;
import com.chamal.dto.UserDto;
import com.chamal.model.CustomerDao;
import com.chamal.model.CustomerProductDao;
import com.chamal.model.ProductDao;
import com.chamal.model.UserDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityDtoConverter {

    private ModelMapper mapper;

    public EntityDtoConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public UserDao getUserDao(UserDto userDto){
        return mapper.map(userDto, UserDao.class);
    }
    public UserDto getUserDto(UserDao userDao){
        return mapper.map(userDao, UserDto.class);
    }

    public CustomerDao getCustomerDao(CustomerDto customerDto){
        return mapper.map(customerDto, CustomerDao.class);
    }
    public CustomerDto getCustomerDto(CustomerDao customerDao){
        return mapper.map(customerDao, CustomerDto.class);
    }

    public ProductDto getProductDto(ProductDao productDao){
        return mapper.map(productDao, ProductDto.class);
    }

    public ProductDao getProductDao(ProductDto productDto) {
        return mapper.map(productDto, ProductDao.class);
    }
    public CustomerProductDao getCustomerProductDao(CustomerProductDto customerProductDto) {
        return mapper.map(customerProductDto, CustomerProductDao.class);
    }
    public CustomerProductDto getCustomerProductDto(CustomerProductDao customerProductDao) {
        return mapper.map(customerProductDao, CustomerProductDto.class);
    }
}
