package org.maven.apache.service.mail;

import lombok.Data;
import org.maven.apache.mail.SimpleOrderManager;
import org.springframework.scheduling.annotation.Async;
@Data
public class MailServiceProvider implements MailService {

    private String message;
    private SimpleOrderManager orderManager;

    @Override
    public void sendEmail(String recipient, String verificationCode) {
        orderManager.placeOrder(recipient, message + " " + verificationCode);
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SimpleOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(SimpleOrderManager orderManager) {
		this.orderManager = orderManager;
	}
}
