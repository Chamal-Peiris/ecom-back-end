package com.chamal.controller;

import com.chamal.constant.OrderStatus;
import com.chamal.dto.CustomOrderDto;
import com.chamal.dto.OrderDto;
import com.chamal.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout/{customerId}")
    public ResponseEntity placeOrder(@RequestBody OrderDto orderDto, @PathVariable String customerId){
        return ResponseEntity.ok(orderService.placeOrder(orderDto,Long.parseLong(customerId)));
    }

    @GetMapping("/customer-orders")
    public ResponseEntity getCustomerOrders(@RequestParam(required = false) Long customerId,
                                                            @RequestParam (required = false) OrderStatus orderStatus){
        return ResponseEntity.ok(orderService.getCustomerOrders(customerId,orderStatus));
    }

    @PutMapping("/update-order-status")
    public ResponseEntity updateOrderStatus(@RequestParam Long orderId,
                                            @RequestParam  OrderStatus orderStatus){
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId,orderStatus));
    }
}
