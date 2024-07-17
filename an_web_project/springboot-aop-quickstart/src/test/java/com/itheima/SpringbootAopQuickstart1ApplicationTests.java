package com.itheima;

import com.itheima.service.DeptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootAopQuickstart1ApplicationTests {
    @Autowired
    private DeptService deptService;

    @Test
    public void testAopDelete(){
        deptService.delete(4);
    }

}
