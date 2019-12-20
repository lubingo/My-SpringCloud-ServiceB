package com.cloud.springcloud;

import com.cloud.springcloud.ecommerce.order.entity.EcommerceOrder;
import com.cloud.springcloud.ecommerce.order.service.EcommerceOrderService;
import com.cloud.springcloud.redis.util.RedisLock;
import com.cloud.springcloud.redis.util.RedisUtil;
import com.cloud.springcloud.redis.util.RedisUtils;
import com.cloud.springcloud.thread.ExecutorServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RestTemplate restTemplate;


    private ExecutorService executorService = ExecutorServiceUtil.getInstance().getExecutorService();


    @RequestMapping(value = "/function", method = {RequestMethod.POST, RequestMethod.GET})
    public String function(int thread, int count) throws ExecutionException, InterruptedException {
        //创建一个key  赋值为 100     库存
        redisUtils.set("kucun", count);
        RedisLock redisLock = new RedisLock(redisTemplate, "kucun");
        // 10000 并发 请求扣减库存
        for (int i = 0; i < thread; i++) {
            int finalI = i;

            Future future = executorService.submit(new Callable() {
                @Override
                public Object call() {
                    String str;
                    try {
                        boolean lockde = redisLock.lock();

                        if (lockde) {
                            if (Integer.parseInt(redisUtils.get("kucun").toString()) > 0) {
                                redisUtils.decr("kucun", 1);
                                logger.info("抢购成功！");
                                str = "抢购成功！";
                            } else {
                                logger.info("没库存了！");
                                return "没库存了！";
                            }
                        } else {
                            logger.info("人太拥挤了，请重试！");
                            return "人太拥挤了，请重试！";
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        str = "抢购失败！";
                    } finally {
                        redisLock.unlock();
                    }
                    return str;
                }
            });
            Object object = future.get();


        }


        return "OK";

    }




    private ExecutorService executorService_CachedThreadPool = Executors.newCachedThreadPool() ;

    @Autowired
    private EcommerceOrderService ecommerceOrderService ;

    /**
     * 并发测试接口
     * @param countRequest  请求总数
     * @param threadTotal 同时并发执行的线程数
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/function1", method = {RequestMethod.POST, RequestMethod.GET})
    public String function1(int countRequest, int threadTotal ,String exportPeopleCode , final  int requestSize ) throws ExecutionException, InterruptedException {

        //TODO 第一步查询 ecommerce_order 表 ， 拿到 order_code    list  ,根据 list.szie()/30 来判断 请求数
        List<EcommerceOrder>  list  = ecommerceOrderService.selectOrderByExportPeopleCode(exportPeopleCode);



        //暂时注释，通过接口传过来 控制请求总数
        //countRequest = list.size()%requestSize ==0 ? list.size()/requestSize :list.size()/requestSize+1 ;

        //信号量，此处用于控制并发的线程数
        final Semaphore semaphore = new Semaphore(threadTotal);

        //闭锁，可实现计数器递减
        final CountDownLatch countDownLatch = new CountDownLatch(threadTotal);

        for (int i = 0; i < countRequest; i++) {
            StringBuilder sb =  new StringBuilder() ;
            List<EcommerceOrder>  list1 = new ArrayList<>();
            if((i+1)* requestSize > list.size()){
                list1.addAll(list.subList(i*requestSize ,list.size()));
            }else{
                list1.addAll(list.subList(i*requestSize ,(i+1 )* requestSize));
            }
            for (int j = 0; j < list1.size(); j++) {
                sb.append("'").append(list1.get(j).getOrderCode()).append("',") ;
            }
           String  str = sb.substring(0,sb.length()-1) ;

            logger.info("此次请求的数据为={}",str);
            Future future = executorService_CachedThreadPool.submit(new Callable() {
                @Override
                public Object call() {
                    //执行此方法用于获取执行许可，当总计未释放的许可数不超过200时，
                    //允许通行，否则线程阻塞等待，直到获取到许可。
                    try {
                        semaphore.acquire();
                        MultiValueMap mp = new LinkedMultiValueMap() ;
                        mp.add("data" ,str);
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        ResponseEntity<String>    str =  restTemplate.postForEntity("http://192.168.203.192:8083/ecommerceOrder/synchronizationSAP",mp,String.class) ;//调用服务
                        //释放许可
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        logger.error("出错啦={}",e);
                    }

                    //闭锁减一
                    countDownLatch.countDown();

                    return  str;
                }

            });
            Object object = future.get();

        }
        countDownLatch.await();//线程阻塞，直到闭锁值为0时，阻塞才释放，继续往下执行
        executorService_CachedThreadPool.isTerminated() ;//结束线城池
        return "OK";

    }




}
