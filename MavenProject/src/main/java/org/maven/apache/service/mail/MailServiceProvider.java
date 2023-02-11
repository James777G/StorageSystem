package org.maven.apache.service.mail;

import jakarta.mail.MessagingException;
import lombok.Data;
import org.maven.apache.item.Item;
import org.maven.apache.mail.SimpleOrderManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Data
@Component
public class MailServiceProvider implements MailService {

    private String message;
    private SimpleOrderManager orderManager;

    @Override
    public void sendEmail(String recipient, String verificationCode) throws MessagingException {
        sendHtmlMail("Verification Code", "<div style=\"text-align: center\">\n" +
                "    <div style=\"font-size: 16px; font-weight: bold;\">This is an automated email please do not reply</div>\n" +
                "    <hr style=\"color: #223c40; border-color: #223c40\">\n" +
                "</div>\n" +
                "<div style=\"padding-top: 45px; text-align: center; align-content: center;\">\n" +
                "    <div style=\"font-size: 16px; font-weight: bold;\">\n" +
                "        Welcome to Diamond Homes Limited&#169;\n" +
                "    </div>\n" +
                "    <div style=\"width: 250px; height: 200px; backdrop-filter: blur(5px); margin-top: 30px; margin-left: auto; margin-right: auto; background: rgba(255, 255, 255, 0.6); box-shadow: 3px 3px 10px rgba(0,0,0,0.1); padding-top: 2px; border-radius: 15px; border: thick solid #223c40\">\n" +
                "        <div style=\"width: 250px; height: 25px; background-color: #223c40; text-align: center;\">\n" +
                "            <h4 style=\"color: white\">Verification Code:</h4>\n" +
                "        </div>\n" +
                "        <div>\n" +
                "            <h1 style=\"color: #223c40; font-size: 30px; padding-top: 20px\">"  +  verificationCode + "</h1>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <hr style=\"margin-top: 150px\">\n" +
                "    <div style=\"display: flex\"></div>\n" +
                "    <p style=\"font-size: small\">\n" +
                "        This verification code is only used internally in Diamond Homes Limited&#169;<br>\n" +
                "        <br>\n" +
                "        Company Address: 5 Birmingham Road, Ōtara, Auckland 2013\n" +
                "    </p>\n" +
                "</div>", recipient);
    }

    @Override
    public void sendWarningEmails(Item item, int itemAmount, String recipient) throws MessagingException {
        orderManager.sendHtmlEmail("Warehouse Warning", "<div style=\"text-align: center\">\n" +
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
                "      Item Name: " + (item.getItemName().length() >= 10 ? "<br>" : "") + item.getItemName() +"<br>\n" +
                "      <hr style=\"color: #223c40\">\n" +
                "    </h2>\n" +
                "    <h3 style=\"font-size: 16px; padding-top: 15px\">\n" +
                "      Current Unit: " + item.getUnit() + "<br><br>\n" +
                "      Acceptable amount: " + itemAmount + "<br><br>\n" +
                "    </h3>\n" +
                "  </div>\n" +
                "\n" +
                "  <div style=\"margin-top: 40px; font-weight: bold\">\n" +
                "    Item Description: <br><br>" + (item.getDescription().isBlank() ? "No Description Available" : item.getDescription()) + " <br><br>\n" +
                "  </div>\n" +
                "\n" +
                "  <hr style=\"margin-top: 40px\">\n" +
                "  <p style=\"font-size: small\">\n" +
                "    This email is only used internally in Diamond Homes Limited&#169;<br>\n" +
                "    <br>\n" +
                "    Address: 5 Birmingham Road, Ōtara, Auckland\n" +
                "  </p>\n" +
                "\n" +
                "</div>", recipient);
    }


    @Override
    public void sendHtmlMail(String subject, String html, String recipient) throws MessagingException {
        orderManager.sendHtmlEmail(subject, html, recipient);
    }

}
