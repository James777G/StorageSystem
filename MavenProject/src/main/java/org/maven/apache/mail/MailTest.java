package org.maven.apache.mail;

import org.maven.apache.service.mail.MailService;
import org.maven.apache.spring.SpringConfiguration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MailTest {
    public static void main(String[] args) {

        //加载spring上下文环境
        BeanFactory factory = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        MailService simpleOrderManager = (MailService)factory.getBean("mailService");

        simpleOrderManager.sendEmail("jamesgong0719@gmail.com", "666794");



    }
}
