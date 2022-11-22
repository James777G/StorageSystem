package org.maven.apache.mybatis;

import java.io.IOException;
import java.util.List;

import org.maven.apache.item.Item;
import org.maven.apache.mapper.ItemMapper;

/**
 * 这个 Class 是 MyBatisConnector 的子类， 必须先 Initialize MyBatisConnector 才能
 * 使用这个Class
 * @author james
 *
 */
public class MyBatisItemConnector extends MyBatisConnector {

	protected static ItemMapper itemMapper;
	public static List<Item> items;

	public static ItemMapper getItemMapper() throws IOException {
		itemMapper = sqlSession.getMapper(ItemMapper.class);
		return itemMapper;
	}

	/**
	 * update the list of items
	 */
	public static void updateItemList() {
		items = itemMapper.selectAll();
	}



}
