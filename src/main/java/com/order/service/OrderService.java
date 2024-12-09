package com.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.order.requests.OrderRequest;
import com.order.requests.OrderResponse;

import java.util.List;

public interface OrderService {

    void save(OrderRequest order) throws JsonProcessingException;

    List<OrderResponse> listOrder();
}
