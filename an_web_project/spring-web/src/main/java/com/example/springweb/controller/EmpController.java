package com.example.springweb.controller;

import com.example.springweb.pojo.Emp;
import com.example.springweb.pojo.Result;
import com.example.springweb.service.EmpService;
import com.example.springweb.service.impl.EmpServiceA;
import com.example.springweb.utils.XmlParserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/5/29 9:03
 */

@RestController
public class EmpController {

    //方法1
    @Autowired //运行时，IOC容器会提供该类型的bean对象，并赋值给该变量 - 依赖注入
    //方法2
    /*@Resource(name = "empServiceB")*/

    private EmpService empService;
    @RequestMapping("/listEmp")
    public Result list() {
        //1.调用Service，获取数据
        List<Emp> empList = empService.listEmp();

        //3. 响应数据
        return Result.success(empList);
    }
}

/*    @RequestMapping("/listEmp")
    public Result list(){
        //1.加载并解析XML文件
        String file = this.getClass().getClassLoader().getResource("emp.xml").getFile();
        System.out.println(file);
        List<Emp> empList = XmlParserUtils.parse(file, Emp.class);

        //2. 对数据进行转换处理
        empList.stream().forEach(emp -> {
            //处理 gender 1: 男, 2: 女
            String gender = emp.getGender();
            if("1".equals(gender)){
                emp.setGender("男");
            }else if("2".equals(gender)){
                emp.setGender("女");
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

        //3. 响应数据
        return Result.success(empList);
    }*/
