package org.maven.apache.springmail;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailTest {
    public static void main(String[] args) {

        //加载spring上下文环境
        BeanFactory factory = new ClassPathXmlApplicationContext("spring.xml");
        SimpleOrderManager simpleOrderManager = (SimpleOrderManager)factory.getBean("simpleOrderManager");
        MailSender mailSender = factory.getBean("mailSender", MailSender.class);
        SimpleMailMessage templateMessage = factory.getBean("templateMessage", SimpleMailMessage.class);

        simpleOrderManager.placeOrder(mailSender, templateMessage);



    }
}
