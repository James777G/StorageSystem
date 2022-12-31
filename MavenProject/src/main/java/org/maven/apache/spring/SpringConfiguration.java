package org.maven.apache.spring;

import org.maven.apache.spring.aspect.ExcelAspect;
import org.maven.apache.spring.aspect.MailAspect;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan(basePackages = "org.maven.apache.service")
@Import({MyBatisAutoConfiguration.class, TransactionConfiguration.class, MailConfiguration.class, ThreadPoolConfiguration.class, ExcelConverterConfiguration.class})
public class SpringConfiguration {

    @Bean
    public MailAspect mailAspect(){
        return new MailAspect();
    }

    @Bean
    public ExcelAspect excelAspect(){
        return new ExcelAspect();
    }
}
