package com.order.util;

import com.order.entity.Order;
import com.order.requests.OrderRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderCreator {

    public static Order createPaidOrder() {
        return Order.builder()
                .id(UUID.randomUUID())
                .products(List.of(ProductDTOCreator.createProductDTO()))
                .totalPrice(new BigDecimal(9))
                .time(LocalDateTime.now())
                .idUser(4L)
                .status("paid")
                .build();
    }

    public static Order createPendingOrder() {
        return Order.builder()
                .products(List.of(ProductDTOCreator.createProductDTO()))
                .totalPrice(new BigDecimal(9))
                .time(LocalDateTime.now())
                .idUser(4L)
                .status("pending")
                .build();
    }

    public static OrderRequest createOrderRequest() {
        return new OrderRequest(List.of(ProductDTOCreator.createProductDTO()), 4L);
    }
}
