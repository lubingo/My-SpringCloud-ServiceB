package com.cloud.springcloud.rabbitmq.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.springcloud.core.CoreResponse;
import com.cloud.springcloud.rabbitmq.service.MQservice;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(path = "/mq")
public class MQController {
    @Autowired
    private MQservice mQservice ;

    //生产消息
    @RequestMapping(path = "/sendMQMessage" ,produces = "application/plain;charset=utf-8" ,method = RequestMethod.POST)
    public String send(HttpServletRequest request , HttpServletResponse  response ,
                           @RequestParam(required = true ,defaultValue = "")String queueName ,
                           @RequestParam(defaultValue = "",required = true) String message) throws ExecutionException, InterruptedException {
        mQservice.sendMQMessage(queueName,message);
        JSONObject  jsonObject=  new  JSONObject() ;
        jsonObject.put("queueName" , queueName) ;
        jsonObject.put("message" , message) ;
        return jsonObject.toJSONString() ;
    }


    //消费消息
    @RequestMapping(path = "/getMQMessage" ,produces = "application/json;charset=utf-8" ,method = RequestMethod.POST)
    public JSONObject get(HttpServletRequest  request) throws ExecutionException, InterruptedException {
        String  queueName  =  request.getParameter("queueName") ;
        CoreResponse coreResponse = mQservice.receiveMessage(queueName)  ;
        Message message = null;
        JSONObject  jsonObject = new JSONObject() ;
        jsonObject.put("queueName",queueName);
        if(coreResponse.getCode() ==0 ){
             message = (Message) coreResponse.getT();
            jsonObject.put("message" , message.toString());
        }else{
            jsonObject.put("message" ,"");
        }
        return  jsonObject;
    }

}
