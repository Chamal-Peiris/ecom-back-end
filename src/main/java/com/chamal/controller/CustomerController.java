package com.chamal.controller;

import com.chamal.dto.CustomerDto;
import com.chamal.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/save")
    public ResponseEntity saveCustomer(@RequestBody CustomerDto customerDto){
        return ResponseEntity.ok(customerService.save(customerDto));
    }

    @PostMapping("/update")
    public ResponseEntity updateCustomer(CustomerDto customerDto){
        return ResponseEntity.ok(customerService.update(customerDto));
    }
    @GetMapping("/get-customer/{customerId}")
    public ResponseEntity getCustomer(@PathVariable String customerId){
        return ResponseEntity.ok(customerService.getCustomer(Long.parseLong(customerId)));
    }

    @GetMapping()
    public ResponseEntity getCustomers(){
        return ResponseEntity.ok(customerService.getCustomers());
    }
    @DeleteMapping("/delete-customer/{customerId}")
    public ResponseEntity deleteCustomer(@PathVariable String customerId){
        customerService.deleteCustomer(Long.parseLong(customerId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity me(){
        return ResponseEntity.ok(customerService.me());
    }
}
