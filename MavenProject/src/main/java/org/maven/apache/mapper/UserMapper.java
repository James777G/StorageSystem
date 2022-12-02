package org.maven.apache.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.maven.apache.user.User;
import org.springframework.stereotype.Component;

/**
 * 这个 Interface 支持模糊搜索
 * @author james
 *
 */
@Component
public interface UserMapper {

	/**
	 * 查看所有
	 * @return List
	 */
	List<User> selectAll();

	/**
	 * 按ID来查询
	 * @param id
	 * @return item
	 */
	User selectById(int id);

	/**
	 * 模糊搜索 条件查询
	 *
	 * 如果需要使用多个参数 那么则需要Param 注解
	 * @param UserName username
	 * @return List
	 */
	List<User> selectByUsername(String UserName);


	/**
	 * This method adds a new item to the table in the database
	 *
	 * 而且对于Input 这个Item，是不需要你去设置 ItemID 值的，而且如果你使用 item.getID() 是可以返回
	 * 当前的ID 值的
	 * @param user user
	 */
	void add(User user);



	/**
	 * 更新一项数据的某个或者某些值
	 * @param user user
	 * @return
	 */
	int update(User user);


	List<User> selectByName(String name);


	/**
	 * 根据 ID 来进行删除
	 */
	void deleteById(int id);

	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteByIds(int[] ids);

}
