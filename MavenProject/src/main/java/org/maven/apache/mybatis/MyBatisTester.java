package org.maven.apache.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisTester {
	
	private static String resource = "mybatis/mybatis-config.xml";
	
	private static InputStream inputStream;
	
	private static SqlSessionFactory sqlSessionFactory;
	
	private static SqlSession sqlSession;
	
	public static List<Object> users;

	public static void initialize() throws IOException {
		//这个是字节输入流， ！！！！ 
		//这个非常重要 ！！！
		//用字节输入流会防止将 Project 转成 Executable Jar 的时候，路径坍缩导致读取不了文件的问题
		//功能上有点像Scanner Class
		inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		//通过 SqlSessionFactory 来构造 SqlSession 对象， SqlSession 简单来讲就是用SQL写的语言
		//通过你的配置文件里的Mapper Map成Java语言
		sqlSession = sqlSessionFactory.openSession();

	}
	
	public static void refreshUsersList() {
		users = sqlSession.selectList("test.selectAll");
	}
	
	public static void printUsersInfo() {
		System.out.println(users);
	}
	
	public static void disconnect() {
		sqlSession.close();
	}

}
