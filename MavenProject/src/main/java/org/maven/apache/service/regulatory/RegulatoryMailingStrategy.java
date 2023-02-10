package org.maven.apache.service.regulatory;

import jakarta.annotation.Resource;
import org.maven.apache.email.Email;
import org.maven.apache.exception.DataNotFoundException;
import org.maven.apache.item.Item;
import org.maven.apache.mapper.EmailMapper;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.RegulatoryMapper;
import org.maven.apache.regulatory.Regulatory;

import java.util.List;
import java.util.function.Consumer;

public final class RegulatoryMailingStrategy extends AbstractRegulatoryStrategy {

    @Resource
    private EmailMapper emailMapper;

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
        emails.forEach(new Consumer<Email>() {
            @Override
            public void accept(Email email) {

            }
        });
    }


}
