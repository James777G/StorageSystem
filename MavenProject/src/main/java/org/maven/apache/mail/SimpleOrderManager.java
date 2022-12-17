package org.maven.apache.mail;

import lombok.Data;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@Data
public class SimpleOrderManager{

    private MailSender mailSender;

    private SimpleMailMessage templateMessage;

    public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public SimpleMailMessage getTemplateMessage() {
		return templateMessage;
	}

	public void setTemplateMessage(SimpleMailMessage templateMessage) {
		this.templateMessage = templateMessage;
	}

	public void placeOrder(String recipientEmail, String text) {
        templateMessage.setTo(recipientEmail);
        templateMessage.setText(text);
        try {
            mailSender.send(templateMessage);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }
}
