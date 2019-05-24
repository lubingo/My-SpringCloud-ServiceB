package com.cloud.springcloud.rabbitmq.dao;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//消费者
@Component
public class RabbitMQConsume {

    @Autowired
    private AmqpTemplate amqpTemplate ;

    public Message receiveMessage(String queueName   ) throws  NullPointerException{
        Message message = amqpTemplate.receive (queueName) ;
        return   message ;
    }

}
