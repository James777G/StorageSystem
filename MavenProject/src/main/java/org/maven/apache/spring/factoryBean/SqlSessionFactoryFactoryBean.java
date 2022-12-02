package org.maven.apache.spring.factoryBean;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class SqlSessionFactoryFactoryBean implements FactoryBean<SqlSessionFactory> {

    @Override
    public SqlSessionFactory getObject() throws Exception {
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Override
    public Class<?> getObjectType() {
        return SqlSessionFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
