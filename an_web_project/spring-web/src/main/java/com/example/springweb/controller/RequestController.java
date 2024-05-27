package com.example.springweb.controller;

import com.example.springweb.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/5/27 22:25
 */

/*测试请求参数接收*/

//简单参数请求
@RestController
public class RequestController {

    //1.简单参数请求
    //springboot方式接收
    @RequestMapping("/simpleParam")//@RequestMapping注解：指定请求路径
    //@RequestParam注解 方法形参名称和请求参数名称不匹配，可通过此注解完成映射
    public String simpleParam(@RequestParam(name="name") String username, Integer age){
        System.out.println(username+":"+age);
        return "ok";
    }



    //2.实体参数
    @RequestMapping("/simplePojo")
    public String simplePojo(User user){
        System.out.println(user);
        return "ok实体参数";
    }

    //2.复杂实体参数
    @RequestMapping("/complexPojo")
    public String complexPojo(User user){
        System.out.println(user);
        return "ok发杂实体参数";
    }

}
