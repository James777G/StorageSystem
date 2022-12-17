package org.maven.apache.service.mail;

import org.springframework.scheduling.annotation.Async;

public interface MailService {


    void sendEmail(String recipient, String verificationCode);
}
