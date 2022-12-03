package org.maven.apache.mail;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.testng.annotations.Test;

public class JavaMail {
	
	@Test
	public static void main(String[] args) throws MessagingException {
		System.out.println("Mail start sending.");
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.port", "587");
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("javamailtester.storagesystem@gmail.com", "kpxhtjsbihqsmall");
			}
		});
		
		Message message = prepareMessage(session);
		Transport.send(message);
		System.out.println("Mail sent.");
	}

	private static Message prepareMessage(Session session) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("javamailtester.storagesystem@gmail.com"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress("javamailtester.storagesystem@gmail.com"));
			message.setSubject("Second verification email");
			message.setText("Welcome to Diamond Homes Limited.\nYour verification code is 香蕉，苹果，大鸭梨");
			return message;
		} catch (Exception ex) {
			Logger.getLogger(JavaMail.class.getName()).log(Level.SEVERE,null,ex);
		} 
		
		return null;
	}
}
