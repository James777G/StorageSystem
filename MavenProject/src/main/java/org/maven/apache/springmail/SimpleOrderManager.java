package org.maven.apache.springmail;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SimpleOrderManager implements OrderManager{

    @Resource
    private MailSender mailSender;
    @Resource
    private SimpleMailMessage templateMessage;

    @Override
    public void placeOrder() {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo("javamail.springboot@gmail.com");
        msg.setText("Hello spring mail");
        try {
            this.mailSender.send(msg);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }
}
