package org.maven.apache.spring.aspect;


import org.aspectj.lang.annotation.After;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PointCut {

    @Before("execution(* org.maven.apache.mapper.*.*(..))")
    public void before() {
        System.out.println("======方法执行前======");
    }

    @After("execution(* org.maven.apache.mapper.*.*(..))")
    public void after() {
        System.out.println("======方法执行后======");
    }
}
