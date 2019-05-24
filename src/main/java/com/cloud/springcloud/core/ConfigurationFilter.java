package com.cloud.springcloud.core;


import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import java.io.IOException;

@Configuration
public class ConfigurationFilter  {

    @Bean
    public  RemoteIpFilter  remoteIpFilter(){
        return  new RemoteIpFilter() ;
    }

    /**
     * 自定义的 filter
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new InitMyFilter());//添加过滤器
        registration.addUrlPatterns("/*");//设置过滤路径，/*所有路径
        registration.setName("InitMyFilter");//设置优先级
        registration.setOrder(1);//设置优先级
        return registration;
    }

    private class InitMyFilter  implements Filter {
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }
        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            servletRequest.setCharacterEncoding("UTF-8");
            servletResponse.setCharacterEncoding("UTF-8");
            filterChain.doFilter(servletRequest ,servletResponse );
        }
        @Override
        public void destroy() {
        }
    }

}
