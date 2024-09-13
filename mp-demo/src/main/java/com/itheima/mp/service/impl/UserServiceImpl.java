package com.itheima.mp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.po.Address;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.AddressVO;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.enums.UserStatus;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/9/10 17:14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {



    @Override
    @Transactional
    public void deductBalance(Long id, Integer money) {
        //1.查询用户
        User user = getById(id);
        //2.校验用户状态
        if (user == null || user.getStatus() == UserStatus.FROZEN) {
            throw new RuntimeException("用户状态异常!");
        }
        //3.校验余额是否充足
        if(user.getBalance() <money){
            throw new RuntimeException("用户余额不足!");
        }
        //4.扣减余额 update user set balance = balance - #{money} where  id = #{id}
        int remainBalance = user.getBalance() - money;

        lambdaUpdate()
                .set(User::getBalance,remainBalance)//set是修改 把User里扣减余额
                .set(remainBalance <=0,User::getStatus,UserStatus.FROZEN)//要是大于等于0，就修改状态
                .eq(User::getId,id)//条件是用户id
                .eq(User::getBalance,user.getBalance())//用户余额必须等于查到的余额（乐观锁（更加安全））
                .update();//后面一定一定要加update！！！！！！不然不执行
    }

    @Override
    public List<User> queryUsers(String name, Integer status, Integer minBalance, Integer maxBalance) {

        return lambdaQuery()//调用得重新编译 在maven->Lifecycle->clean(双击)
                // 条件成立，就对name做模糊查询
                .like(name != null,User::getUsername,name)//list模糊
                .eq(status != null,User::getStatus,status)//eq比较
                .gt(minBalance != null,User::getBalance,minBalance)//gt大于
                .lt(maxBalance != null,User::getBalance,maxBalance)//lt小于
                //list查多个，one查一个，page做分页，count做统计
                .list();
    }

    @Override
    public UserVO queryUserAndAddressById(Long id) {
        //1.查询用户
        User user = getById(id);
        if (user == null || user.getStatus() == UserStatus.FROZEN) {
            throw new RuntimeException("用户状态异常!");
        }
        //2.查询地址
        List<Address> addresses = Db.lambdaQuery(Address.class)
                .eq(Address::getUserId, id)
                .list();

        //3.封装VO
        //3.1 转User的Po为VO
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        //3.1 转地址VO
        if(CollUtil.isNotEmpty(addresses)){
            List<AddressVO> addressVOS = BeanUtil.copyToList(addresses, AddressVO.class);
            userVO.setAddresses(addressVOS);
        }
        return userVO;
    }

    @Override
    public List<UserVO> queryUserAndAddressByIds(List<Long> ids) {

        //1.查询用户
        List<User> users = listByIds(ids);
        if(CollUtil.isNotEmpty(users)){//如果用户为空
            return Collections.emptyList();
        }
        //2.查询地址
        //2.1获取用户id集合
        List<Long> userIds = users.stream().map(User::getId).collect(Collectors.toList());
        //2.2根据用户id查询地址
        List<Address> addresses = Db.lambdaQuery(Address.class).in(Address::getUserId, userIds).list();
        //2.3 转换地址Vo
        List<AddressVO> addressVOList = BeanUtil.copyToList(addresses, AddressVO.class);
        //2.4用户地址集合分组处理，相同用户的放入一个集合（组）中
        Map<Long, List<AddressVO>> addressMap = new HashMap<>(0);
        if(CollUtil.isNotEmpty(addressVOList)){
            addressMap = addressVOList.stream().collect(Collectors.groupingBy(AddressVO::getUserId));
        }

        //3.转Vo返回
        List<UserVO> list = new ArrayList<>(users.size());
        for (User user : users) {
            //3.1转换User的Po为VO
            UserVO vo = BeanUtil.copyProperties(user, UserVO.class);
            list.add(vo);
            //3.2转换地址Vo
            vo.setAddresses(addressMap.get(user.getId()));
        }
        return list;
    }

    //跟据条件分页查询用户
    @Override
    public PageDTO<UserVO> queryUsersPage(UserQuery query) {
        String name = query.getName();
        Integer status = query.getStatus();

        //1.构建查询条件
        Page<User> page = query.toMpPageDefaultSortByCreateTime();

        //2.分页查询
        Page<User> p =  lambdaQuery()
                // 条件成立，就对name做模糊查询
                .like(name != null,User::getUsername,name)//list模糊
                .eq(status != null,User::getStatus,status)//eq比较
                .page(page);
        //3.封装VO结果
        return PageDTO.of(p,user -> {
            //1.拷贝基础属性
            UserVO vo = BeanUtil.copyProperties(user, UserVO.class);
            //2.处理特殊逻辑
            vo.setUsername(vo.getUsername().substring(0,vo.getUsername().length()-2) + "**");

            return vo;
        });
    }
}
