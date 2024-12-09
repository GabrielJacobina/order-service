package com.order.entity;

import com.order.dto.ProductDTO;
import com.order.requests.OrderRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Document
@CompoundIndex(name = "user_time_unique_idx", def = "{'idUser': 1, 'time': 1}", unique = true)
public class Order {

    @Id
    private UUID id;
    private List<ProductDTO> products;
    private BigDecimal totalPrice;
    private LocalDateTime time;
    private Long idUser;

    public Order() {
    }

    public Order(UUID id, List<ProductDTO> products, BigDecimal totalPrice, LocalDateTime time) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
        this.time = time;
    }

    public Order(OrderRequest dto) {
        this.id = UUID.randomUUID();
        this.products = dto.products();
        calculateTotalPrice(dto.products());
        this.time = LocalDateTime.now();
        this.idUser = dto.idUser();
    }

    private void calculateTotalPrice(List<ProductDTO> products) {
        AtomicReference<BigDecimal> price = new AtomicReference<>(BigDecimal.ZERO);
        products.forEach(product -> price.set((product.price().multiply(BigDecimal.valueOf(product.quantity()))).add(price.get())));
        this.totalPrice = price.get();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}
