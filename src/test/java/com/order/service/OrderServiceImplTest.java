package com.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.order.entity.Order;
import com.order.exception.CustomException;
import com.order.repository.OrderRepository;
import com.order.requests.OrderRequest;
import com.order.requests.OrderResponse;
import com.order.requests.PaymentRequest;
import com.order.requests.PaymentResponse;
import com.order.util.OrderCreator;
import com.order.util.PaymentCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Value("${exchange.order_payment.name}")
    private String exchangeOrderPaymentName;

    @Test
    void receiveOrder_WhenOrderIsValid_ShouldSaveOrderAndProcessPayment() throws JsonProcessingException {
        OrderRequest orderRequest = OrderCreator.createOrderRequest();
        Order savedOrder = new Order(orderRequest);
        ObjectWriter mockWriter = mock(ObjectWriter.class);

        BDDMockito.given(objectMapper.writerWithView(any())).willReturn(mockWriter);
        BDDMockito.given(orderRepository.save(any(Order.class))).willReturn(savedOrder);

        orderService.receiveOrder(orderRequest);

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(rabbitTemplate, times(1)).convertAndSend(eq(exchangeOrderPaymentName), any(PaymentRequest.class));
        verify(objectMapper, times(1)).writerWithView(Order.class);
        verify(mockWriter, times(1)).writeValueAsString(savedOrder);
    }

    @Test
    void listOrder_WhenOrdersExist_ShouldReturnOrderList() {
        List<Order> orders = List.of(OrderCreator.createPaidOrder());

        BDDMockito.given(orderRepository.findAll()).willReturn(orders);

        List<OrderResponse> responses = orderService.listOrder();

        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        assertEquals(orders.size(), responses.size());
        assertEquals(orders.get(0).getIdUser(), responses.get(0).idUser());
    }

    @Test
    void listOrder_WhenNoOrdersExist_ShouldThrowCustomException() {
        BDDMockito.given(orderRepository.findAll()).willReturn(List.of());

        CustomException exception = assertThrows(CustomException.class, orderService::listOrder);

        assertEquals("No orders found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void receivePayment_WhenOrderExists_ShouldUpdateOrderStatus() throws JsonProcessingException {
        PaymentResponse paymentResponse = PaymentCreator.createPaymentResponse();
        Order existingOrder = OrderCreator.createPaidOrder();
        ObjectWriter mockWriter = mock(ObjectWriter.class);

        BDDMockito.given(objectMapper.writerWithView(any())).willReturn(mockWriter);
        BDDMockito.given(orderRepository.findById(paymentResponse.idOrder())).willReturn(Optional.of(existingOrder));
        BDDMockito.given(orderRepository.save(any(Order.class))).willReturn(existingOrder);

        orderService.receivePayment(paymentResponse);

        BDDMockito.then(orderRepository).should().findById(paymentResponse.idOrder());
        BDDMockito.then(orderRepository).should().save(existingOrder);
    }

    @Test
    void receivePayment_WhenOrderDoesNotExist_ShouldDoNothing() {
        PaymentResponse paymentResponse = PaymentCreator.createPaymentResponse();

        BDDMockito.given(orderRepository.findById(paymentResponse.idOrder())).willReturn(Optional.empty());

        assertDoesNotThrow(() -> orderService.receivePayment(paymentResponse));

        verify(orderRepository, times(1)).findById(paymentResponse.idOrder());
        verify(orderRepository, never()).save(any(Order.class));
    }


}
