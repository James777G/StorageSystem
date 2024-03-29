package org.maven.apache.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.maven.apache.dateTransaction.DateTransaction;
import org.maven.apache.item.Item;
import org.springframework.stereotype.Component;

/**
 * 这个 Interface 支持模糊搜索
 * @author james
 *
 */
@Component
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

	Item selectByItemName(String itemName);
	
	/**
	 * 模糊搜索 条件查询
	 * 
	 * 如果需要使用多个参数 那么则需要Param 注解
	 * @param itemName
	 * @param unit
	 * @return List
	 */
	List<Item> selectByCondition(@Param("ItemName") String itemName, @Param("unit") int unit);
	
	
	/**
	 * This method adds a new item to the table in the database
	 * 
	 * 而且对于Input 这个Item，是不需要你去设置 ItemID 值的，而且如果你使用 item.getID() 是可以返回
	 * 当前的ID 值的
	 * @param item
	 */
	void add(Item item);
	
	
	
	/**
	 * 更新一项数据的某个或者某些值
	 * @param item
	 * @return
	 */
	int update(Item item);


	/**
	 * 根据 ID 来进行删除
	 */
	void deleteById(int id);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteByIds(int[] ids);

	/**
	 * separate the item with no order in page asked
	 * @param pageNumberCal
	 * @param pageSize
	 * @return
	 */
	List<Item> pageAskedNOOrder(@Param("pageNumberCal") int pageNumberCal , @Param("pageSize") int pageSize);

	/**
	 * separate the item with unit by ascending order
	 * @param pageNumberCal
	 * @param pageSize
	 * @return
	 */
	List<Item> pageAskedUnitAscend(@Param("pageNumberCal") int pageNumberCal ,@Param("pageSize") int pageSize);
	/**
	 * separate the item with unit by descending order
	 * @param pageNumberCal
	 * @param pageSize
	 * @return
	 */
	List<Item> pageAskedUnitDescend(@Param("pageNumberCal") int pageNumberCal ,@Param("pageSize") int pageSize);
	/**
	 * separate the item with ItemID by ascending order
	 * @param pageNumberCal
	 * @param pageSize
	 * @return
	 */
	List<Item> pageAskedItemIDAscend(@Param("pageNumberCal") int pageNumberCal ,@Param("pageSize") int pageSize);
	/**
	 * separate the item with ItemID by descending order
	 * @param pageNumberCal
	 * @param pageSize
	 * @return
	 */
	List<Item> pageAskedItemIDDescend(@Param("pageNumberCal") int pageNumberCal ,@Param("pageSize") int pageSize);



}
