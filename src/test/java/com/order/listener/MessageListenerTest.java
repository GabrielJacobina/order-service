package com.order.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.order.requests.OrderRequest;
import com.order.requests.PaymentResponse;
import com.order.service.OrderService;
import com.order.util.OrderCreator;
import com.order.util.PaymentCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageListenerTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private MessageListener messageListener;

    @Test
    void receiveMessageOrder_WhenValidOrder_ShouldCallReceiveOrder() throws JsonProcessingException {
        OrderRequest orderRequest = OrderCreator.createOrderRequest();
        ObjectWriter mockWriter = mock(ObjectWriter.class);

        BDDMockito.given(objectMapper.writerWithView(any())).willReturn(mockWriter);

        messageListener.receiveMessageOrder(orderRequest);

        verify(objectMapper, times(1)).writerWithView(OrderRequest.class);
        verify(orderService, times(1)).receiveOrder(orderRequest);
    }

    @Test
    void receiveMessagePayment_WhenValidPayment_ShouldCallReceivePayment() throws JsonProcessingException {
        PaymentResponse paymentResponse = PaymentCreator.createPaymentResponse();
        ObjectWriter mockWriter = mock(ObjectWriter.class);

        BDDMockito.given(objectMapper.writerWithView(any())).willReturn(mockWriter);

        messageListener.receiveMessagePayment(paymentResponse);

        verify(objectMapper, times(1)).writerWithView(PaymentResponse.class);
        verify(orderService, times(1)).receivePayment(paymentResponse);
    }

}
