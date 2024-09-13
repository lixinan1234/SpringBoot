package com.itheima.mp.service;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.po.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IUserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    void testSaveUser(){
        User user = new User();
        //user.setId(5L);
        user.setUsername("廖乐乐");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        //user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setInfo(UserInfo.of(24,"英语老师","female"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userService.save(user);
    }

    @Test
    void testQuery(){
        List<User> users = userService.listByIds(List.of(1L, 2L, 4L));
        users.forEach(System.out::println);
    }


    @Test
    void testSaveBatch() {
        // 准备10万条数据
        List<User> list = new ArrayList<>(1000);
        long b = System.currentTimeMillis();
        for (int i = 1; i <= 100000; i++) {
            list.add(buildUser(i));
            // 每1000条批量插入一次
            if (i % 1000 == 0) {
                userService.saveBatch(list);
                list.clear();
            }
        }
        long e = System.currentTimeMillis();
        System.out.println("耗时：" + (e - b));
    }


    private User buildUser(int i) {
        User user = new User();
        user.setUsername("user_" + i);
        user.setPassword("123");
        user.setPhone("" + (18688190000L + i));
        user.setBalance(2000);
        user.setInfo(UserInfo.of(24,"英语老师","female"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(user.getCreateTime());
        return user;
    }

    @Test
    void testPageQuery(){
        int pageNo = 1, pageSize=2;

        //1.准备分类条件
        Page<User> page = Page.of(pageNo, pageSize);//设置页码和页数
        //1.2.排序条件
        page.addOrder(new OrderItem().setColumn("balance").setAsc(true));//设置balance的降序排序
        page.addOrder(new OrderItem().setColumn("id").setAsc(true));
        //2.分页查询
        Page<User> p = userService.page(page);

        //3.解析
        long total = p.getTotal();//一共有几条数据
        System.out.println("total = " + total);
        long pages = p.getPages();
        System.out.println("pages" + pages);
        List<User> users = p.getRecords();
        users.forEach(System.out::println);

    }
}