package org.maven.apache.service.mail;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public interface MailService {

    /**
     * This method sends recipient an email with a verification code format
     * @param recipient people who receive emails
     * @param verificationCode auto-generated
     */
    void sendEmail(String recipient, String verificationCode);
}
