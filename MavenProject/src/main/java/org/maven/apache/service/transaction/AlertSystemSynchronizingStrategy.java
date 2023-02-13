package org.maven.apache.service.transaction;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import org.maven.apache.email.Email;
import org.maven.apache.exception.DataNotFoundException;
import org.maven.apache.exception.NegativeDataException;
import org.maven.apache.item.Item;
import org.maven.apache.mapper.EmailMapper;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.RegulatoryMapper;
import org.maven.apache.mapper.TransactionMapper;
import org.maven.apache.regulatory.Regulatory;
import org.maven.apache.service.mail.MailService;
import org.maven.apache.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("alertStrategy")
public final class AlertSystemSynchronizingStrategy extends AbstractTransactionStrategy {

    @Resource
    private RegulatoryMapper regulatoryMapper;

    @Resource
    private MailService mailService;

    @Resource
    private EmailMapper emailMapper;

    @Override
    public void doStrategy(ItemMapper itemMapper, TransactionMapper transactionMapper, Transaction transaction) throws DataNotFoundException, NegativeDataException {
        if("TAKEN".equals(transaction.getStatus()) && isUrgent(itemMapper, transactionMapper, transaction)){
            sendEmails(itemMapper, transaction);
        }
    }

    @Override
    public void doStrategy(ItemMapper itemMapper, TransactionMapper transactionMapper, int id) throws NegativeDataException {
        Transaction transaction = transactionMapper.selectById(id);
        if("RESTOCK".equals(transaction.getStatus()) && isUrgent(itemMapper,transactionMapper,transaction)){
            sendEmails(itemMapper, transaction);
        }
    }

    private boolean isUrgent(ItemMapper itemMapper, TransactionMapper transactionMapper, Transaction transaction){
        Item item = null;
        Regulatory regulatory = null;

        try{
            item = itemMapper.selectByItemName(transaction.getItemName());
        }catch (Exception e){
            return false;
        }
        Integer unit = item.getUnit();

        try{
            regulatory = regulatoryMapper.selectByName(transaction.getItemName());
            return unit < regulatory.getItemAmount();
        }catch(Exception e){
            return false;
        }
    }

    private void sendEmails(ItemMapper itemMapper, Transaction transaction){
        List<Email> emails = emailMapper.selectAll();
        if(!emails.isEmpty()){
            emails.forEach(email -> {
                try {
                    mailService.sendWarningEmails(itemMapper.selectByItemName(transaction.getItemName()),
                            regulatoryMapper.selectByName(transaction.getItemName()).getItemAmount(), email.getEmailAddress());
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

}
