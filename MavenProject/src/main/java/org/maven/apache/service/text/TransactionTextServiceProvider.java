package org.maven.apache.service.text;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service("transactionTextService")
@PropertySource("classpath:/text/transaction.properties")
public class TransactionTextServiceProvider implements TransactionTextService {

    @Value("${transaction.add.item.prependText}")
    private String prependTextInAdd;

    @Value("${transaction.add.item.appendText}")
    private String appendTextInAdd;

    @Value("${transaction.delete.item.prependText}")
    private String prependTextInDelete;

    @Value("${transaction.delete.item.appendText}")
    private String appendTextInDelete;


    @Override
    public String getTextInAddItem(String itemName, int itemAmount) {
        return prependTextInAdd + " " + itemAmount + " " + itemName + " " + appendTextInAdd;
    }

    @Override
    public String getTextInDeleteItem(String itemName, int itemAmount) {
        return prependTextInDelete + " " + itemAmount + " " + itemName + " " + appendTextInDelete;
    }
}
