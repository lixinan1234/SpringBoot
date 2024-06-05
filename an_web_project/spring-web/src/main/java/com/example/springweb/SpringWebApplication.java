package com.example.springweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


//启动类 --启动springboot工程的
//要是有其他包的需要加上注解：@ComponentScan({"com.example.springweb","其他包名"})
@SpringBootApplication//默认扫描当前包及其子包
public class SpringWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebApplication.class, args);
    }

}
