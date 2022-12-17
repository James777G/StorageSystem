package org.maven.apache.mapper;

import org.maven.apache.verificationCode.VerificationCode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface VerificationCodeMapper {
    List<VerificationCode> selectAll();
    VerificationCode selectByUsername(String username);

    void add(VerificationCode verificationCode);

    void deleteById(int id);



}
