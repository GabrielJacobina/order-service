package com.order.util;

import com.order.dto.ProductDTO;

import java.math.BigDecimal;

public class ProductDTOCreator {

    public static ProductDTO createProductDTO() {
        return new ProductDTO(1L, "Pasta", new BigDecimal(4.50), 2L);
    }
}
