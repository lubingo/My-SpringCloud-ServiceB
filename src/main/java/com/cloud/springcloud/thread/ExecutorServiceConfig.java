package com.cloud.springcloud.thread;

/**
 * 线程池配置类
 */
public class ExecutorServiceConfig {

    public   static final  int   EXECUTORSERVICESIZE = Runtime.getRuntime().availableProcessors() ;//默认线程池大小(CPU 个数)

}
