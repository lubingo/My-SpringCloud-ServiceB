package com.cloud.springcloud.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Auth lubing
 * RestTemplate Bean
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory){
        RestTemplate restTemplate =  new RestTemplate(clientHttpRequestFactory);
        List<HttpMessageConverter<?>>  list= restTemplate.getMessageConverters();
        list.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(list);
        HttpHeaders headers = new HttpHeaders();
        //定义请求参数类型，这里用json所以是MediaType.APPLICATION_JSON
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate  ;
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(){
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory() ;
        simpleClientHttpRequestFactory.setConnectTimeout(100000);
        simpleClientHttpRequestFactory.setReadTimeout(600000);
        return  simpleClientHttpRequestFactory ;
    }

}
