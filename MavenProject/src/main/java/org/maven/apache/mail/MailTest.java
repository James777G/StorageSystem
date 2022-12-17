package org.maven.apache.mail;

import org.maven.apache.service.mail.MailService;
import org.maven.apache.spring.SpringConfiguration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ExecutorService;

public class MailTest {
    public static void main(String[] args) throws InterruptedException {

        //加载spring上下文环境
        BeanFactory factory = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        MailService simpleOrderManager = (MailService)factory.getBean("mailService");
        ExecutorService threadPoolExecutor = factory.getBean("threadPoolExecutor", ExecutorService.class);
        threadPoolExecutor.execute(() -> simpleOrderManager.sendEmail("xuxinhuiqiang@gmail.com", "666794123"));





    }
}
