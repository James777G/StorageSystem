package org.maven.apache.spring;

import org.maven.apache.mapper.TransactionMapper;
import org.maven.apache.service.excel.ExcelConverterService;
import org.maven.apache.service.excel.ExcelConverterServiceProvider;
import org.maven.apache.service.item.ItemService;
import org.maven.apache.service.staff.StaffDAOService;
import org.maven.apache.spring.aspect.ExcelAspect;
import org.maven.apache.spring.aspect.MailAspect;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan(basePackages = "org.maven.apache.service")
@Import({MyBatisAutoConfiguration.class, TransactionConfiguration.class, MailConfiguration.class, ThreadPoolConfiguration.class})
public class SpringConfiguration {

    @Bean
    public MailAspect mailAspect() {
        return new MailAspect();
    }

    @Bean
    public ExcelAspect excelAspect() {
        return new ExcelAspect();
    }

    @Bean
    public ExcelConverterService excelConverterService(ItemService itemService, StaffDAOService staffDAOService, TransactionMapper transactionMapper) {
        ExcelConverterServiceProvider excelConverterServiceProvider = new ExcelConverterServiceProvider();
        excelConverterServiceProvider.setStaffDAOService(staffDAOService);
        excelConverterServiceProvider.setItemService(itemService);
        excelConverterServiceProvider.setTransactionMapper(transactionMapper);
        return excelConverterServiceProvider;
    }
}
