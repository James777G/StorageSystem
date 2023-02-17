package org.maven.apache.service.text;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service("cargoMessage")
@PropertySource("classpath:/text/message.properties")
public final class CargoBasedTextService extends AbstractMessageTextService {
    @Value("${cargo.header}")
    private String header;

    @Value("${cargo.content.name}")
    private String name;

    @Value("${cargo.content.id}")
    private String id;

    @Value("${message}")
    private String message;

    @Override
    public String getHeaderText(String id, String name) {
        return header + this.id + " " + id + this.name + " " + name + this.message + " ";
    }
}
