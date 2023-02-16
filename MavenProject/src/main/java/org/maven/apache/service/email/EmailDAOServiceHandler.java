package org.maven.apache.service.email;

import jakarta.annotation.Resource;
import org.maven.apache.email.Email;
import org.maven.apache.exception.BaseException;
import org.maven.apache.mapper.EmailMapper;
import org.maven.apache.utils.EmailCachedUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("emailService")
public class EmailDAOServiceHandler implements EmailService {

    @Resource
    private EmailMapper emailMapper;

    @Resource
    private EmailCachedDataManipulationService emailDataManipulator;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = BaseException.class)
    public void updateCachedEmailData() {
        EmailCachedUtils.putLists(EmailCachedUtils.listType.ALL,
                emailDataManipulator.getPagedCacheList(emailMapper.selectAll(), 3));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BaseException.class)
    public void addNewEmail(Email email) {
        emailMapper.addNewEmail(email);
        updateCachedEmailData();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BaseException.class)
    public void deleteEmailByName(String name) {
        emailMapper.deleteByEmailAddress(name);
        updateCachedEmailData();
    }
}
