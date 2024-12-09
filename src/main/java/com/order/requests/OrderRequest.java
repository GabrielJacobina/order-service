package com.order.requests;

import com.order.dto.ProductDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(@NotEmpty List<ProductDTO> products, @NotNull Long idUser) {
}
