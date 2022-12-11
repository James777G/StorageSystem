package org.maven.apache.spring;


import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import org.maven.apache.mail.SimpleOrderManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class MailConfiguration {

    @Bean
    public Properties mailProperties() throws IOException {
        InputStream inputStream = MailConfiguration.class.getResourceAsStream("/mail/mail.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }

    @Bean
    public Properties accountProperties() throws IOException {
        InputStream inputStream = MailConfiguration.class.getResourceAsStream("/mail/account.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }

    @Bean
    public Session session(Properties mailProperties, Properties accountProperties) throws Exception {
        return Session.getInstance(mailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(accountProperties.getProperty("username"),accountProperties.getProperty("password"));
            }
        });
    }

    @Bean
    public JavaMailSender mailSender(Session session){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setDefaultEncoding("utf-8");
        javaMailSender.setSession(session);
        return javaMailSender;
    }

    @Bean
    public SimpleMailMessage templateMessage(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("javamail.springboot@gmail.com");
        mailMessage.setSubject("Your Order");
        return mailMessage;
    }

    @Bean
    public SimpleOrderManager orderManager(MailSender mailSender, SimpleMailMessage templateMessage){
        SimpleOrderManager orderManager = new SimpleOrderManager();
        orderManager.setMailSender(mailSender);
        orderManager.setTemplateMessage(templateMessage);
        return orderManager;
    }

}
