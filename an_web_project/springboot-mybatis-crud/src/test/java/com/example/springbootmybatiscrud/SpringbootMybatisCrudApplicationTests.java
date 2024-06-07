package com.example.springbootmybatiscrud;

import com.example.mapper.EmpMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
