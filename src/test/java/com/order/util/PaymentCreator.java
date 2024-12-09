package com.order.util;

import com.order.requests.PaymentRequest;
import com.order.requests.PaymentResponse;

import java.util.UUID;

public class PaymentCreator {

    public static PaymentRequest createPaymentRequest() {
        return new PaymentRequest(UUID.randomUUID(), 4L, "pending");
    }

    public static PaymentResponse createPaymentResponse() {
        return new PaymentResponse(UUID.randomUUID(), 4L, "paid");
    }
}
