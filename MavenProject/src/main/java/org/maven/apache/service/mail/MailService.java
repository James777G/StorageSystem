package org.maven.apache.service.mail;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public interface MailService {

    void sendEmail(String recipient, String verificationCode);
}
