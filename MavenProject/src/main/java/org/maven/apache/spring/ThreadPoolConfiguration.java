package org.maven.apache.spring;

import org.maven.apache.thread.DedicatedThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfiguration {
    @Bean
    public DedicatedThreadPoolExecutor threadPoolExecutor() {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(200);
        return new DedicatedThreadPoolExecutor(4, 4, 60, TimeUnit.SECONDS, workQueue, new ThreadPoolExecutor.AbortPolicy());
    }
}
