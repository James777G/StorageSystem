package org.maven.apache.service.mail;

public interface MailService {

    void sendEmail(String recipient, String verificationCode);
}
