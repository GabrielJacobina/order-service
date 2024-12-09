package com.order.exception;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

@Component("validationErrorHandler")
public class ValidationErrorHandler implements RabbitListenerErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ValidationErrorHandler.class);

    @Override
    public Object handleError(Message message, org.springframework.messaging.Message<?> message1, ListenerExecutionFailedException e) throws Exception {
        return null;
    }

    @Override
    public Object handleError(Message amqpMessage, Channel channel, org.springframework.messaging.Message<?> message, ListenerExecutionFailedException exception) throws Exception {
        try {
            logger.error("Error processing message: {}", exception.getMessage());

            boolean requeue = false;
            channel.basicNack(amqpMessage.getMessageProperties().getDeliveryTag(), false, requeue);
        } catch (Exception e) {
            logger.error("Error sending NACK: {}", e.getMessage());
        }
        return RabbitListenerErrorHandler.super.handleError(amqpMessage, channel, message, exception);
    }
}
