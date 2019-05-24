package com.cloud.springcloud.rabbitmq.dao;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 生产者
 */
@Component
public class RabbitMQProduct {


    @Autowired
    public AmqpTemplate amqpTemplate ;

    public  void  poductMessage(String queueName ,String queueMessage ){
        //amqpTemplate.convertAndSend(queueName ,queueMessage);
        amqpTemplate.send("bingo.TopicExchange","",new Message(queueMessage.getBytes(),new MessageProperties() ));
    }


}
