package org.maven.apache.spring;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mybatis.MyBatisOperator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class SpringMybatisConfig {

    private String resource = "mybatis/mybatis-config.xml";

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Bean
    public SqlSession sqlSession() throws IOException {
        return sqlSessionFactory().openSession(true);
    }

    @Bean
    public ItemMapper itemMapper() throws IOException {
        return sqlSession().getMapper(ItemMapper.class);
    }


}
