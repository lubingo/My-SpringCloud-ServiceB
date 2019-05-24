package com.cloud.springcloud.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;

@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {


    /**
     * 生成key的策略
     * @return
     */
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {

                StringBuilder  sb =  new StringBuilder(o.getClass().getName()) ;
                sb.append(method.getName());
                for (Object obj:objects ) {
                    sb.append(obj);
                }
                return sb.toString();
            }
        };
    }

    /**
     * RedisTemplate配置
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public  RedisTemplate<String ,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String ,Object>  redisTemplate  = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 值采用json序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 设置hash key 和value序列化模式
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return  redisTemplate ;
    }

}
