package org.maven.apache.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.maven.apache.MyLauncher;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Aspect
@Component
public class ExcelAspect {

    @Around("execution(* org.maven.apache.service.excel.*.*(..))")
    public void around(ProceedingJoinPoint joinPoint){
        ExecutorService threadPoolExecutor = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);
        threadPoolExecutor.execute(() -> {
            try {
                joinPoint.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }
}
