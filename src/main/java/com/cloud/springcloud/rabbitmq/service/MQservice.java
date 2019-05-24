package com.cloud.springcloud.rabbitmq.service;



import com.cloud.springcloud.core.CoreResponse;
import com.cloud.springcloud.core.CoreResponseCode;
import com.cloud.springcloud.rabbitmq.dao.RabbitMQConsume;
import com.cloud.springcloud.rabbitmq.dao.RabbitMQProduct;
import com.cloud.springcloud.thread.ExecutorServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class MQservice {

    //生产者
    @Autowired
    private RabbitMQProduct rabbitMQProduct ;
    //消费者
    @Autowired
    private RabbitMQConsume rabbitMQConsume ;

    private ExecutorService executorService = ExecutorServiceUtil.getInstance().getExecutorService() ;

    private static  final Logger logger  = LoggerFactory.getLogger(MQservice.class);
    public  void  sendMQMessage(String queueName ,String  message) throws ExecutionException, InterruptedException {

        List<Future> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String finalMessage = message +i;
             Future future = executorService.submit(new Callable() {
                @Override
                public Object call()  {
                    logger.info( "生产线程名："+Thread.currentThread().getName());
                    rabbitMQProduct.poductMessage(queueName, finalMessage);
                    return null;
                }
            }) ;
            list.add(future)  ;
        }
    }

    public CoreResponse receiveMessage(String queueName  ) throws ExecutionException, InterruptedException {
       List<Future> list = new ArrayList<>() ;
        for (int i = 0; i <100; i++) {

           Future future = executorService.submit(new Callable() {
                @Override
                public Object call() throws Exception {
                    logger.info( "消费线程名："+Thread.currentThread().getName());
                    Message message  = rabbitMQConsume.receiveMessage(queueName) ;
                    return message;
                }
            });
            list.add(future);
            if(future.isDone()){
                logger.info("返回的消息为："+future.get());
            }
        }
        Message message1 = (Message) list.get(0).get();
        if( message1 == null   ){
            return  new CoreResponse(CoreResponseCode.CODE_ERROR,"查询MQ消息队列无数据");
        }else{
            return  new CoreResponse<Message>(CoreResponseCode.CODE_OK,"查询MQ消息队列成功",null, message1);
        }
    }

}
