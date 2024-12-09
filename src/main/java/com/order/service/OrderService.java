package com.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.order.dto.OrderDTO;

public interface OrderService {

    void save(OrderDTO order) throws JsonProcessingException;
}
