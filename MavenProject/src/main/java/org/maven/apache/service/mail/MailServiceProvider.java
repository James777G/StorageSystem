package org.maven.apache.service.mail;

import lombok.Data;
import org.maven.apache.mail.SimpleOrderManager;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
public class MailServiceProvider implements MailService {

    private String message;
    private SimpleOrderManager orderManager;

    @Override
    @Async
    public void sendEmail(String recipient, String verificationCode) {
        orderManager.placeOrder(recipient, message + " " + verificationCode);
    }
}
