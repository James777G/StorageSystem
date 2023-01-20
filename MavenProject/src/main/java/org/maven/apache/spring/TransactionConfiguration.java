package org.maven.apache.spring;

import javax.sql.DataSource;

import org.maven.apache.mapper.*;
import org.maven.apache.service.DateTransaction.DateTransactionServiceProvider;
import org.maven.apache.service.item.ItemServiceProvider;
import org.maven.apache.service.transaction.cachedTransactionServiceProvider;
import org.maven.apache.service.user.UserServiceProvider;
import org.maven.apache.service.verificationCode.VerificationCodeServiceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


@Configuration
@Import(MyBatisAutoConfiguration.class)
public class TransactionConfiguration {

	@Bean
	public ItemServiceProvider itemService(ItemMapper itemMapper) {
		ItemServiceProvider itemService = new ItemServiceProvider();
		itemService.setItemMapper(itemMapper);
		return itemService;
	}

	@Bean
	public UserServiceProvider userService(UserMapper userMapper) {
		UserServiceProvider userService = new UserServiceProvider();
		userService.setUserMapper(userMapper);
		return userService;
	}

	@Bean
	public DateTransactionServiceProvider dateTransactionService(DateTransactionMapper dateTransactionMapper){
		DateTransactionServiceProvider dateTransactionService = new DateTransactionServiceProvider();
		dateTransactionService.setDateTransactionMapper(dateTransactionMapper);
		return dateTransactionService;
	}

	@Bean
	public cachedTransactionServiceProvider TransactionService(TransactionMapper transactionMapper){
		cachedTransactionServiceProvider cachedTransactionServiceProvider = new cachedTransactionServiceProvider();
		cachedTransactionServiceProvider.setTransactionMapper(transactionMapper);
		return cachedTransactionServiceProvider;
	}

	@Bean
	public VerificationCodeServiceProvider verificationCodeService(VerificationCodeMapper verificationCodeMapper){
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
