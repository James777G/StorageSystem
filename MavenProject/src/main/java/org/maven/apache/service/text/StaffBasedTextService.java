package org.maven.apache.service.text;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service("staffMessage")
@PropertySource("classpath:/text/message.properties")
public final class StaffBasedTextService extends AbstractMessageTextService {

    @Value("${staff.header}")
    private String header;

    @Value("${staff.content.id}")
    private String id;

    @Value("${staff.content.name}")
    private String name;

    @Value("${message}")
    private String message;

    @Override
    public String getHeaderText(String id, String name) {
        return header + this.id + " " + id + this.name + " " + name + this.message + " ";
    }
}
