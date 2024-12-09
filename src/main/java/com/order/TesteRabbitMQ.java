package com.order;

import com.order.dto.OrderDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rabbit")
public class TesteRabbitMQ {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${queue.order.name}")
    private String queueName;

    @PostMapping
    ResponseEntity sendMessage(@RequestBody OrderDTO orderDTO) {
        rabbitTemplate.convertAndSend(queueName, orderDTO);
        return ResponseEntity.ok().body(orderDTO);
    }
}
