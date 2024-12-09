package com.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductDTO(@NotNull Long id, @NotEmpty String name, @NotNull BigDecimal price, @NotNull Long quantity) {
}
