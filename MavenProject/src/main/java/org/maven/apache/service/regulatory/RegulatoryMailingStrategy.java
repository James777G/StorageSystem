package org.maven.apache.service.regulatory;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import org.maven.apache.email.Email;
import org.maven.apache.exception.DataNotFoundException;
import org.maven.apache.item.Item;
import org.maven.apache.mapper.EmailMapper;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.RegulatoryMapper;
import org.maven.apache.regulatory.Regulatory;
import org.maven.apache.service.mail.MailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("regulatoryMailingStrategy")
public final class RegulatoryMailingStrategy extends AbstractRegulatoryStrategy {

    @Resource
    private EmailMapper emailMapper;

    @Resource
    private MailService mailService;

    @Override
    public boolean doStrategy(Regulatory regulatory, ItemMapper itemMapper, RegulatoryMapper regulatoryMapper) throws DataNotFoundException {
        Item item = itemMapper.selectByItemName(regulatory.getItemName());
        if(item.getUnit() < regulatory.getItemAmount()){
            sendEmail(item, regulatory.getItemAmount());
        }
        return true;
    }

    private void sendEmail(Item item, int itemAmount) {
        List<Email> emails = emailMapper.selectAll();
        if(emails.isEmpty()){
            return;
        }
        emails.forEach(email -> {
            try {
                mailService.sendWarningEmails(item, itemAmount, email.getEmailAddress());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
    }


}
