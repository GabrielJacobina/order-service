package com.order.entity;

import com.order.dto.OrderDTO;
import com.order.dto.ProductDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
@Document
public class Order {

    @Id
    private UUID id;
    private List<ProductDTO> products;
    private BigDecimal totalPrice;
    private LocalDateTime time;

    public Order() {
    }

    public Order(UUID id, List<ProductDTO> products, BigDecimal totalPrice, LocalDateTime time) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
        this.time = time;
    }

    public Order(OrderDTO dto) {
        this.id = UUID.randomUUID();
        this.products = dto.products();
        calculateTotalPrice(dto.products());
        this.time = dto.time();
    }

    private void calculateTotalPrice(List<ProductDTO> products) {
        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);
        products.forEach(product -> totalPrice.set((product.price().multiply(BigDecimal.valueOf(product.quantity()))).add(totalPrice.get())));
        this.totalPrice = totalPrice.get();
    }
}
