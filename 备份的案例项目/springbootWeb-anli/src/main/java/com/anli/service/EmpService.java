package com.anli.service;

import com.anli.pojo.Emp;
import com.anli.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

//员工业务规则
public interface EmpService {

    //分页查询
    PageBean page(Integer page, Integer pageSize,String name, Short gender,LocalDate begin,LocalDate end);

    //批量删除
    void delete(List<Integer> ids);

    //新增员工
    void add(Emp emp);

    //根据ID查询员工信息
    Emp inquire(Integer id);

    //修改员工
    void update(Emp emp);

    //登入
    Emp login(Emp emp);
}