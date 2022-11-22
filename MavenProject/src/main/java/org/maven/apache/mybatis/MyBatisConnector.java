package org.maven.apache.mybatis;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * 这个 Class 是所有特定类型 Connector Class 的母类
 * 这个 Class 必须先于其子类 Initialize 导入 InputStream 数据
 * 执行完这个 Class 的 Initialization， 其子类的各种 Method 才可以使用
 * 
 * 但关闭 Close Session 就用这个 Class close 就好了
 * 在其母类并没有Close Method
 * 
 * @author james
 *
 */
public class MyBatisConnector {
	protected static String resource = "mybatis/mybatis-config.xml";
	protected static InputStream inputStream;
	protected static SqlSessionFactory sqlSessionFactory;
	protected static SqlSession sqlSession;
	
	public static void initialize() throws IOException {
		inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 通过 SqlSessionFactory 来构造 SqlSession 对象， SqlSession 简单来讲就是用SQL写的语言
		// 通过你的配置文件里的Mapper Map成Java语言
		sqlSession = sqlSessionFactory.openSession();
	}
	
	public static void closeSession() {
		sqlSession.close();
	}
}
