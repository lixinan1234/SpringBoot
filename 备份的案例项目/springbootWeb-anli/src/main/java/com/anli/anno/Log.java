package com.anli.anno;


import org.aspectj.lang.annotation.Around;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



//这是一个自定义注解

@Retention(RetentionPolicy.RUNTIME)//当前注解什么时候生效：运行时有效
@Target(ElementType.METHOD)//当前注解能作用在哪些地方：自定义（方法上）
public @interface Log {

}
