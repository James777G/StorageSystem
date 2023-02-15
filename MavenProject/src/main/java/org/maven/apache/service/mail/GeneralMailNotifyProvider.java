package org.maven.apache.service.mail;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import org.maven.apache.email.Email;
import org.maven.apache.item.Item;
import org.maven.apache.mapper.EmailMapper;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.RegulatoryMapper;
import org.maven.apache.regulatory.Regulatory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("mailNotifyService")
public class GeneralMailNotifyProvider implements MailNotifyService {

    @Resource
    private ItemMapper itemMapper;

    @Resource
    private RegulatoryMapper regulatoryMapper;

    @Resource
    private EmailMapper emailMapper;

    @Resource
    private MailService mailService;


    @Override
    public void notifyUsers() {
        List<Regulatory> regulatories = regulatoryMapper.selectAll();
        Map<Item, Regulatory> resultMap = new HashMap<>();
        regulatories.forEach(regulatory -> {
            if (isUrgent(regulatory)) {
                resultMap.put(itemMapper.selectByItemName(regulatory.getItemName()), regulatory);
            }
        });
        sendNotifications(resultMap);
    }

    private boolean isUrgent(Regulatory regulatory) {
        String itemName = regulatory.getItemName();
        Item item = null;
        try {
            item = itemMapper.selectByItemName(itemName);
        } catch (Exception e) {
            return false;
        }
        return item.getUnit() < regulatory.getItemAmount();
    }

    private String concatenateHtml(Item item, Regulatory regulatory) {
        return "    <h2 style=\"font-size: 18px\">\n" +
                "      Item Name: " + (item.getItemName().length() >= 10 ? "<br>" : "") + item.getItemName() + "<br>\n" +
                "      <hr style=\"color: #223c40\">\n" +
                "    </h2>\n" +
                "    <h3 style=\"font-size: 16px; padding-top: 15px\">\n" +
                "      Current Unit: " + item.getUnit() + "<br><br>\n" +
                "      Acceptable amount: " + regulatory.getItemAmount() + "<br><br>\n" +
                "    </h3>\n";
    }

    private String joinConcatenatedHtml(Map<Item, Regulatory> map) {
        StringBuilder result = new StringBuilder();
        if (!map.isEmpty()) {
            for (Item item : map.keySet()) {
                result.append(concatenateHtml(item, map.get(item)));
            }
        }
        String generalFollowingHtml = "  </div>\n" +
                "\n" +
                "  <hr style=\"margin-top: 40px\">\n" +
                "  <p style=\"font-size: small\">\n" +
                "    This email is only used internally in Diamond Homes Limited&#169;<br>\n" +
                "    <br>\n" +
                "    Address: 5 Birmingham Road, Ōtara, Auckland 2013\n" +
                "  </p>\n" +
                "</div>";
        String generalLeadingHtml = "<div style=\"text-align: center\">\n" +
                "  <div style=\"font-size: 16px; font-weight: bold;\">This is an automated email please do not reply</div>\n" +
                "  <hr style=\"color: #223c40; border-color: #223c40\">\n" +
                "</div>\n" +
                "\n" +
                "<div style=\"padding-top: 45px; text-align: center; align-content: center;\">\n" +
                "  <div style=\"font-size: 16px; font-weight: bold;\">\n" +
                "    Welcome to Diamond Homes Limited&#169;<br>\n" +
                "    You are receiving this email due to the shortage in the following items:\n" +
                "  </div>\n" +
                "  <div style=\"padding-top: 0px; padding-bottom: 20px; border: solid thick #223c40; border-radius: 30px; width: 70%; margin-top: 60px; margin-left: auto; margin-right: auto;\"\n>";
        return generalLeadingHtml + result + generalFollowingHtml;
    }

    private void sendNotifications(Map<Item, Regulatory> map) {
        List<Email> emails = emailMapper.selectAll();
        if (!emails.isEmpty()) {
            emails.forEach(email -> {
                try {
                    mailService.sendHtmlMail("Notification", joinConcatenatedHtml(map), email.getEmailAddress());
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });
        }

    }

}
