package com.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.order.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    void save(OrderDTO order) throws JsonProcessingException;

    List<OrderDTO> listOrder();
}
