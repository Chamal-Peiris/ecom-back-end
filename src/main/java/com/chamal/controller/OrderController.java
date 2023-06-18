package com.chamal.controller;

import com.chamal.dto.OrderDto;
import com.chamal.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout/{customerId}")
    public ResponseEntity placeOrder(@RequestBody OrderDto orderDto, @PathVariable String customerId){
        return ResponseEntity.ok(orderService.placeOrder(orderDto,Long.parseLong(customerId)));
    }
}
