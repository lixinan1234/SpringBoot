package com.example.springweb.dao.impl;

import com.example.springweb.dao.EmpDao;
import com.example.springweb.pojo.Emp;
import com.example.springweb.utils.XmlParserUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/5/29 10:35
 */

//@Component完成控制反转
//@Component//将当前类交给IOC管理容器，成为IOC容器中的bean
@Repository
public class EmpDaoA implements EmpDao {

    @Override
    public List<Emp> listEmp() {
        //1.加载并解析XML文件
        //动态的获取文件路径
        String file = this.getClass().getClassLoader().getResource("emp.xml").getFile();
        System.out.println(file);
        //parse解析Xml文件：参数1：要加载哪个文件 参数2：要往哪个对象去封装
        List<Emp> empList = XmlParserUtils.parse(file, Emp.class);

        return empList;
    }
}
