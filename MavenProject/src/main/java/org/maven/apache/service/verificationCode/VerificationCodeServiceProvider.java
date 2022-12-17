package org.maven.apache.service.verificationCode;

import org.maven.apache.mapper.VerificationCodeMapper;
import org.maven.apache.verificationCode.VerificationCode;

import java.util.List;

public class VerificationCodeServiceProvider implements VerificationCodeService{

    private VerificationCodeMapper verificationCodeMapper;

    public VerificationCodeMapper getVerificationCodeMapper() {
        return verificationCodeMapper;
    }

    public void setVerificationCodeMapper(VerificationCodeMapper verificationCodeMapper) {
        this.verificationCodeMapper = verificationCodeMapper;
    }

    @Override
    public List<VerificationCode> selectAll() {
        return verificationCodeMapper.selectAll();
    }

    @Override
    public VerificationCode selectByUsername(String username) {
        return verificationCodeMapper.selectByUsername(username);
    }

    @Override
    public void add(VerificationCode verificationCode) {
        verificationCodeMapper.add(verificationCode);
    }

    @Override
    public void deleteById(int id) {
        verificationCodeMapper.deleteById(id);
    }
}
