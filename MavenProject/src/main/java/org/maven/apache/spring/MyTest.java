package org.maven.apache.spring;

import org.maven.apache.item.Item;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.UserMapper;
import org.maven.apache.user.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.Test;

import java.util.List;

public class MyTest {


    @Test
    public void test1(){
        ApplicationContext context = new AnnotationConfigApplicationContext(MyBatisAutoConfiguration.class);
        ItemMapper itemMapper = (ItemMapper) (context.getBean("itemMapper"));
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        User user = userMapper.selectById(3);
        List<Item> items = itemMapper.selectAll();
        System.out.println(user);
        System.out.println(items);

    }
}
