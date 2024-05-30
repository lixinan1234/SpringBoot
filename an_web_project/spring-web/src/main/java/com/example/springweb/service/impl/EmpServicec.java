package com.example.springweb.service.impl;

import com.example.springweb.dao.EmpDao;
import com.example.springweb.pojo.Emp;
import com.example.springweb.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/5/29 10:39
 */

//@Component//将当前类交给IOC管理容器，成为IOC容器中的bean
@Service
public class EmpServicec implements EmpService {

    @Autowired //运行时，IOC容器会提供该类型的bean对象，并赋值给该变量 - 依赖注入
    private EmpDao empDao;

    @Override
    public List<Emp> listEmp() {
        //1.调用dao,获取数据
        List<Emp> empList = empDao.listEmp();

        //2. 对数据进行转换处理
        empList.stream().forEach(emp -> {
            //处理 gender 1: 男, 2: 女
            String gender = emp.getGender();
            if("1".equals(gender)){
                emp.setGender("男d");
            }else if("2".equals(gender)){
                emp.setGender("女d");
            }
            //处理job - 1: 讲师, 2: 班主任 , 3: 就业指导
            String job = emp.getJob();
            if(job.equals("1")){
                emp.setJob("讲师");
            }else if(job.equals("2")){
                emp.setJob("班主任");
            }else if(job.equals("3")){
                emp.setJob("就业指导");
            }
        });

        return empList;
    }




}
