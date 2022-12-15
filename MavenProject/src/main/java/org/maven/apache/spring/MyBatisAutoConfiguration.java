package org.maven.apache.spring;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "org.maven.apache.mapper" )
@MapperScan(basePackages = "org.maven.apache.mapper")
public class MyBatisAutoConfiguration {

    // please change this name to your name when connecting to your local databases
    // People with username "root" and password "" change to "Common"
    // Otherwise "Kylyn", "Chauncey"
    private static final String name = "Common";

    @Bean
    public Properties properties(){
        Properties properties = new Properties();
        String propertiesLocation = "/spring/spring.properties";
        InputStream inputStream = MyBatisAutoConfiguration.class.getResourceAsStream(propertiesLocation);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    @Bean
    public DataSource dataSource(Properties properties){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(properties.getProperty("Driver"));
        dataSource.setUrl(properties.getProperty(name + "Connection"));
        dataSource.setUsername(properties.getProperty(name + "Username"));
        dataSource.setPassword(properties.getProperty(name + "Password"));
        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource, Properties properties) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(properties.getProperty("MapperPackage"));
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(properties.getProperty("MapperClasses"));
        sqlSessionFactoryBean.setMapperLocations(resources);
        return sqlSessionFactoryBean;
    }


}
