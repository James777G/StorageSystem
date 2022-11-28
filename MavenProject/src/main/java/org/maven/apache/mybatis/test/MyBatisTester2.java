package org.maven.apache.mybatis.test;

import java.io.IOException;
import java.util.List;

import ai.djl.Application;
import org.maven.apache.item.Item;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mybatis.MyBatisOperator;
import org.maven.apache.search.FuzzySearch;
import org.maven.apache.spring.SpringConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyBatisTester2 {
	
	
	public static void main(String[] args) throws IOException {
		List<Item> result;
		List<Item> result2;
		String name = "thatch";
		int unit = 200;
		Item item = new Item();
		item.setItemName(name);
		item.setUnit(unit);
		item.setItemID(1);
		//处理参数

		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		ItemMapper itemMapper = (ItemMapper) context.getBean("itemMapper");

		result = itemMapper.selectAll();
		item = itemMapper.selectById(2);
		result2 = itemMapper.selectByCondition(FuzzySearch.getFuzzyName("Ele"), 500);
		System.out.println(result2);
		System.out.println(item.getItemName());
		System.out.println(result);
		
		MyBatisOperator.closeSession();
	}
	

}