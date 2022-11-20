package org.maven.apache.mapper;

import java.util.List;

import org.maven.apache.user.User;

/**
 * 这个文件的路径不要改， 因为目前这个文件在 Compile 过后会和下面Resource里的 UserMapper.xml 在同一个目录下
 * 这样子 Mybatis 内部就会在 UserMapper.xml 里面找同一个文件名且同一个ID 所对应的 SQL 执行命令
 * 比如说 selectAll
 * 
 * @author james
 *
 */
public interface UserMapper {
	
	
	/**
	 * 获取表中全部数据
	 * @return 一个User List
	 */
	List<User> selectAll();

}
