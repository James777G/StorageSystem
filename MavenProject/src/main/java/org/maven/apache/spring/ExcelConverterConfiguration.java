package org.maven.apache.spring;

import org.maven.apache.service.excel.ExcelConverterService;
import org.maven.apache.service.excel.ExcelConverterServiceProvider;
import org.maven.apache.service.item.ItemService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(TransactionConfiguration.class)
public class ExcelConverterConfiguration {

    @Bean
    public ExcelConverterServiceProvider excelConverterService(ItemService itemService){
        ExcelConverterServiceProvider excelConverterService = new ExcelConverterServiceProvider();
        excelConverterService.setItemService(itemService);
        return excelConverterService;
    }
}
