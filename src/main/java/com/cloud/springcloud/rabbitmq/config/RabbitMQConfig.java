package com.cloud.springcloud.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 初始化定义  队列  渠道
 *
 */
@Configuration
public class RabbitMQConfig {


    //直连交换机
    @Bean
    public Queue directQueueFirst(){
        return  new Queue("bingo.DirectQueueFirst");
    }
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("bingo.DirectExchange");
    }
    @Bean
    public Binding bindingDirectExchange(Queue directQueueFirst ,DirectExchange directExchange){
        return  BindingBuilder.bind(directQueueFirst).to(directExchange).with("bindingKeyForDirectExchange");
    }

    //主题交换机  主题式 *（星号）：可以（只能）匹配一个单词   #（井号）：可以匹配多个单词（或者零个）
    @Bean
    public Queue  topicQueueFirst(){
         return  new Queue("bingo.TopicQueueFirst" );
     }
    @Bean
    public TopicExchange topicExchannel(){
        return  new TopicExchange("bingo.TopicExchange");
    }
    @Bean
    public Binding bindingQueueTopicExchange(Queue topicQueueFirst , TopicExchange topicExchannel){
        return BindingBuilder.bind(topicQueueFirst).to(topicExchannel).with("bingo.bindingKeyForTopicExchange");
    }

    //扇型交换机  广播式
    @Bean
    public Queue  fanoutQueueFirst(){
        return  new Queue("bingo.FanoutQueueFirst" );
    }
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("bingo.FanoutExchange");
    }
    @Bean
    public  Binding bindingQueueFanoutExchangeFirst(Queue fanoutQueueFirst ,FanoutExchange fanoutExchange){
        return  BindingBuilder.bind(fanoutQueueFirst).to(fanoutExchange);
    }





}
