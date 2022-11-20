package org.maven.apache.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.maven.apache.mapper.UserMapper;
import org.maven.apache.user.User;

public class MyBatisTester2 {

	public static void main(String[] args) throws IOException {
		
		String resource = "mybatis/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		//通过 SqlSessionFactory 来构造 SqlSession 对象， SqlSession 简单来讲就是用SQL写的语言
		//通过你的配置文件里的Mapper Map成Java语言
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		List<User> users = userMapper.selectAll();
		System.out.println(users);
		sqlSession.close();


	}

}
