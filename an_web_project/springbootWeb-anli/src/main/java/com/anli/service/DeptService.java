package com.anli.service;

import com.anli.pojo.Dept;
import com.anli.pojo.DeptLog;

import java.util.List;

//部门业务规则
public interface DeptService {

    /*查询全部部门数据*/
    List<Dept> list();


    /*删除部门信息*/
    void delete(Integer id);


    /*添加部门信息*/
    void add(Dept dept);





    void update(Dept dept);

    Dept getByID(Integer id);


}
