package com.order.controller;

import com.order.exception.CustomException;
import com.order.requests.OrderResponse;
import com.order.service.OrderService;
import com.order.util.OrderCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Test
    void listOrder_ShouldReturnListOfOrders() {
        List<OrderResponse> orderReponse = List.of(OrderCreator.createOrderResponse());
        BDDMockito.given(orderService.listOrder()).willReturn(orderReponse);

        ResponseEntity<List<OrderResponse>> response = orderController.listOrder();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(orderReponse.get(0).idUser(), response.getBody().get(0).idUser());
        verify(orderService, times(1)).listOrder();
    }

    @Test
    void getOrderById_WhenOrderExists_ShouldReturnOrder() {
        OrderResponse orderReponse = OrderCreator.createOrderResponse();
        UUID id = orderReponse.id();
        BDDMockito.given(orderService.gerOrderById(id)).willReturn(orderReponse);

        ResponseEntity<OrderResponse> response = orderController.listOrder(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().id());
        assertEquals(orderReponse.idUser(), response.getBody().idUser());
        verify(orderService, times(1)).gerOrderById(id);
    }

    @Test
    void getOrderById_WhenOrderDoesNotExist_ShouldThrowCustomException() {
        UUID orderId = UUID.randomUUID();
        BDDMockito.given(orderService.gerOrderById(orderId)).willThrow(new CustomException("Order not found", HttpStatus.NOT_FOUND));

        CustomException exception = assertThrows(CustomException.class, () -> orderController.listOrder(orderId));

        assertEquals("Order not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(orderService, times(1)).gerOrderById(orderId);
    }

}
