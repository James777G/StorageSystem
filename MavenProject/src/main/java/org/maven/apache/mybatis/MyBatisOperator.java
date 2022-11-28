package org.maven.apache.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.maven.apache.spring.SpringConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyBatisOperator {

    private static ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

    public static void closeSession(){
        context.getBean("sqlSession", SqlSession.class).close();
    }

    public static void clearCache(){
        context.getBean("sqlSession", SqlSession.class).clearCache();
    }
}
