package com.chamal.service.impl;

import com.chamal.dto.ProductDto;
import com.chamal.model.Product;
import com.chamal.repository.ProductRepository;
import com.chamal.service.JwtUserDetailsService;
import com.chamal.service.ProductService;
import com.chamal.service.exception.NotFoundException;
import com.chamal.service.util.EntityDtoConverter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private EntityDtoConverter mapper;
    private ProductRepository productRepository;
    private JwtUserDetailsService jwtUserDetailsService;

    public ProductServiceImpl(EntityDtoConverter mapper, ProductRepository productRepository, JwtUserDetailsService jwtUserDetailsService) {
        this.mapper = mapper;
        this.productRepository = productRepository;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        ProductDto savedDto=null;
        if(productDto.getId()==null){
            //Save
          savedDto =  mapper.getProductDto(productRepository.save(mapper.getProductDao(productDto)));
        }else{
            //Update
            Optional<Product> productDao = productRepository.findById(productDto.getId());

            if(!productDao.isPresent()) throw new NotFoundException("No product is found for the given id");
            productDao.get().setProductName(productDto.getProductName());
            productDao.get().setBuyingPrice(productDto.getBuyingPrice());
            productDao.get().setSellingPrice(productDto.getSellingPrice());
            productDao.get().setAvailableQuantity(productDto.getAvailableQuantity());
            productDao.get().setDescription(productDto.getDescription());
            if(!productDto.getImageBase64String().equals("")){
                productDao.get().setImageBase64String(productDto.getImageBase64String());
            }

          savedDto=  mapper.getProductDto(productRepository.save(productDao.get()));

        }
        return savedDto;
    }

    @Override
    public ProductDto getProduct(Long productId) {
        Optional<Product> productDao = productRepository.findById(productId);

        if (!productDao.isPresent()) throw new NotFoundException("No product is found for the given id");

        return mapper.getProductDto(productDao.get());
    }

    @Override
    public List<ProductDto> getProducts() {

        List<Product> allProducts = productRepository.findAll();
        if (allProducts.isEmpty()) throw new NotFoundException("No products found");

        return allProducts.stream().map(productDao -> mapper.getProductDto(productDao)).collect(Collectors.toList());

    }

    @Override
    public ProductDto updateStockAmount(Long productId, Long stockAmount) {
        Optional<Product> productDao = productRepository.findById(productId);

        if(!productDao.isPresent()) throw new NotFoundException("No product is found for the given id");
        productDao.get().setAvailableQuantity(stockAmount);
        return mapper.getProductDto(productRepository.save(productDao.get()));
    }
}
