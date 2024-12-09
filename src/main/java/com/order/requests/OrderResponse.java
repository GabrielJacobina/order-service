package com.order.requests;

import com.order.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderResponse(UUID id, List<ProductDTO> products, BigDecimal totalPrice, String time) {
}
