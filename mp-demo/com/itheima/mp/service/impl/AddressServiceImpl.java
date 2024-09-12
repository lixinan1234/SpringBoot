package com.itheima.mp.service.impl;

import com.itheima.mp.dao.AddressDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.itheima.mp.service.AddressService;
import org.springframework.stereotype.Service;

/**
 * 服务接口实现
 *
 * @author kancy
 * @since 2024-09-12 09:08:26
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {
    private final AddressDao addressDao;

}