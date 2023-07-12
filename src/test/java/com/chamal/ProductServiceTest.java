package com.chamal;

import com.chamal.dto.ProductDto;
import com.chamal.model.Product;
import com.chamal.repository.ProductRepository;
import com.chamal.service.ProductService;
import com.chamal.service.util.EntityDtoConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTest {
    @Autowired
    ProductService productService;
   @Autowired
    ProductRepository productRepository;

   @Autowired
   private EntityDtoConverter mapper;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void TestProductSave(){

        ProductDto productDto = new ProductDto();
        productDto.setProductName("test");
        productDto.setAvailableQuantity(15);

        Product productDao = mapper.getProductDao(productDto);


       // when(productRepository.save(any(ProductDao.class))).thenReturn(productDao);

        Product save1 = productRepository.save(productDao);



        ProductDto save = productService.save(productDto);

        Assert.assertEquals(save1.getProductName(),save.getProductName());

    }
}
