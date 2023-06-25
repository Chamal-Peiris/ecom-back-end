package com.chamal.service;

import com.chamal.dto.ProductDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductService {
     ProductDto save(ProductDto productDto);
     ProductDto getProduct(Long productId);
     List<ProductDto> getProducts();

     ProductDto updateStockAmount(Long productId,Long stockAmount);
}
