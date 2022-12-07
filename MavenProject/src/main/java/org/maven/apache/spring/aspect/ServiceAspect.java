package org.maven.apache.spring.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
public class ServiceAspect {

    @Pointcut("execution(* org.maven.apache.service.*.*(..))")
    public void pointcut(){}

    @Before(value="pointcut")
    public void before() {
        System.out.println("======方法执行前======");
    }

    @After(value="pointcut")
    public void after() {
        System.out.println("======方法执行后======");
    }
}
