package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/9/3 22:15
 */

//定时任务类，定时处理订单状态
@Component//实例化,并且放到Spring容器进行管理
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    //处理超时订单
    @Scheduled(cron = "0 * * * * ? ")//每分钟触发一次
    //@Scheduled(cron = "1/5 * * * * ? ")
    public void processTimeoutOrder(){
        log.info("定时处理超时订单：{}", LocalDateTime.now());
        //select * from order where status = ? and order_time < (当前时间 - 15分钟)

        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);//当前时间-15分钟
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, time);

        if(ordersList !=null && ordersList.size() > 0){
            for (Orders orders : ordersList) {
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelReason("订单超时，自动取消");
                orders.setCancelTime(LocalDateTime.now());
                orderMapper.update(orders);
            }
        }
    }


    //处理一直处于派送中状态的这种订单
    @Scheduled(cron = "0 0 1 * * ?")//每天凌晨1点触发一次
    //@Scheduled(cron = "0/5 * * * * ? ")
    public void processDeliveryOrder(){
        log.info("定时处理处于派送中的订单：{}",LocalDateTime.now());

        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);

        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, time);

        if(ordersList !=null && ordersList.size() > 0){
            for (Orders orders : ordersList) {
                orders.setStatus(Orders.COMPLETED);
                orders.setCancelReason("订单已超时，自动取消");
                orders.setCancelTime(LocalDateTime.now());
                orderMapper.update(orders);
            }
        }

    }


}