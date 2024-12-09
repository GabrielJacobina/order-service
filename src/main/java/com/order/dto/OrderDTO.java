package com.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(List<ProductDTO> products, BigDecimal totalPrice, LocalDateTime time) {
}
