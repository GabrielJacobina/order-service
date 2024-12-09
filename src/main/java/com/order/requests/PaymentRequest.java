package com.order.requests;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PaymentRequest(@NotNull UUID idOrder, @NotNull Long idUser, String status) {
}
