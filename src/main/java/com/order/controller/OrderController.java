package com.order.controller;

import com.order.requests.OrderResponse;
import com.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService service;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> listOrder() {
        return ResponseEntity.ok(service.listOrder());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> listOrder(@PathVariable UUID id) {
        return ResponseEntity.ok(service.gerOrderById(id));
    }
}
