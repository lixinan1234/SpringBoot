package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.itheima.mp.domain.po.User;
import net.minidev.json.writer.UpdaterMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsert() {
        User user = new User();
        user.setId(5L);
        user.setUsername("Lucy");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Test
    void testSelectById() {
        User user = userMapper.selectById(5L);
        System.out.println("user = " + user);
    }


    @Test
    void testQueryByIds() {
        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(4L);
        user.setBalance(20000);
        userMapper.updateById(user);
    }

    @Test
    void testDeleteUser() {
        userMapper.deleteById(5L);
    }


    @Test
    void testQueryWrapper(){
        //1.构建查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .select("id","username","info","balance")//要查询的字段
                .like("username","o")//查询带o字的用户名
                .ge("balance",1000);//balance值大于等于1000的
        //2.查询
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }


    @Test
    void testUpdateQueryWrapper(){

        //1.要更新的数据
        User user = new User();
        user.setBalance(2000);
        //2.要更新的条件
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .eq("username","jack");
        //3.执行更新
        userMapper.update(user,wrapper);
    }

    @Test
    void textUpdateWrapper(){
        List<Long> ids = List.of(1L,2L,4L);
        UpdateWrapper<User> wrapper = new UpdateWrapper<User>()
                .setSql("balance = balance - 200")
                .in("id",ids);
        userMapper.update(null,wrapper);
    }

    @Test
    void testLambdaQueryWrapper(){
        //1.构建查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .select(User::getId,User::getUsername,User::getInfo,User::getBalance)//要查询的字段
                .like(User::getUsername,"o")//查询带o字的用户名
                .ge(User::getBalance,1000);//balance值大于等于1000的
        //2.查询
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void textCustomSqlUpdate(){
        //1.更新条件
        List<Long> ids = List.of(1L,2L,4L);
        int amount = 100;
        //2.定义条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .in(User::getId,ids);
        userMapper.updateBalanceByIds(wrapper,amount);
    }
}