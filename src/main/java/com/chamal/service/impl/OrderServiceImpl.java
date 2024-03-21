package com.chamal.service.impl;

import com.chamal.constant.OrderStatus;
import com.chamal.dto.CustomOrderDto;
import com.chamal.dto.CustomerDto;
import com.chamal.dto.OrderDto;
import com.chamal.model.Customer;
import com.chamal.model.CustomerProduct;
import com.chamal.model.Order;
import com.chamal.model.OrderItem;
import com.chamal.repository.OrderRepository;
import com.chamal.service.CustomerProductService;
import com.chamal.service.CustomerService;
import com.chamal.service.OrderService;
import com.chamal.service.exception.NotFoundException;
import com.chamal.service.util.EntityDtoConverter;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Array;
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
        orderDao.setOrderStatus(OrderStatus.PLACED);

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

    @Override
    public List<CustomOrderDto> getCustomerOrders(Long customerId, OrderStatus orderStatus) {
        List<Order> customerOrders;
        if (customerId == null && orderStatus == null) {
            customerOrders = orderRepository.findAll();
        } else if (customerId != null && orderStatus == null) {

            Customer customer = mapper.getCustomerDao(customerService.getCustomer(customerId));
            if (customer == null) {
                throw new NotFoundException("Customer Not Found");
            }
            customerOrders = orderRepository.findByCustomer(customer);
        } else if (customerId == null && orderStatus != null) {
            customerOrders = orderRepository.findByOrderStatus(orderStatus);
        } else {
            Customer customer = mapper.getCustomerDao(customerService.getCustomer(customerId));
            if (customer == null) {
                throw new NotFoundException("Customer Not Found");
            }
            customerOrders = orderRepository.findByCustomerAndOrderStatus(customer, orderStatus);
        }

        List<CustomOrderDto> customOrderDtoList = new ArrayList<>();

        if (!customerOrders.isEmpty()) {
            for (Order order : customerOrders) {
                CustomOrderDto customOrderDto = new CustomOrderDto();
                customOrderDto.setId(order.getId());
                customOrderDto.setCustomerId(order.getCustomer().getId());
                customOrderDto.setOrderStatus(order.getOrderStatus());
                customOrderDto.setOrderTotal(order.getOrderTotal());
                customOrderDto.setOrderPlacedDate(order.getOrderPlacedDate());
                customOrderDto.setDeliveryAddress(order.getShippingAddress());
                customOrderDto.setOrderItems(mapper.getOrderItemnDtoList(order.getOrderItemDaos()));
                customOrderDtoList.add(customOrderDto);
            }
        }
        return customOrderDtoList;
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId).get();

        if (order == null) {
            throw new NotFoundException("No order found");
        }

        order.setOrderStatus(orderStatus);
        return mapper.getOrderDto(orderRepository.save(order));
    }

}
