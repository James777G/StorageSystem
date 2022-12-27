package org.maven.apache.spring;


import java.util.List;


import org.maven.apache.MyLauncher;

import org.maven.apache.item.Item;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.UserMapper;
import org.maven.apache.service.item.ItemService;
import org.maven.apache.service.mail.MailService;
import org.maven.apache.service.user.UserService;
import org.maven.apache.user.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.Test;


import java.util.List;
import java.util.concurrent.ExecutorService;

public class MyTest {


    @Test
    public void test1(){
        ApplicationContext context = new AnnotationConfigApplicationContext(MyBatisAutoConfiguration.class);
        ItemMapper itemMapper = (ItemMapper) (context.getBean("itemMapper"));
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        User user = userMapper.selectById(3);
        User user2 = new User();
        user2.setPassword("123123134");
        user2.setUsername("Jerry2223333");
        user2.setName("asd");
        List<Item> items = itemMapper.selectAll();
        userMapper.add(user2);
        System.out.println(user);
        System.out.println(items);

    }

    @Test
    public void test2(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        ItemService itemService = context.getBean("itemService", ItemService.class);
        List<Item> items = itemService.selectAll();
        System.out.println(items);

    }

    @Test
    public void test3(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        UserService userService = context.getBean("userService", UserService.class);
        List<User> users = userService.selectAll();
        System.out.println(users);

    }

    @Test
    public void test4(){
        ExecutorService threadPoolExecutor = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);

        for(int i = 0; i < 14; i++){
            threadPoolExecutor.execute(() -> System.out.println("123123"));
        }
    }

    @Test
    public void test5() throws InterruptedException {
        MailService mailService = MyLauncher.context.getBean("mailService", MailService.class);
        mailService.sendEmail("jamesgong0719@gmail.com", "123456999");
        System.out.println("woshisheiasd");
        System.out.println("woshisheiasd");
        System.out.println("woshisheiasd");
        System.out.println("woshisheiasd");
        System.out.println("woshisheiasd");
        System.out.println("woshisheiasd");
        Thread.sleep(5000);
    }

}
