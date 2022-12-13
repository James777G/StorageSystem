package org.maven.apache.service.mail;

import org.springframework.scheduling.annotation.Async;

public interface MailService {

    @Async
    void sendEmail(String recipient, String verificationCode);
}
