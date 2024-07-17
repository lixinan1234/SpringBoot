package com.anli.service.impl;

import com.anli.mapper.DeptMapper;
import com.anli.mapper.EmpMapper;
import com.anli.pojo.Dept;
import com.anli.pojo.DeptLog;
import com.anli.service.DeptLogService;
import com.anli.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/6/30 11:22
 */


//部门业务实现类
@Slf4j
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private DeptLogService deptLogService;

    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }


    @Transactional(rollbackFor = Exception.class)  //Spring事务管理
    @Override
    public void delete(Integer id) {

        try {
            deptMapper.deleteById(id);//根据部门ID删除部门

            empMapper.deleteByDeptId(id);//根据部门ID来删除该部门下的员工
        } finally {
            DeptLog deptLog = new DeptLog();
            deptLog.setCreateTime(LocalDateTime.now());
            deptLog.setDescription("执行了解散部门的操作，此次解散的是"+id+"号部门");
            deptLogService.insert(deptLog);
        }
    }

    @Override
    public void add(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());

        deptMapper.insert(dept);
    }

    @Override
    public Dept getByID(Integer id) {
        Dept dept = deptMapper.getByID(id);
        return dept;
    }


    @Override
    public void update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }


}
