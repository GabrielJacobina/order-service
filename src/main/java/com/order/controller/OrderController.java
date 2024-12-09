package com.order.controller;

import com.order.requests.OrderResponse;
import com.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService orderService) {
        this.service = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> listOrder() {
        return ResponseEntity.ok(service.listOrder());
    }
}
