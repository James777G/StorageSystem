package org.maven.apache.spring;


import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.maven.apache.mail.SimpleOrderManager;
import org.maven.apache.service.mail.MailService;
import org.maven.apache.service.mail.MailServiceProvider;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
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
    public Properties messageProperties() throws IOException {
        InputStream inputStream = MailConfiguration.class.getResourceAsStream("/mail/mailText.properties");
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
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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
    public SimpleMailMessage templateMessage(Properties accountProperties, Properties messageProperties){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(accountProperties.getProperty("username"));
        mailMessage.setSubject(messageProperties.getProperty("subject"));
        return mailMessage;
    }



    @Bean
    public SimpleOrderManager orderManager(JavaMailSender mailSender, SimpleMailMessage templateMessage, Properties accountProperties) throws MessagingException {
        SimpleOrderManager orderManager = new SimpleOrderManager();
        orderManager.setMailSender(mailSender);
        orderManager.setTemplateMessage(templateMessage);
        return orderManager;
    }

    @Bean
    public String message(Properties messageProperties){
        return messageProperties.getProperty("mainMessage");
    }

    @Bean
    public MailService mailService(SimpleOrderManager orderManager, String message){
        MailServiceProvider serviceProvider = new MailServiceProvider();
        serviceProvider.setOrderManager(orderManager);
        serviceProvider.setMessage(message);
        return serviceProvider;
    }

}
