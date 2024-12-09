package com.order.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PaymentResponse(@NotNull UUID idOrder, @NotNull Long idUser, @NotNull @NotBlank String status) {
}
