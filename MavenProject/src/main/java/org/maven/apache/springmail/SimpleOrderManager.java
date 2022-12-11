package org.maven.apache.springmail;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class SimpleOrderManager implements OrderManager{

    public MailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public SimpleMailMessage getTemplateMessage() {
        return templateMessage;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }

    private MailSender mailSender;

    private SimpleMailMessage templateMessage;


    public void placeOrder(MailSender mailSender, SimpleMailMessage simpleMailMessage) {
        simpleMailMessage.setTo("javamail.springboot@gmail.com");
        simpleMailMessage.setText("Hello spring mail");
        try {
            mailSender.send(simpleMailMessage);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void placeOrder() {

    }
}
