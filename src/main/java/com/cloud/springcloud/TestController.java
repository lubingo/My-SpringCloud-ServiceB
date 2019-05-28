package com.cloud.springcloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/function" ,method = {RequestMethod.POST})
    public String  function(@RequestParam int a  , @RequestParam int b ){
        logger.info("请求的参数为a={},b={}",a,b);
        logger.info("本方法返回两个数的积");
        return  "r="+a*b ;
    }
}
