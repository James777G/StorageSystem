package org.maven.apache.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.maven.apache.item.Item;

/**
 * 这个 Interface 支持模糊搜索
 * @author james
 *
 */
public interface ItemMapper {
	
	/**
	 * 查看所有
	 * @return List
	 */
	List<Item> selectAll();
	
	/**
	 * 按ID来查询
	 * @param id
	 * @return item
	 */
	Item selectById(int id);
	
	/**
	 * 模糊搜索 条件查询
	 * 
	 * 如果需要使用多个参数 那么则需要Param 注解
	 * @param itemName
	 * @param unit
	 * @return List
	 */
	List<Item> selectByCondition(@Param("ItemName") String itemName, @Param("unit") int unit);

}
