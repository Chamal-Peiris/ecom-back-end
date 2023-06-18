package com.chamal.controller;

import com.chamal.dto.CustomerProductDto;
import com.chamal.service.CustomerProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    private CustomerProductService customerProductService;

    public CartController(CustomerProductService customerProductService) {
        this.customerProductService = customerProductService;
    }

    @PostMapping("/save")
    public ResponseEntity saveCart(@RequestBody CustomerProductDto customerProductDto){
        return ResponseEntity.ok(customerProductService.saveCart(customerProductDto));
    }
}
