package org.maven.apache.mybatis;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.maven.apache.item.Item;
import org.maven.apache.mapper.ItemMapper;

public class MyBatisTester2 {
	
	
	public static void main(String[] args) throws IOException {
		List<Item> result;
		MyBatisConnector.initialize();
		ItemMapper itemMapper = MyBatisItemConnector.getItemMapper();
		result = itemMapper.selectAll();
		System.out.println(result);
		MyBatisItemConnector.closeSession();
	}
	

}
