package com.chamal.service;

import com.chamal.dto.CustomerProductDto;
import com.chamal.model.CustomerProduct;

import java.util.List;

public interface CustomerProductService {
    CustomerProductDto saveCart(CustomerProductDto customerProductDto);

    List<CustomerProductDto> getCustomerCart(Long customerId);

    void removeProduct(Long customerId,Long productId);

    List<CustomerProduct> getCustomerProductDaos(Long customerId);

    void deleteCustomerCart(Long customerId);

    CustomerProductDto alterCartQuantity(Long cartId,String alter) throws IllegalAccessException;

}
