package org.maven.apache.spring.factoryBean;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SqlSessionFactoryBean implements FactoryBean<SqlSession> {

    /**
     * Dependency Injection
     */
    private SqlSessionFactoryFactoryBean sqlSessionFactoryFactoryBean;


    public SqlSessionFactoryFactoryBean getSqlSessionFactoryFactoryBean() {
        return sqlSessionFactoryFactoryBean;
    }

    public void setSqlSessionFactoryFactoryBean(SqlSessionFactoryFactoryBean sqlSessionFactoryFactoryBean) {
        this.sqlSessionFactoryFactoryBean = sqlSessionFactoryFactoryBean;
    }


    @Override
    public SqlSession getObject() throws Exception {
        return Objects.requireNonNull(sqlSessionFactoryFactoryBean.getObject()).openSession(true);
    }

    @Override
    public Class<?> getObjectType() {
        return SqlSession.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
