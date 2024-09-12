package com.itheima.mp.service;

import com.itheima.mp.domain.po.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressServiceTest {
    @Autowired
    private  AddressService addressService;

    @Test
    void testLogicDelete(){
        //删除
        addressService.removeById(59L);

        //2.查询
        Address address = addressService.getById(59L);
        System.out.println("address = " + address);
    }


}