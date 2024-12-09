package com.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${queue.order.name}")
    private String queueOrder;

    @Value("${queue.order_payment.name}")
    private String queueOrderPaymentName;

    @Value("${exchange.order_payment.name}")
    private String exchangeOrderPaymentName;

    @Value("${queue.payment.name}")
    private String queuePayment;

    @Bean
    public Queue queueOrderPaymentName() {
        return new Queue(queueOrderPaymentName, true);
    }

    @Bean
    public Queue queueOrder() {
        return new Queue(queueOrder, true);
    }

    @Bean
    public Queue queuePayment() {
        return new Queue(queuePayment, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeOrderPaymentName);
    }

    @Bean
    public Binding binding(@Qualifier("queueOrderPaymentName") Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("");
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
