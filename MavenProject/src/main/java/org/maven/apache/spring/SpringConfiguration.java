package org.maven.apache.spring;

import org.maven.apache.spring.aspect.ServiceAspect;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan(basePackages = "org.maven.apache.service")
@Import({MyBatisAutoConfiguration.class, TransactionConfiguration.class})
public class SpringConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ServiceAspect mapperAspect(){
        return new ServiceAspect();
    }

}
