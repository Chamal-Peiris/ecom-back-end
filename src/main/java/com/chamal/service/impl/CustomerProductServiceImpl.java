package com.chamal.service.impl;

import com.chamal.dto.CustomerProductDto;
import com.chamal.dto.ProductDto;
import com.chamal.model.Customer;
import com.chamal.model.CustomerProduct;
import com.chamal.model.Product;
import com.chamal.repository.CustomerProductRepository;
import com.chamal.repository.ProductRepository;
import com.chamal.service.CustomerProductService;
import com.chamal.service.CustomerService;
import com.chamal.service.ProductService;
import com.chamal.service.exception.GenericEcomException;
import com.chamal.service.exception.NotFoundException;
import com.chamal.service.util.EntityDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerProductServiceImpl implements CustomerProductService {

    private CustomerService customerService;
    private ProductService productService;
    private CustomerProductRepository customerProductRepository;
    private EntityDtoConverter mapper;

    @Autowired
    private ProductRepository productRepository;

    public CustomerProductServiceImpl(CustomerService customerService, ProductService productService, CustomerProductRepository customerProductRepository, EntityDtoConverter mapper) {
        this.customerService = customerService;
        this.productService = productService;
        this.customerProductRepository = customerProductRepository;
        this.mapper = mapper;
    }

    @Override
    public CustomerProductDto saveCart(CustomerProductDto customerProductDto) {
        Customer customerDao = mapper.getCustomerDao(customerService.getCustomer(customerProductDto.getCustomerId()));
        Product productDao = mapper.getProductDao(productService.getProduct(customerProductDto.getProductId()));

        CustomerProductDto customerProductDto1 = null;
        if (productDao.getAvailableQuantity() < customerProductDto.getQuantity())
            throw new GenericEcomException("Requested stock amount is not available.");

        CustomerProduct customerProductDao = new CustomerProduct();
        customerProductDao.setProductDao(productDao);
        customerProductDao.setCustomerDao(customerDao);
        customerProductDao.setQuantity(customerProductDto.getQuantity());
        customerProductDao.setDateAdded(new Date());
        customerProductDao.setPrice(customerProductDto.getPrice());

        CustomerProduct availableCpDao = customerProductRepository.findByCustomerDaoAndProductDao(customerDao, productDao);

        if (availableCpDao != null) {
            //Update cart
            availableCpDao.setQuantity(customerProductDto.getQuantity());
            customerProductDto1 = mapper.getCustomerProductDto(customerProductRepository.save(availableCpDao));
        } else {
            //Save cart
            customerProductDto1 = mapper.getCustomerProductDto(customerProductRepository.save(customerProductDao));
        }

        //update the quantity
        productDao.setAvailableQuantity(productDao.getAvailableQuantity()- customerProductDto.getQuantity());
        productService.save(mapper.getProductDto(productDao));
        return customerProductDto1;

    }

    @Override
    public List<CustomerProductDto> getCustomerCart(Long customerId) {
        List<CustomerProduct> customerProductDaoList = customerProductRepository.findByCustomerDao(mapper.getCustomerDao(customerService.getCustomer(customerId)));

        if(customerProductDaoList.isEmpty()) throw new NotFoundException("No cart items for this customer");
        return customerProductDaoList.stream().map(cp->mapper.getCustomerProductDto(cp)).collect(Collectors.toList());
    }

    @Override
    public void removeProduct(Long customerId, Long productId) {
        CustomerProduct cpDao = customerProductRepository.findByCustomerDaoAndProductDao(mapper.getCustomerDao(customerService.getCustomer(customerId)), mapper.getProductDao(productService.getProduct(productId)));
        if(cpDao==null) throw new NotFoundException("No cart avaialble, either the given customer id or product id is wrong");
        int quantityToBeAddedToStock = cpDao.getQuantity();
        Product productDao = cpDao.getProductDao();
        productDao.setAvailableQuantity(productDao.getAvailableQuantity()+quantityToBeAddedToStock);
        productRepository.save(productDao);
        customerProductRepository.delete(cpDao);
    }

    @Override
    public List<CustomerProduct> getCustomerProductDaos(Long customerId) {
        List<CustomerProduct> customerProductDaoList = customerProductRepository.findByCustomerDao(mapper.getCustomerDao(customerService.getCustomer(customerId)));

        if(customerProductDaoList.isEmpty()) throw new NotFoundException("No cart items for this customer");
        return customerProductDaoList;
    }

    @Override
    public void deleteCustomerCart(Long customerId) {

        Customer customerDao = mapper.getCustomerDao(customerService.getCustomer(customerId));

        if (customerDao == null) throw new NotFoundException("No customer found with the given id");

        List<CustomerProduct> customerProductDaoList = customerProductRepository.findByCustomerDao(customerDao);

        if (customerProductDaoList.isEmpty()) throw new NotFoundException("No cart items for this customer");

        List<Product> productList = new ArrayList<>();
        for (CustomerProduct customerProduct : customerProductDaoList) {
            int quantityToBeAddedToStock = customerProduct.getQuantity();
            Product productDao = customerProduct.getProductDao();
            productDao.setAvailableQuantity(productDao.getAvailableQuantity() + quantityToBeAddedToStock);
            productList.add(productDao);
        }
        productRepository.saveAll(productList);
        customerProductRepository.deleteAll(customerProductDaoList);
    }

    @Override
    public CustomerProductDto alterCartQuantity(Long cartId, String alter) throws IllegalAccessException {
        CustomerProduct customerProduct = customerProductRepository.findById(cartId).get();

        if (customerProduct == null) {
            throw new NotFoundException(("No cart found for the given ID"));
        }

        //Validate the available stock amount for the given product before increase
        ProductDto product = productService.getProduct(customerProduct.getProductDao().getId());

        int currentQuantity = customerProduct.getQuantity();

        //Increase
        if (alter.equals("UP")) {
            currentQuantity++;
            if (product.getAvailableQuantity() < currentQuantity) {
                throw new IllegalAccessException("Sorry Insufficent stock amount");
            }
            customerProduct.setQuantity(currentQuantity);
        }
        if(alter.equals("DOWN")){
            currentQuantity--;
            customerProduct.setQuantity(currentQuantity);
        }

        return mapper.getCustomerProductDto(customerProductRepository.save(customerProduct));

    }
}
