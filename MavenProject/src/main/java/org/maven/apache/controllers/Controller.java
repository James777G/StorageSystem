package org.maven.apache.controllers;

import javafx.fxml.FXML;
import org.maven.apache.mail.SimpleOrderManager;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class Controller {

    @FXML
    private void send(){
        BeanFactory factory = new ClassPathXmlApplicationContext("spring.xml");
        SimpleOrderManager simpleOrderManager = (SimpleOrderManager)factory.getBean("simpleOrderManager");
        MailSender mailSender = factory.getBean("mailSender", MailSender.class);
        SimpleMailMessage templateMessage = factory.getBean("templateMessage", SimpleMailMessage.class);
//
//        simpleOrderManager.placeOrder(mailSender, templateMessage);
    }
}
