package org.maven.apache.mybatis.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Executors;

import ai.djl.Application;
import org.apache.ibatis.session.SqlSession;
import org.maven.apache.MyLauncher;
import org.maven.apache.item.Item;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.StaffMapper;
import org.maven.apache.mapper.UserMapper;
import org.maven.apache.mybatis.MyBatisOperator;
import org.maven.apache.search.FuzzySearch;
import org.maven.apache.service.staff.CachedStaffService;
import org.maven.apache.service.user.UserService;
import org.maven.apache.service.verificationCode.VerificationCodeService;
import org.maven.apache.spring.SpringConfiguration;
import org.maven.apache.staff.Staff;
import org.maven.apache.user.User;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.StaffCachedUtils;
import org.maven.apache.verificationCode.VerificationCode;
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

	@Test
	public void testSpring(){
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		ItemMapper itemMapper = context.getBean("itemMapper", ItemMapper.class);
		List<Item> items = itemMapper.selectAll();
		System.out.println(items);
		SqlSession sqlSession = (SqlSession) context.getBean("sqlSession");
		sqlSession.clearCache();
		sqlSession.close();
	}

	@Test
	public void testUpdateUser(){
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		UserService userService = context.getBean("userService", UserService.class);
		List<User> ethan = userService.selectByName("Ethan Duan");
		User user = ethan.get(0);
		user.setEmailAddress("hgon777@aucklanduni.ac.nz");
		userService.update(user);

	}

	@Test
	public void testVerificationCode(){
		VerificationCodeService verificationCodeService = MyLauncher.context.getBean("verificationCodeService", VerificationCodeService.class);
		VerificationCode code = new VerificationCode();
		code.setUsername("1231232aab");
		code.setCode(666999);
		verificationCodeService.add(code);
		verificationCodeService.deleteById(3);
		VerificationCode code1 = verificationCodeService.selectByUsername("123123");
		System.out.println(code1);
		List<VerificationCode> verificationCodes = verificationCodeService.selectAll();
		System.out.println(verificationCodes);
	}

	@Test
	public void test1213(){
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
		StaffMapper staffMapper = context.getBean("staffMapper", StaffMapper.class);
		List<Staff> staff = staffMapper.selectAll();
		System.out.println(staff);
		Staff staffA = new Staff();
		staffA.setStaffName("BJamess");
		staffA.setStatus("INACTIVE");
		staffA.setOtherInfo("NO other info");
		staffMapper.add(staffA);
		List<Staff> staffB = staffMapper.selectAll();
		System.out.println(staffB);
		staffA.setStaffName("James");
		staffMapper.updateStaff(staffA);
		System.out.println(staffMapper.selectAll());
		System.out.println(staffMapper.selectByStatus("ACTIVE"));
		System.out.println(staffMapper.selectByStatus("INACTIVE"));
		System.out.println(staffMapper.selectById(1));
		staffMapper.deleteById(1);

	}

	@Test
	public void testZ(){
		CachedStaffService staffService = MyLauncher.context.getBean("staffService", CachedStaffService.class);
		staffService.updateAllCachedStaffData();
		System.out.println(StaffCachedUtils.getLists(StaffCachedUtils.listType.ALL));
		System.out.println(StaffCachedUtils.getLists(StaffCachedUtils.listType.INACTIVE));
		System.out.println(StaffCachedUtils.getLists(StaffCachedUtils.listType.ACTIVE));
		Staff staff = new Staff();
		staff.setStatus("ACTIVE");
		staff.setStaffName("Peter");
		staff.setOtherInfo("I AM GOD");
		staffService.addNewStaff(staff);
		System.out.println(StaffCachedUtils.getLists(StaffCachedUtils.listType.ALL));
		System.out.println(StaffCachedUtils.getLists(StaffCachedUtils.listType.ACTIVE));
		staff.setOtherInfo("I AM NOT GOD");
		staffService.updateTransaction(staff);
		System.out.println(StaffCachedUtils.getLists(StaffCachedUtils.listType.ACTIVE));

	}



}