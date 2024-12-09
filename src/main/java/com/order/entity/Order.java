package com.order.entity;

import com.order.dto.ProductDTO;
import com.order.requests.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document
@CompoundIndex(name = "user_time_unique_idx", def = "{'idUser': 1, 'time': 1}", unique = true)
public class Order {

    @Id
    private UUID id;
    private List<ProductDTO> products;
    private BigDecimal totalPrice;
    private LocalDateTime time;
    private Long idUser;
    private String status;

    public Order(OrderRequest dto) {
        this.id = UUID.randomUUID();
        this.products = dto.products();
        calculateTotalPrice(dto.products());
        this.time = LocalDateTime.now();
        this.idUser = dto.idUser();
        this.status = "pendente";
    }

    private void calculateTotalPrice(List<ProductDTO> products) {
        AtomicReference<BigDecimal> price = new AtomicReference<>(BigDecimal.ZERO);
        products.forEach(product -> price.set((product.price().multiply(BigDecimal.valueOf(product.quantity()))).add(price.get())));
        this.totalPrice = price.get();
    }

}
