package com.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.dto.OrderDTO;
import com.order.entity.Order;
import com.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OderServiceImpl.class);

    private final ObjectMapper objectMapper;

    private final OrderRepository orderRepository;

    public OderServiceImpl(ObjectMapper objectMapper, OrderRepository orderRepository) {
        this.objectMapper = objectMapper;
        this.orderRepository = orderRepository;
    }

    @Override
    public void save(OrderDTO order) throws JsonProcessingException {
        orderRepository.save(new Order(order));
        logger.info("Order saved successfully {}", objectMapper.writerWithView(Order.class).writeValueAsString(order));
    }
}
