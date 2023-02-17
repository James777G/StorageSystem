package org.maven.apache.service.text;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service("transactionMessage")
@PropertySource("classpath:/text/message.properties")
public final class TransactionBasedTextService extends AbstractMessageTextService {

    @Value("${transaction.header}")
    private String header;

    @Value(("${transaction.content.id}"))
    private String id;

    @Value("${transaction.content.name}")
    private String name;

    @Value("${message}")
    private String message;

    @Override
    public String getHeaderText(String id, String name) {
        return header + this.id + " " + id + this.name + " " + name + message + " ";
    }

}
