package org.maven.apache.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.maven.apache.item.Item;
import org.maven.apache.mapper.ItemMapper;

public class MyBatisItemConnector {

	private static String resource = "mybatis/mybatis-config.xml";
	private static InputStream inputStream;
	private static SqlSessionFactory sqlSessionFactory;
	private static SqlSession sqlSession;
	protected static ItemMapper itemMapper;
	public static List<Item> items;

	public static void initialize() throws IOException {
		inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 通过 SqlSessionFactory 来构造 SqlSession 对象， SqlSession 简单来讲就是用SQL写的语言
		// 通过你的配置文件里的Mapper Map成Java语言
		sqlSession = sqlSessionFactory.openSession();
		itemMapper = sqlSession.getMapper(ItemMapper.class);
	}

	/**
	 * update the list of items
	 */
	public static void updateItemList() {
		items = itemMapper.selectAll();
	}

	public static void closeSession() {
		sqlSession.close();
	}

}
