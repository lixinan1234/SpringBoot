package com.example.springweb.controller;

import com.example.springweb.pojo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/5/28 23:16
 */

@RestController
public class ResponseController {
    @RequestMapping("/hello")
    public Result hello(){
        System.out.println("Hello World~");
       // return new Result(1,"success","Hello World~");
        return Result.success("Hello World~");
    }
}
