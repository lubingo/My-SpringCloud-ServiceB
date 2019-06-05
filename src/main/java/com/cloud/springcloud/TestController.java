package com.cloud.springcloud;

import com.cloud.springcloud.redis.util.RedisLock;
import com.cloud.springcloud.redis.util.RedisUtil;
import com.cloud.springcloud.redis.util.RedisUtils;
import com.cloud.springcloud.thread.ExecutorServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);


    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    private ExecutorService executorService = ExecutorServiceUtil.getInstance().getExecutorService();

    @RequestMapping(value = "/function", method = {RequestMethod.POST})
    public String function(int thread, int count) throws ExecutionException, InterruptedException {
        //创建一个key  赋值为 100     库存
        redisUtils.set("kucun", count);
        RedisLock redisLock = new RedisLock(redisTemplate, "kucun");
        // 10000 并发 请求扣减库存
        for (int i = 0; i < thread; i++) {
            int finalI = i;

           if(i > count){
               return "已抢光！" ;
           }
            Future future = executorService.submit(new Callable() {
                @Override
                public Object call() {

                    try {
                        boolean lockde = redisLock.lock();

                        if (lockde) {
                            if (Integer.parseInt(redisUtils.get("kucun").toString()) > 0) {
                                redisUtils.decr("kucun", 1);
                                logger.info("抢购成功！");
                            } else {
                                logger.info("没库存了" + finalI);
                                return "";
                            }
                        } else {
                            logger.info("人太拥挤！");
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        redisLock.unlock();
                    }
                    return null;
                }
            });
           Object object = future.get() ;

        }


        return "OK";

    }


}
