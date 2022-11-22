package org.maven.apache.mybatis;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.maven.apache.item.Item;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.search.FuzzySearch;

public class MyBatisTester2 {
	
	
	public static void main(String[] args) throws IOException {
		List<Item> result;
		List<Item> result2;
		Item item;
		//处理参数
		
		MyBatisConnector.initialize();
		ItemMapper itemMapper = MyBatisItemConnector.getItemMapper();
		result = itemMapper.selectAll();
		item = itemMapper.selectById(2);
		result2 = itemMapper.selectByCondition(FuzzySearch.getFuzzyName("ele"), 10);
		System.out.println(result2);
		System.out.println(item.getItemName());
		System.out.println(result);
		MyBatisItemConnector.closeSession();
	}
	

}
