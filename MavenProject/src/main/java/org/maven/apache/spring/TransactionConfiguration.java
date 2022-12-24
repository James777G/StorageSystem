package org.maven.apache.spring;

import javax.sql.DataSource;

import org.maven.apache.mapper.DateTransactionMapper;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.UserMapper;
import org.maven.apache.mapper.VerificationCodeMapper;
import org.maven.apache.service.DateTransaction.DateTransactionServiceProvider;
import org.maven.apache.service.item.ItemServiceProvider;
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
	public DateTransactionServiceProvider dateTransactionService(DateTransactionMapper dateTransactionMapper){
		DateTransactionServiceProvider dateTransactionService = new DateTransactionServiceProvider();
		dateTransactionService.setDateTransactionMapper(dateTransactionMapper);
		return dateTransactionService;
	}

	@Bean
	public UserServiceProvider userService(UserMapper userMapper) {
		UserServiceProvider userService = new UserServiceProvider();
		userService.setUserMapper(userMapper);
		return userService;
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
