package org.maven.apache.mail;

import org.testng.annotations.Test;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailUtils {

    private static final Properties properties = new Properties();

    static{
        InputStream inputStream = MailUtils.class.getResourceAsStream("/mail/mail.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public static void main(String[] args) throws MessagingException {
        System.out.println("Mail start sending.");
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
