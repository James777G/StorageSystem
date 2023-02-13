package org.maven.apache.service.mail;

import jakarta.mail.MessagingException;
import org.maven.apache.item.Item;
import org.maven.apache.regulatory.Regulatory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public interface MailService {

    /**
     * This method sends recipient an email with a verification code format
     * @param recipient people who receive emails
     * @param verificationCode auto-generated
     */
    void sendEmail(String recipient, String verificationCode) throws MessagingException;

    void sendWarningEmails(Item item, int itemAmount, String recipient) throws MessagingException;

    void sendHtmlMail(String subject, String html, String recipient) throws MessagingException;
}
