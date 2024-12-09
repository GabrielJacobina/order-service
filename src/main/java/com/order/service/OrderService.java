package com.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.order.requests.OrderRequest;
import com.order.requests.OrderResponse;
import com.order.requests.PaymentResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    void receiveOrder(OrderRequest order) throws JsonProcessingException;

    List<OrderResponse> listOrder();

    void receivePayment(PaymentResponse payment) throws JsonProcessingException;

    OrderResponse gerOrderById(UUID id);
}
