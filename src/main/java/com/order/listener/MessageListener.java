package com.order.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.requests.OrderRequest;
import com.order.requests.PaymentResponse;
import com.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Component
@Validated
public class MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    private final ObjectMapper objectMapper;

    private final OrderService orderService;

    @RabbitListener(queues = "${queue.order.name}", errorHandler = "validationErrorHandler")
    public void receiveMessageOrder(@Valid OrderRequest order) throws JsonProcessingException {
        logger.info("Received order of product A: {}", objectMapper.writerWithView(OrderRequest.class).writeValueAsString(order));
        orderService.receiveOrder(order);
    }

    @RabbitListener(queues = "${queue.payment.name}", errorHandler = "validationErrorHandler")
    public void receiveMessagePayment(@Valid PaymentResponse payment) throws JsonProcessingException {
        logger.info("Received order of payment: {}", objectMapper.writerWithView(PaymentResponse.class).writeValueAsString(payment));
        orderService.receivePayment(payment);
    }
}
