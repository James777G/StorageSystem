package org.maven.apache.mapper;

import org.maven.apache.email.Email;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface EmailMapper {


    List<Email> selectAll();


    void addNewEmail(Email email);

    void deleteById(int id);

    void deleteByEmailAddress(String emailAddress);

}
