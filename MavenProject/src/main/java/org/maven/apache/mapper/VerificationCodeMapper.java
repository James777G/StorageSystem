package org.maven.apache.mapper;

import org.maven.apache.verificationCode.VerificationCode;

import java.util.List;

public interface VerificationCodeMapper {
    List<VerificationCode> selectAll();
    VerificationCode selectByUsername(String username);

    void add(VerificationCode verificationCode);



}
