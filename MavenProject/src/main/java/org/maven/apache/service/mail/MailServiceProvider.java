package org.maven.apache.service.mail;

import lombok.Data;
import org.maven.apache.mail.SimpleOrderManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Data
@Component
public class MailServiceProvider implements MailService {

    private String message;
    private SimpleOrderManager orderManager;

    @Override
    public void sendEmail(String recipient, String verificationCode) {
        orderManager.placeOrder(recipient, message + " " + verificationCode);
    }

}
