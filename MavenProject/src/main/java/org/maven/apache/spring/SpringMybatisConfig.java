package org.maven.apache.spring;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.UserMapper;
import org.maven.apache.mybatis.MyBatisOperator;
import org.maven.apache.spring.factoryBean.SqlSessionFactoryBean;
import org.maven.apache.spring.factoryBean.SqlSessionFactoryFactoryBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class SpringMybatisConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SqlSessionFactoryFactoryBean sqlSessionFactoryFactoryBean(){
        return new SqlSessionFactoryFactoryBean();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SqlSessionFactoryBean sqlSessionFactoryBean(){
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setSqlSessionFactoryFactoryBean(sqlSessionFactoryFactoryBean());
        return bean;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        return sqlSessionFactoryFactoryBean().getObject();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public SqlSession sqlSession() throws Exception {
        return sqlSessionFactoryBean().getObject();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ItemMapper itemMapper() throws Exception {
        return sqlSession().getMapper(ItemMapper.class);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public UserMapper userMapper() throws Exception {
        return sqlSession().getMapper(UserMapper.class);
    }


}
