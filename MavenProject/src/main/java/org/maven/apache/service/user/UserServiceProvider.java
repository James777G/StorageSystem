
package org.maven.apache.service.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.maven.apache.mapper.UserMapper;
import org.maven.apache.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Transactional
public class UserServiceProvider implements UserService{

    private UserMapper userMapper;

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 查看所有
     *
     * @return List
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    /**
     * 按ID来查询
     *
     * @param id User Id
     * @return item
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User selectById(int id) {
        return userMapper.selectById(id);
    }

    /**
     * 模糊搜索 条件查询
     * <p>
     * 如果需要使用多个参数 那么则需要Param 注解
     *
     * @param UserName username
     * @return List
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<User> selectByUsername(String UserName) {
        return userMapper.selectByUsername(UserName);
    }

    /**
     * This method adds a new item to the table in the database
     * <p>
     * 而且对于Input 这个Item，是不需要你去设置 ItemID 值的，而且如果你使用 item.getID() 是可以返回
     * 当前的ID 值的
     *
     * @param user user
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void add(User user) {
        userMapper.add(user);
    }

    /**
     * 更新一项数据的某个或者某些值
     *
     * @param user user
     * @return an integer showing how many rows are affected
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int update(User user) {
        return userMapper.update(user);
    }

    /**
     * 根据名字查询
     *
     * @param name user's name
     * @return a user
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<User> selectByName(String name) {
        return userMapper.selectByName(name);
    }

    /**
     * 根据 ID 来进行删除
     *
     * @param id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(int id) {
        userMapper.deleteById(id);
    }

    /**
     * 批量删除
     *
     * @param ids an array of id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByIds(int[] ids) {
        userMapper.deleteByIds(ids);
    }
}
