package org.maven.apache.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan(basePackages = "org.maven.apache.service")
@Import({MyBatisAutoConfiguration.class, TransactionConfiguration.class, MailConfiguration.class, ThreadPoolConfiguration.class})
public class SpringConfiguration {
}
