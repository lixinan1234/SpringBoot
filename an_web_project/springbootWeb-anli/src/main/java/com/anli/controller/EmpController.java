package com.anli.controller;

import com.anli.anno.Log;
import com.anli.pojo.Emp;
import com.anli.pojo.PageBean;
import com.anli.pojo.Result;
import com.anli.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/6/30 11:19
 */
//员工管理控制器
@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {

    @Autowired
    private EmpService empService;


    //条件分页查询
    @GetMapping       //@RequestParam(defaultValue = "默认值")设置请求参数的默认值
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name, Short gender,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
                       ) {

        //记录日志
        log.info("分页查询，参数：{},{},{},{},{},{}", page, pageSize,name,gender,begin,end);
        //调用service业务层分页查询功能
        PageBean pageBean = empService.page(page, pageSize,name,gender,begin,end);
        //响应
        return Result.success(pageBean);
    }




    @Log
    //批量删除
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("批量删除操作，ids:{}",ids);
        empService.delete(ids);
        return Result.success();
    }


    @Log
    //新增员工
    @PostMapping
    public Result add(@RequestBody Emp emp){
        log.info("新增员工:{}",emp);
        empService.add(emp);
        return Result.success();
    }


    //根据ID查询员工信息
    @GetMapping("/{id}")
    public Result inquire(@PathVariable Integer id){
        log.info("根据ID查询员工信息：{}",id);
        Emp emp =  empService.inquire(id);
        return Result.success(emp);
    }

    @Log
    //修改员工
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("修改员工的信息：{}",emp);
        empService.update(emp);
        return Result.success();
    }



}