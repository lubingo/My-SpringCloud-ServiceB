package com.cloud.springcloud.login.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.springcloud.core.CoreResponse;
import com.cloud.springcloud.login.service.LoginService;
import com.bingo.bingo.mail.MailUtil;
import com.bingo.bingo.redis.util.RedisUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(path = "/login")
public class LoginController {

    Logger  logger = Logger.getLogger(this.getClass());

    @Autowired
    private LoginService loginService ;
    @Autowired
    private RedisUtils redisUtils ;
    @RequestMapping(path = "/loginSystem" , produces = "application/json;charset=utf-8" ,method =  RequestMethod.POST)
    public JSONObject loginSystem(HttpServletRequest request , @RequestParam(name =  "email"  ,defaultValue = "15810172080@139.com") String  email ,@RequestParam(name ="strPassword") String strPassword){


        JSONObject jsonObject = new JSONObject() ;
        CoreResponse coreResponse = loginService.login(email,strPassword);
        jsonObject = (JSONObject) JSONObject.toJSON(coreResponse);


        try {
            MailUtil.sendHtmlMail(email,"安全登录",coreResponse.getMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return jsonObject ;
    }

}
