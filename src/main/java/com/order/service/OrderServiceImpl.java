package com.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.dto.OrderDTO;
import com.order.entity.Order;
import com.order.exception.CustomException;
import com.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final ObjectMapper objectMapper;

    private final OrderRepository orderRepository;

    public OrderServiceImpl(ObjectMapper objectMapper, OrderRepository orderRepository) {
        this.objectMapper = objectMapper;
        this.orderRepository = orderRepository;
    }

    @Override
    public void save(OrderDTO order) throws JsonProcessingException {
        Order orderSaved = orderRepository.save(new Order(order));
        logger.info("Order saved successfully", objectMapper.writerWithView(Order.class).writeValueAsString(orderSaved));
    }

    @Override
    public List<OrderDTO> listOrder() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new CustomException("No orders found", HttpStatus.NOT_FOUND);
        }
        return orders.stream().map(this::toOrderDTO).toList();
    }

    public OrderDTO toOrderDTO(Order order) {
        return new OrderDTO(order.getProducts(), order.getTotalPrice(), order.getTime());
    }
}
