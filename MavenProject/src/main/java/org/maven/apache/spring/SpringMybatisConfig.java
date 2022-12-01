package org.maven.apache.spring;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mybatis.MyBatisOperator;
import org.maven.apache.spring.factoryBean.SqlSessionFactoryBean;
import org.maven.apache.spring.factoryBean.SqlSessionFactoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class SpringMybatisConfig {

    @Bean
    public SqlSessionFactoryFactoryBean sqlSessionFactoryFactoryBean(){
        return new SqlSessionFactoryFactoryBean();
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(){
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setSqlSessionFactoryFactoryBean(sqlSessionFactoryFactoryBean());
        return bean;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        return sqlSessionFactoryFactoryBean().getObject();
    }

    @Bean
    public SqlSession sqlSession() throws Exception {
        return sqlSessionFactoryBean().getObject();
    }

    @Bean
    public ItemMapper itemMapper() throws Exception {
        return sqlSession().getMapper(ItemMapper.class);
    }


}
