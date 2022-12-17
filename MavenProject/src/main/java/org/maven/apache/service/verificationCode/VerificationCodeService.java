package org.maven.apache.service.verificationCode;

import org.maven.apache.verificationCode.VerificationCode;

import java.util.List;

public interface VerificationCodeService {

    List<VerificationCode> selectAll();

    VerificationCode selectByUsername(String username);

    void add(VerificationCode verificationCode);

    void deleteById(int id);
}
