package com.chamal.service.impl;

import com.chamal.dto.OrderDto;
import com.chamal.model.Customer;
import com.chamal.model.CustomerProduct;
import com.chamal.model.Order;
import com.chamal.model.OrderItem;
import com.chamal.repository.OrderRepository;
import com.chamal.service.CustomerProductService;
import com.chamal.service.CustomerService;
import com.chamal.service.OrderService;
import com.chamal.service.util.EntityDtoConverter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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
        Customer customerDao = mapper.getCustomerDao(customerService.getCustomer(customerId));
        Order orderDao = new Order();

        orderDao.setOrderPlacedDate(new Date());
        orderDao.setCustomer(customerDao);
        orderDao.setShippingAddress(orderDto.getShippingAddress());
        orderDao.setShipped(false);

        List<CustomerProduct> cpDaoList = customerProductService.getCustomerProductDaos(customerId);

        Set<OrderItem> orderItemSet = new HashSet<>();
        double totalPrice = 0;
        for (CustomerProduct cp : cpDaoList) {
            OrderItem orderItemDao = new OrderItem();
            orderItemDao.setProduct(cp.getProductDao());
            orderItemDao.setQuantity(cp.getQuantity());
            orderItemDao.setSoldPrice(cp.getPrice());
            orderItemDao.setOrder(orderDao);
            orderItemSet.add(orderItemDao);
            totalPrice += (cp.getPrice() * cp.getQuantity());
        }

        orderDao.setOrderItemDaos(orderItemSet);
        orderDao.setOrderTotal(totalPrice);
        Order savedOrder = orderRepository.save(orderDao);

        customerProductService.deleteCustomerCart(customerId);

        return new OrderDto(savedOrder, mapper.getCustomerDto(savedOrder.getCustomer()));
    }
}
