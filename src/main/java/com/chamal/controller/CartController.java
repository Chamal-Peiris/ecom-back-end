package com.chamal.controller;

import com.chamal.dto.CustomerProductDto;
import com.chamal.service.CustomerProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private CustomerProductService customerProductService;

    public CartController(CustomerProductService customerProductService) {
        this.customerProductService = customerProductService;
    }

    @PostMapping("/save")
    public ResponseEntity saveCart(@RequestBody CustomerProductDto customerProductDto){
        return ResponseEntity.ok(customerProductService.saveCart(customerProductDto));
    }

    @GetMapping("/get-customer-cart")
    public ResponseEntity getCustomerCart(@RequestParam Long customerId){
        return ResponseEntity.ok(customerProductService.getCustomerCart(customerId));
    }

    @DeleteMapping("/remove-product")
    public ResponseEntity deleteCustomerCart(@RequestParam Long customerId,@RequestParam Long productId){
        customerProductService.removeProduct(customerId,productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear-cart")
    public ResponseEntity deleteCustomerCart(@RequestParam Long customerId){
        customerProductService.deleteCustomerCart(customerId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/alter-cart-quantity")
    public ResponseEntity alterCartQuantity(@RequestParam Long cartId,@RequestParam String alter) throws IllegalAccessException {
        return ResponseEntity.ok(customerProductService.alterCartQuantity(cartId,alter));
    }
}
