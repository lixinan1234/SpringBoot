package com.example.springweb.controller;

import com.example.springweb.pojo.Result;
import com.example.springweb.pojo.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/5/27 22:25
 */

/*测试请求参数接收*/

//简单参数请求
@RestController//一个使用@RestController注解的类会处理HTTP请求，
// 根据请求的内容生成相应的数据并返回给客户端。
// 这个类通常会使用@RequestMapping注解来指定处理请求的URL路径和HTTP方法。
public class RequestController {

    //1.简单参数请求
    //springboot方式接收
    @RequestMapping("/simpleParam")//@RequestMapping注解：指定请求路径
    //@RequestParam注解 方法形参名称和请求参数名称不匹配，可通过此注解完成映射
    public String simpleParam(@RequestParam(name = "name") String username, Integer age) {
        System.out.println(username + ":" + age);
        return "ok";
    }

    //2.实体参数
    @RequestMapping("/simplePojo")
    public String simplePojo(User user) {
        System.out.println(user);
        return "ok实体参数";
    }

    //3.复杂实体参数
    @RequestMapping("/complexPojo")
    public String complexPojo(User user) {
        System.out.println(user);
        return "ok发杂实体参数";
    }

    //4.数组请求
    @RequestMapping("/ArrayParam")
    public String ArrayParam(String[] hobby) {
        System.out.println(Arrays.toString(hobby));
        return "ok数组集合请求";
    }


    //5.集合请求
    @RequestMapping("/listParam")
    //这里要加个@RequestParam注解，通过@RequestParam绑定参数关系
    public String listParam(@RequestParam List<String> hobby) {
        System.out.println(hobby);
        return "ok集合请求成功";
    }


    //6.日期时间参数
    @RequestMapping("/dataParam")
    public String dataParam(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime updateTime) {
        System.out.println(updateTime);
        return "ok请求到日期时间参数";
    }

    //7.join参数
    @RequestMapping("/jsonParam")
    //需要加@RequestBody注解，将json请求数据封装实体对象当中
    public String jsonParam(@RequestBody User user) {
        System.out.println(user);
        return "okJson参数请求成功";
    }

    //8.1路径参数
    @RequestMapping("/path/{id}")//使用{…}来标识该路径参数
    //需要使用@PathVariable获取路径参数
    public String pathParam(@PathVariable Integer id) {
        System.out.println(id);
        return "ok路径参数请求成功";
    }

    //8.2多个路径参数
    @RequestMapping("/path/{id}/{name}")//使用{…}来标识该路径参数
    //需要使用@PathVariable获取路径参数
    public String pathParam2(@PathVariable Integer id, @PathVariable String name) {
        System.out.println(id);
        System.out.println(name);
        return "ok路径参数请求成功";
    }


}
