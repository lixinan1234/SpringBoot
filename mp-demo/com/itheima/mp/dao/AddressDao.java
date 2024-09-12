package com.itheima.mp.dao;

import lombok.extern.slf4j.Slf4j;
import com.itheima.mp.entity.Address;
import com.itheima.mp.mapper.AddressMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * (address)数据DAO
 *
 * @author kancy
 * @since 2024-09-12 09:08:26
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@Repository
public class AddressDao extends ServiceImpl<AddressMapper, Address> {

}