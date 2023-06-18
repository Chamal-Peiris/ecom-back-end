package com.chamal.service.impl;

import com.chamal.dto.CustomerProductDto;
import com.chamal.dto.OrderDto;
import com.chamal.model.CustomerDao;
import com.chamal.model.CustomerProductDao;
import com.chamal.model.OrderDao;
import com.chamal.model.OrderItemDao;
import com.chamal.repository.OrderRepository;
import com.chamal.service.CustomerProductService;
import com.chamal.service.CustomerService;
import com.chamal.service.OrderService;
import com.chamal.service.exception.NotFoundException;
import com.chamal.service.util.EntityDtoConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.management.remote.JMXPrincipal;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    private CustomerService customerService;
    private EntityDtoConverter mapper;
    private OrderRepository orderRepository;
    private CustomerProductService customerProductService;

    public OrderServiceImpl(CustomerService customerService, EntityDtoConverter mapper, OrderRepository orderRepository, CustomerProductService customerProductService) {
        this.customerService = customerService;
        this.mapper = mapper;
        this.orderRepository = orderRepository;
        this.customerProductService = customerProductService;
    }

    @Override
    public OrderDto placeOrder(OrderDto orderDto, Long customerId) {
        CustomerDao customerDao = mapper.getCustomerDao(customerService.getCustomer(customerId));
        OrderDao orderDao = new OrderDao();

        orderDao.setOrderPlacedDate(new Date());
        orderDao.setCustomer(customerDao);
        orderDao.setShippingAddress(orderDto.getShippingAddress());
        orderDao.setShipped(false);

        List<CustomerProductDao> cpDaoList = customerProductService.getCustomerProductDaos(customerId);

        Set<OrderItemDao> orderItemSet = new HashSet<>();
        double totalPrice = 0;
        for (CustomerProductDao cp : cpDaoList) {
            OrderItemDao orderItemDao = new OrderItemDao();
            orderItemDao.setProduct(cp.getProductDao());
            orderItemDao.setQuantity(cp.getQuantity());
            orderItemDao.setSoldPrice(cp.getPrice());
            orderItemDao.setOrder(orderDao);
            orderItemSet.add(orderItemDao);
            totalPrice += (cp.getPrice() * cp.getQuantity());
        }

        orderDao.setOrderItemDaos(orderItemSet);
        orderDao.setOrderTotal(totalPrice);
        OrderDao savedOrder = orderRepository.save(orderDao);

        customerProductService.deleteCustomerCart(customerId);

        return new OrderDto(savedOrder, mapper.getCustomerDto(savedOrder.getCustomer()));
    }
}
