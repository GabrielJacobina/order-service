package com.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.entity.Order;
import com.order.exception.CustomException;
import com.order.repository.OrderRepository;
import com.order.requests.OrderRequest;
import com.order.requests.OrderResponse;
import com.order.requests.PaymentRequest;
import com.order.requests.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final ObjectMapper objectMapper;

    private final OrderRepository orderRepository;

    private final RabbitTemplate rabbitTemplate;

    @Value("${exchange.order_payment.name}")
    private String exchangeOrderPaymentName;

    private Order save(Order order) throws JsonProcessingException {
        Order orderSaved = orderRepository.save(order);
        logger.info("Order saved successfully {}", objectMapper.writerWithView(Order.class).writeValueAsString(orderSaved));
        return orderSaved;
    }

    @Override
    public void receiveOrder(OrderRequest order) throws JsonProcessingException {
        Order orderSaved = save(new Order(order));
        processPayment(new PaymentRequest(orderSaved.getId(), orderSaved.getIdUser(), orderSaved.getStatus()));
    }

    @Override
    public List<OrderResponse> listOrder() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new CustomException("No orders found", HttpStatus.NOT_FOUND);
        }
        return orders.stream().map(this::toOrderDTO).toList();
    }

    @Override
    public void receivePayment(PaymentResponse payment) throws JsonProcessingException {
        Optional<Order> orderOptional = orderRepository.findById(payment.idOrder());
        if (orderOptional.isPresent()) {
            orderOptional.get().setStatus(payment.status());
            save(orderOptional.get());
        }
    }

    @Override
    public OrderResponse gerOrderById(UUID id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        return orderOptional.map(this::toOrderDTO).orElseThrow(() -> new CustomException("Order not found", HttpStatus.NOT_FOUND));
    }

    private OrderResponse toOrderDTO(Order order) {
        return new OrderResponse(order.getId(), order.getProducts(), order.getTotalPrice(), order.getIdUser(), order.getTime().toString());
    }

    private void processPayment(PaymentRequest payment) {
        rabbitTemplate.convertAndSend(exchangeOrderPaymentName, payment);
        logger.info("send message to payment {}", payment);
    }
}
