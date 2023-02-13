package org.maven.apache.service.email;

import org.maven.apache.email.Email;

public interface EmailService {

    void updateCachedEmailData();

    void addNewEmail(Email email);

    void deleteEmailByName(String name);

}
