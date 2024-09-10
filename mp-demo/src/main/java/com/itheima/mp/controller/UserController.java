package com.itheima.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/9/10 17:59
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService iUserService;

    @ApiOperation("新增用户接口")
    @PostMapping
    public void saveUser(@RequestBody UserFormDTO userFormDTO){
        //1.把DTO拷贝到po
        User user =  BeanUtil.copyProperties(userFormDTO, User.class);
        //2.新增
        iUserService.save(user);
    }

    @ApiOperation("删除用户接口")
    @DeleteMapping("/{id}")
    public void deleteUserById(@ApiParam("用户id") @PathVariable("id") Long id){
        iUserService.removeById(id);
    }

    @ApiOperation("跟据id查询用户接口")
    @GetMapping("/{id}")
    public UserVO queryUserById(@ApiParam("用户id") @PathVariable("id") Long id){
        //1.查询用户PO
        User user = iUserService.getById(id);
        //2.把PO拷贝VO
        return BeanUtil.copyProperties(user,UserVO.class);
    }


    @ApiOperation("跟据id批量查询用户接口")
    @GetMapping()
    public List<UserVO> queryUserByIds(@ApiParam("用户id") @RequestParam("id") List<Long> ids){
        //1.查询用户PO
        List<User> users = iUserService.listByIds(ids);
        //2.把PO拷贝VO
        return BeanUtil.copyToList(users,UserVO.class);
    }

    @ApiOperation("扣减用户余额接口")
    @PutMapping("/{id}/deduction/{money}")
    public void deductionMoneyById(
            @ApiParam("用户id") @PathVariable("id") Long id,
            @PathVariable("扣减的金额") Integer money){
        iUserService.deductBalance(id,money);
    }


}