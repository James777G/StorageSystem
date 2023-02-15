package org.maven.apache.spring;

import org.maven.apache.mapper.*;
import org.maven.apache.service.DateTransaction.DateTransactionServiceProvider;
import org.maven.apache.service.item.ItemServiceProvider;
import org.maven.apache.service.message.MessageServiceProvider;
import org.maven.apache.service.text.TransactionTextService;
import org.maven.apache.service.user.UserServiceProvider;
import org.maven.apache.service.verificationCode.VerificationCodeServiceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@Import(MyBatisAutoConfiguration.class)
@ComponentScan(basePackages = "org.maven.apache.service")
public class TransactionConfiguration {

    @Bean
    public ItemServiceProvider itemService(ItemMapper itemMapper, TransactionMapper transactionMapper, TransactionTextService transactionTextService) {
        ItemServiceProvider itemService = new ItemServiceProvider();
        itemService.setItemMapper(itemMapper);
        itemService.setTransactionMapper(transactionMapper);
        itemService.setTransactionTextService(transactionTextService);
        return itemService;
    }

    @Bean
    public UserServiceProvider userService(UserMapper userMapper) {
        UserServiceProvider userService = new UserServiceProvider();
        userService.setUserMapper(userMapper);
        return userService;
    }

    @Bean
    public MessageServiceProvider messageService(MessageMapper messageMapper) {
        MessageServiceProvider messageService = new MessageServiceProvider();
        messageService.setMessageMapper(messageMapper);
        return messageService;
    }

//	@Bean
//	public CachedMessageServiceProvider cachedMessageService(MessageMapper messageMapper){
//		CachedMessageServiceProvider cachedMessageService=new CachedMessageServiceProvider();
//		cachedMessageService.setMessageMapper(messageMapper);
//		return cachedMessageService;
//	}

    @Bean
    public DateTransactionServiceProvider dateTransactionService(DateTransactionMapper dateTransactionMapper) {
        DateTransactionServiceProvider dateTransactionService = new DateTransactionServiceProvider();
        dateTransactionService.setDateTransactionMapper(dateTransactionMapper);
        return dateTransactionService;
    }


    @Bean
    public VerificationCodeServiceProvider verificationCodeService(VerificationCodeMapper verificationCodeMapper) {
        VerificationCodeServiceProvider verificationCodeService = new VerificationCodeServiceProvider();
        verificationCodeService.setVerificationCodeMapper(verificationCodeMapper);
        return verificationCodeService;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
