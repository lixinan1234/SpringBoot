package com.example.springbootmybatiscrud;

import com.example.mapper.EmpMapper;
import com.example.pojo.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SpringbootMybatisCrudApplicationTests {

    @Autowired
    private EmpMapper empMapper;


    @Test
    public void testDelete(){
        int delete = empMapper.delete(16);
        if(delete == 1){
            System.out.println("删除成功！");
        }else{
            System.out.println("");
        }
    }

    @Test
    public void testInsert(){

        Emp emp = new Emp();
        emp.setUsername("Tom2");
        emp.setName("汤姆2");
        emp.setImage("1.jpg");
        emp.setGender((short)1);
        emp.setJob((short)1);
        emp.setEntrydate(LocalDate.of(2000,1,1));
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        emp.setDeptId(1);

        //执行新增员工信息操作
        empMapper.insert(emp);
        System.out.println(emp.getId());
    }


    //更新员工
    @Test
    public void textupdate(){
        Emp emp = new Emp();
        emp.setId(18);
        emp.setUsername("lixinan");
        emp.setName("李新安");
        emp.setImage("1.jpg");
        emp.setGender((short)1);
        emp.setJob((short)1);
        emp.setEntrydate(LocalDate.of(2000,1,1));
        emp.setUpdateTime(LocalDateTime.now());
        emp.setDeptId(1);
        //执行更新员工操作
        empMapper.update(emp);
    }

    //根据ID查询员工
    @Test
    public void testGetById(){
        Emp emp = empMapper.getById(18);
        System.out.println(emp);

    }

    //根据条件查询员工
    @Test
    public void testList(){
//        List<Emp> empList = empMapper.list("张", (short) 1, LocalDate.of
//                (2010, 1, 1), LocalDate.of(2020, 1, 1));
        //动态条件查询
        List<Emp> empList = empMapper.list(null, (short) 1, null, null);
        System.out.println(empList);
    }



    //动态更新员工- 更新ID为18的员工 username 更新为 Tom11,name更新为汤姆111，gender更新为2
    //更新员工
    @Test
    public void textupdate2(){
        Emp emp = new Emp();
        emp.setId(15);
        emp.setUsername("Tom999");
        emp.setName("汤姆999");
        emp.setGender((short)2);
        emp.setUpdateTime(LocalDateTime.now());
        //执行更新员工操作
        empMapper.update2(emp);
    }


    //批量删除员工 -  13，14，15
    @Test
    public void testDeleteByIds(){
        List<Integer> ids = Arrays.asList(13, 14, 15);
        empMapper.deleteByIds(ids);
    }
}
