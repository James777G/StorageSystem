package org.maven.apache.spring;

import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.UserMapper;
import org.maven.apache.service.item.ItemServiceProvider;
import org.maven.apache.service.user.UserServiceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class TransactionConfiguration {

    @Bean
    public ItemServiceProvider itemService(ItemMapper itemMapper){
        ItemServiceProvider itemService = new ItemServiceProvider();
        itemService.setItemMapper(itemMapper);
        return itemService;
    }

    @Bean
    public UserServiceProvider userService(UserMapper userMapper){
        UserServiceProvider userService = new UserServiceProvider();
        userService.setUserMapper(userMapper);
        return userService;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
