package com.anli.controller;

import com.anli.anno.Log;
import com.anli.pojo.Dept;
import com.anli.pojo.Result;
import com.anli.service.DeptService;
import com.anli.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.LoggingPermission;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/6/30 11:18
 */
//部门管理控制器
@Slf4j//日志记录对象(第二种方法)
@RestController
@RequestMapping("/depts")
public class DeptController {

    @Autowired
    private DeptService deptService;



    /*查询部门数据*/
    //日志记录对象(第一种方法)
    //private static Logger log = LoggerFactory.getLogger(DeptController.class);
    //原始方法
    //@RequestMapping("/depts")
    //使用Get方式请求
    @GetMapping
    public Result list() {
        //尽量不用sout输出日志，用Logger
        //System.out.println("查询全部部门数据");
        //调用日志记录对象
        log.info("查询全部部门数据");

        //调用service查询部门数据
        List<Dept> deptList = deptService.list();

        return Result.success(deptList);
    }


    @Log
    /*删除部门数据*/
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){//@PathVariable获取路径变量的Id然后绑定id参数
        log.info("根据ID删除部门：{}",id);
        //调用Service删除部门数据
        deptService.delete(id);
        return Result.success();
    }



    @Log
    /*添加部门数据*/
    @PostMapping
    public Result add(@RequestBody Dept dept){//要封装到对象当中就要加@RequestBody
        log.info("新增部门：{}",dept);
        //调用Service添加部门数据
        deptService.add(dept);

        return Result.success();
    }


    @Log
    /*修改部门数据（根据id查询部门）*/
    @GetMapping("/{id}")
    public Result getByID(@PathVariable Integer id) {
        log.info("获取部门ID:{}",id);
        Dept dept = deptService.getByID(id);
        return Result.success(dept);
    }
    /*修改部门*/
    @PutMapping
    public Result update(@RequestBody Dept dept) {//要封装到对象当中就要加@RequestBody
        log.info("修改部门:{}",dept);
        deptService.update(dept);
        return Result.success();
    }

}
