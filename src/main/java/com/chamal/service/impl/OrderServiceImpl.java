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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
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
        orderDao.setShippingAddress(orderDto.getAddress());
        orderDao.setEmail(orderDto.getEmail());
        orderDao.setFullName(orderDto.getFullName());
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


        //Trigger an email to customer after placing the order
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("customerName",savedOrder.getCustomer().getFullName());
        requestMap.put("orderTotal",savedOrder.getOrderTotal());
        requestMap.put("orderStatus",savedOrder.getOrderStatus());
        requestMap.put("email",savedOrder.getEmail());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(savedOrder.getOrderPlacedDate());

        requestMap.put("orderPlacedDate",formattedDate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Auth","qazwsxedc");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestMap, headers);
        try{
            URI uri = new URI("https://prod-33.southeastasia.logic.azure.com:443/workflows/57de1a2a49e9466ab8a540fd33fbb3e0/triggers/manual/paths/invoke");

            UriComponentsBuilder builder = UriComponentsBuilder.fromUri(uri)
                    .queryParam("api-version","2016-06-01")
                    .queryParam("sp","/triggers/manual/run")
                    .queryParam("sv","1.0")
                    .queryParam("sig","RoDgAhHHketwPVB5TpbtK4PXWjZqWPCQkkNTsk1UZls");

            RestTemplate restTemplate=new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.POST,entity, String.class);
        } catch (Exception e){
            logger.error("#### Error caused in mail server :{}####",e.getMessage());
        }

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
                customOrderDto.setCustomerName(order.getCustomer().getFullName());
                customOrderDtoList.add(customOrderDto);
            }
        }
        //Sort list in desc only for customers
        if(customerId!=null){
          customOrderDtoList =  customOrderDtoList.stream()
                    .sorted(Comparator.comparingLong(CustomOrderDto::getId).reversed())
                    .collect(Collectors.toList());
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
