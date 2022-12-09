package org.maven.apache.springmail;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MailTest {
    public static void main(String[] args) {

        //加载spring上下文环境
        BeanFactory factory = new ClassPathXmlApplicationContext("spring.xml");
        SimpleOrderManager simpleOrderManager = (SimpleOrderManager)factory.getBean("simpleOrderManager");

        simpleOrderManager.placeOrder();
    }
}
