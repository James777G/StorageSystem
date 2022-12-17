package org.maven.apache.spring;

import org.maven.apache.thread.DedicatedThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolConfiguration {
    @Bean
    public DedicatedThreadPoolExecutor threadPoolExecutor(){
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(10);
        return new DedicatedThreadPoolExecutor(2, 4, 60, TimeUnit.SECONDS, workQueue, new ThreadPoolExecutor.AbortPolicy());
    }
}