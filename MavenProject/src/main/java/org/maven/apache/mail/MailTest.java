package org.maven.apache.mail;

import org.maven.apache.spring.SpringConfiguration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MailTest {
    public static void main(String[] args) {

        //加载spring上下文环境
        BeanFactory factory = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        SimpleOrderManager simpleOrderManager = (SimpleOrderManager)factory.getBean("orderManager");

        simpleOrderManager.placeOrder("zj2079260754@gmail.com", "666");



    }
}
