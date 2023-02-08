package org.maven.apache.service.text;

public interface TransactionTextService {

    String getTextInAddItem(String itemName, int itemAmount);

    String getTextInDeleteItem(String itemName, int itemAmount);

}
