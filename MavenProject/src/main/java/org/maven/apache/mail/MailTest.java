package org.maven.apache.mail;

import jakarta.mail.MessagingException;
import org.maven.apache.service.mail.MailService;
import org.maven.apache.spring.SpringConfiguration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ExecutorService;

public class MailTest {
    public static void main(String[] args) throws InterruptedException {

        //加载spring上下文环境
        BeanFactory factory = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        MailService simpleOrderManager = (MailService)factory.getBean("mailService");
        ExecutorService threadPoolExecutor = factory.getBean("threadPoolExecutor", ExecutorService.class);
        threadPoolExecutor.execute(() -> {
            try {
                simpleOrderManager.sendHtmlMail("Test", "<div style=\"text-align: center\">\n" +
                        "  <div style=\"font-size: 16px; font-weight: bold;\">This is an automated email please do not reply</div>\n" +
                        "  <hr style=\"color: #223c40; border-color: #223c40\">\n" +
                        "</div>\n" +
                        "<div style=\"padding-top: 45px; text-align: center; align-content: center;\">\n" +
                        "  <div style=\"font-size: 16px; font-weight: bold;\">\n" +
                        "    Welcome to Diamond Homes Limited&#169;<br>\n" +
                        "    You are receiving this email due to the shortage in the following items:\n" +
                        "  </div>\n" +
                        "  <div style=\"padding-top: 0px; padding-bottom: 20px; border: solid thick #223c40; border-radius: 30px; width: 70%; margin-top: 60px; margin-left: auto; margin-right: auto;\">\n" +
                        "    <h2 style=\"font-size: 18px\">\n" +
                        "      Item Name: Thatch<br>\n" +
                        "      <hr style=\"color: #223c40\">\n" +
                        "    </h2>\n" +
                        "    <h3 style=\"font-size: 16px; padding-top: 15px\">\n" +
                        "      Current Unit: 300<br><br>\n" +
                        "      Acceptable amount: 100<br><br>\n" +
                        "    </h3>\n" +
                        "  </div>\n" +
                        "\n" +
                        "  <div style=\"margin-top: 40px; font-weight: bold\">\n" +
                        "    Item Description: This item is used for building thatch made houses. <br><br>\n" +
                        "  </div>\n" +
                        "\n" +
                        "  <hr style=\"margin-top: 40px\">\n" +
                        "  <p style=\"font-size: small\">\n" +
                        "    This verification code is only used internally in Diamond Homes Limited&#169;<br>\n" +
                        "    <br>\n" +
                        "    Company Address: 5 Birmingham Road, Ōtara, Auckland 2013\n" +
                        "  </p>\n" +
                        "\n" +
                        "</div>\n", "jamesgong0719@gmail.com");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });





    }
}
