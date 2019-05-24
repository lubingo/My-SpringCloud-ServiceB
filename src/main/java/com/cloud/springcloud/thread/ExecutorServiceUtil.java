package com.cloud.springcloud.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池工具类 单例
 */
public class ExecutorServiceUtil  {
   private  static ExecutorServiceUtil executorServiceUtil= new ExecutorServiceUtil() ;

   public ExecutorService getExecutorService() {
      return executorService;
   }
   private  ExecutorService  executorService = null ;
   public static   ExecutorServiceUtil getInstance(){
      return  executorServiceUtil ;
   }
   private ExecutorServiceUtil(){
      executorService = Executors.newFixedThreadPool(ExecutorServiceConfig.EXECUTORSERVICESIZE);
   }

}
