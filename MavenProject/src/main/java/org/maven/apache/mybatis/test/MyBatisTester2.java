package org.maven.apache.mybatis.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import ai.djl.Application;
import org.maven.apache.item.Item;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.UserMapper;
import org.maven.apache.mybatis.MyBatisOperator;
import org.maven.apache.search.FuzzySearch;
import org.maven.apache.spring.SpringConfiguration;
import org.maven.apache.user.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.Test;

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

	@Test
	public void testUserMapperSelectAll(){
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
		List<User> users = userMapper.selectAll();
		System.out.println(users);
	}

	@Test
	public void testUserMapperSelectById(){
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
		User user = userMapper.selectById(7);
		System.out.println(user);

	}

	@Test
	public void testUserMapperSelectByName(){
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
		List<User> james = userMapper.selectByName(FuzzySearch.getFuzzyName("James"));
		System.out.println(james);
	}

	@Test
	public void testUserMapperSelectByUsername(){
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
		List<User> k = userMapper.selectByUsername(FuzzySearch.getFuzzyName("w"));
		System.out.println(k);

	}

	@Test
	public void testUserMapperAdd() throws InstantiationException, IllegalAccessException {
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
		Class clazz = User.class;
		User user = (User) clazz.newInstance();
		user.setName("Jerry");
		user.setUsername("jerrybbd");
		user.setPassword("5201314");

		userMapper.add(user);
		List<User> users = userMapper.selectAll();
		System.out.println(users);

	}

	@Test
	public void testUserMapperDelete(){
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
		userMapper.deleteById(10);
		List<User> users = userMapper.selectAll();
		System.out.println(users);
	}
	

}