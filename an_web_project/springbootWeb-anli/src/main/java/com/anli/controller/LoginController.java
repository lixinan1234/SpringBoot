package com.anli.controller;

import com.anli.pojo.Emp;
import com.anli.pojo.Result;
import com.anli.service.EmpService;
import com.anli.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/7/8 9:02
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private  EmpService empService;


    //登入
    @PostMapping("/login")
    public Result login(@RequestBody Emp emp){
        log.info("登入的账号和密码：{}",emp);
        Emp e = empService.login(emp);

        //登入令牌，生成令牌，下发令牌
        if(e != null){
            Map<String, Object> claims  = new HashMap<>();
            claims.put("id",e.getId());
            claims.put("name",e.getName());
            claims.put("username",e.getUsername());

            String jwt = JwtUtils.generateJwt(claims); //jwt包含了当前登入的员工信息
            return Result.success(jwt);
        }

        //登入失败，返回错误信息
        return Result.error("员工信息不存在");
    }
}
