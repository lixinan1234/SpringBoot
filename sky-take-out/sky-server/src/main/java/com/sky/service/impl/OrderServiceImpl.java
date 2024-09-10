package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.*;
import com.sky.entity.*;
import com.sky.exception.OrderBusinessException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.*;
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.utils.HttpClientUtil;
import com.sky.utils.WeChatPayUtil;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import com.sky.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/8/27 15:58
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private WeChatPayUtil weChatPayUtil;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WebSocketServer webSocketServer;

    /*@Value("${sky.shop.address}")
    private String shopAddress;
    @Value("${sky.baidu.ak}")
    private String ak;*/



    //用户下单
    @Transactional
    @Override
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {

        //处理各种业务异常（地址薄为空，购物车数据为空）
        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
        if(addressBook == null){
            //抛出异常
            throw new SetmealEnableFailedException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        //checkOutOfRange(addressBook.getCityName() + addressBook.getDistrictName() + addressBook.getDetail());


        //查看当前购物车数据
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart =  new ShoppingCart();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);
        if(shoppingCartList == null || shoppingCartList.size() == 0){
            throw new SetmealEnableFailedException(MessageConstant.SHOPPING_CART_IS_NULL);
        }


        //向订单表插入1条数据
        Orders order = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO,order);//对象属性拷贝
        order.setOrderTime(LocalDateTime.now());//设置下单时间
        order.setPayStatus(Orders.UN_PAID);//设置支付状态
        order.setStatus(Orders.PENDING_PAYMENT);//设置订单状态
        order.setNumber(String.valueOf(System.currentTimeMillis()));//转字符串，然后设置时间戳
        order.setPhone(addressBook.getPhone());//获取并插入手机号
        order.setConsignee(addressBook.getConsignee());//获取并插入收货人
        order.setAddress(addressBook.getDetail());
        order.setUserId(userId);//设置用户ID
        orderMapper.insert(order);//插入数据


        List<OrderDetail> orderDetailList = new ArrayList<>();
        //向订单明细表插入n条数据
        for (ShoppingCart cart : shoppingCartList) {
            OrderDetail orderDetail = new OrderDetail();//订单明细
            BeanUtils.copyProperties(cart,orderDetail);
            orderDetail.setOrderId(order.getId());//设置当前订单明细关联的订单ID
            orderDetailList.add(orderDetail);
        }
        orderDetailMapper.insertBatch(orderDetailList);

        //清空当前用户购物车数据
        shoppingCartMapper.deleteByUserId(userId);

        //封装VO返回结果
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(order.getId())
                .orderNumber(order.getNumber())
                .orderTime(order.getOrderTime())
                .orderAmount(order.getAmount())
                .build();

        return orderSubmitVO;
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
   public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
       // 当前登录用户id
       Long userId = BaseContext.getCurrentId();
       User user = userMapper.getById(userId);

       paySuccess(ordersPaymentDTO.getOrderNumber());

       String orderNumber = ordersPaymentDTO.getOrderNumber(); //订单号
       Long id = orderMapper.getorderId(orderNumber);//根据订单号查主键

       JSONObject jsonObject = new JSONObject();//本来没有2
       jsonObject.put("code", "ORDERPAID"); //本来没有3
       OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
       vo.setPackageStr(jsonObject.getString("package"));
       //为替代微信支付成功后的数据库订单状态更新，多定义一个方法进行修改
       Integer OrderPaidStatus = Orders.PAID; //支付状态，已支付
       Integer OrderStatus = Orders.TO_BE_CONFIRMED; //订单状态，待接单
       //发现没有将支付时间 check_out属性赋值，所以在这里更新
       LocalDateTime check_out_time = LocalDateTime.now();

       orderMapper.updateStatus(OrderStatus, OrderPaidStatus, check_out_time, id);
       return vo;  //  修改支付方法中的代码
   }

       /**
        * 支付成功，修改订单状态
        *
        * @param outTradeNo
        */
       public void paySuccess(String outTradeNo) {

           // 根据订单号查询订单
           Orders ordersDB = orderMapper.getByNumber(outTradeNo);

           // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
           Orders orders = Orders.builder()
                   .id(ordersDB.getId())
                   .status(Orders.TO_BE_CONFIRMED)
                   .payStatus(Orders.PAID)
                   .checkoutTime(LocalDateTime.now())
                   .build();

           orderMapper.update(orders);

           //通过websocket向客户端浏览器推送消息 type orderId content
           Map map = new HashMap();
           map.put("type",1);//1表示来单提醒 2表示客户催单
           map.put("orderId",ordersDB.getId());
           map.put("content","订单号" + outTradeNo);

           String json = JSON.toJSONString(map);
           webSocketServer.sendToAllClient(json);

       }

       //历史订单查询
      @Override
      public PageResult pageQuery4User(int pageNum, int pageSize, Integer status) {
           //分页设置
          PageHelper.startPage(pageNum,pageSize);

          OrdersPageQueryDTO ordersPageQueryDTO = new OrdersPageQueryDTO();
          ordersPageQueryDTO.setUserId(BaseContext.getCurrentId());
          ordersPageQueryDTO.setStatus(status);

          //分页条件查询
          Page<Orders> page =  orderMapper.pageQuery(ordersPageQueryDTO);

          List<OrderVO> list = new ArrayList();

          //查询出订单明细，并封装入OrderVO进行封装
          if(page != null && page.getTotal() >0){
              for (Orders orders : page) {
                  Long orderId = orders.getId();//订单id

                  // 查询订单明细
                  List<OrderDetail> orderDetails = orderDetailMapper.getByOrderId(orderId);

                  OrderVO orderVO = new OrderVO();
                  BeanUtils.copyProperties(orders, orderVO);
                  orderVO.setOrderDetailList(orderDetails);

                  list.add(orderVO);
              }
          }
          return new PageResult(page.getTotal(), list);
      }


      //查询订单详情
    @Override
    public OrderVO details(Long id) {
         //跟据id查询订单
         Orders orders = orderMapper.getById(id);

        // 查询该订单对应的菜品/套餐明细
        List<OrderDetail> orderDetailList =  orderDetailMapper.getByOrderId(orders.getId());

        // 将该订单及其详情封装到OrderVO并返回
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orders,orderVO);
        orderVO.setOrderDetailList(orderDetailList);

        return orderVO;
    }


    //取消订单
    @Override
    public void userCancelById(Long id) throws Exception {
        // 根据id查询订单
        Orders ordersOB =  orderMapper.getById(id);

        // 校验订单是否存在
        if(ordersOB == null){
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }

        //订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
        if(ordersOB.getStatus() > 2){
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        Orders orders = new Orders();
        orders.setId(ordersOB.getId());
        // 订单处于待接单状态下取消，需要进行退款
        if(ordersOB.getStatus().equals(Orders.TO_BE_CONFIRMED)){
            /*//调用微信支付退款接口
            weChatPayUtil.refund(
                    ordersOB.getNumber(), //商户订单号
                    ordersOB.getNumber(), //商户退款单号
                    new BigDecimal(0.01),//退款金额，单位 元
                    new BigDecimal(0.01));//原订单金额*/

            //支付状态修改为 退款
            orders.setPayStatus(Orders.REFUND);
        }

        // 更新订单状态、取消原因、取消时间
        orders.setStatus(Orders.CANCELLED);
        orders.setCancelReason("用户取消");
        orders.setCancelTime(LocalDateTime.now());
        orderMapper.update(orders);
    }


    //再来一单
    @Override
    public void repetition(Long id) {
        // 查询当前用户id
        Long userId = BaseContext.getCurrentId();

        // 根据订单id查询当前订单详情
        List<OrderDetail> orderDetailList =  orderDetailMapper.getByOrderId(id);

        // 将订单详情对象转换为购物车对象
        List<ShoppingCart> shoppingCartList = orderDetailList.stream().map(x ->{
            ShoppingCart shoppingCart = new ShoppingCart();

            // 将原订单详情里面的菜品信息重新复制到购物车对象中
            BeanUtils.copyProperties(x,shoppingCart,"id");
            shoppingCart.setUserId(userId);
            shoppingCart.setCreateTime(LocalDateTime.now());

            return shoppingCart;

        }).collect(Collectors.toList());

        // 将购物车对象批量添加到数据库
        shoppingCartMapper.insertBatch(shoppingCartList);
    }


    //订单搜索
    @Override
    public PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
           PageHelper.startPage(ordersPageQueryDTO.getPage(),ordersPageQueryDTO.getPageSize());
           Page<Orders> page = orderMapper.pageQuery(ordersPageQueryDTO);

        //部分订单状态，需要额外返回订单菜品信息，将Orders转化为OrderVO
        List<OrderVO> orderVOList = getOrderVOList(page);

        return new PageResult(page.getTotal(), orderVOList);
    }


    //各个状态的订单数量统计
    @Override
    public OrderStatisticsVO statistics() {
        // 根据状态，分别查询出待接单、待派送、派送中的订单数量
        Integer toBeConfirmed = orderMapper.countStatus(Orders.TO_BE_CONFIRMED);
        Integer confirmed = orderMapper.countStatus(Orders.CONFIRMED);
        Integer deliveryInProgress = orderMapper.countStatus(Orders.DELIVERY_IN_PROGRESS);

        // 将查询出的数据封装到orderStatisticsVO中响应
        OrderStatisticsVO orderStatisticsVO = new OrderStatisticsVO();
        orderStatisticsVO.setToBeConfirmed(toBeConfirmed);
        orderStatisticsVO.setConfirmed(confirmed);
        orderStatisticsVO.setDeliveryInProgress(deliveryInProgress);
        return orderStatisticsVO;
    }


    //接单
    @Override
    public void confirm(OrdersConfirmDTO ordersConfirmDTO) {
        // 创建一个 Orders 对象，使用 builder 模式
           Orders orders = Orders.builder()
                   .id(ordersConfirmDTO.getId())// 设置订单 ID
                   .status(Orders.CONFIRMED)// 设置订单状态为已接单
                   .build();// 构建 Orders 对象
           orderMapper.update(orders);// 更新订单信息
    }

    //取消订单
    @Override
    public void cancel(OrdersCancelDTO ordersCancelDTO) throws Exception {
           //跟据id查询订单
        Orders ordersDB = orderMapper.getById(ordersCancelDTO.getId());

        //支付状态
        Integer payStatus = ordersDB.getPayStatus();
        if(payStatus == 1){
           /* //用户已支付，需要退款
            String refund = weChatPayUtil.refund(
                    ordersDB.getNumber(),
                    ordersDB.getNumber(),
                    new BigDecimal(0.01),
                    new BigDecimal(0.01));
            log.info("申请退款：{}", refund);*/
        }


        //管理端取消订单需要退款，根据订单id更新订单状态、取消原因、取消时间
        Orders orders = new Orders();
        orders.setId(ordersCancelDTO.getId());
        orders.setStatus(Orders.CANCELLED);
        orders.setCancelReason(ordersCancelDTO.getCancelReason());
        orders.setCancelTime(LocalDateTime.now());
        orderMapper.update(orders);
    }

    //订单派送
    @Override
    public void delivery(Long id) {
        // 根据id查询订单
        Orders ordersDB = orderMapper.getById(id);

        // 校验订单是否存在，并且状态为3
        if(ordersDB == null && ordersDB.getStatus().equals(Orders.CONFIRMED)){
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        Orders orders = new Orders();
        orders.setId(ordersDB.getId());

        // 更新订单状态,状态转为派送中
        orders.setStatus(Orders.DELIVERY_IN_PROGRESS);

        orderMapper.update(orders);
    }


    //完成订单
    @Override
    public void complete(Long id) {
        //跟据id查询订单
        Orders ordersDB =  orderMapper.getById(id);

        // 校验订单是否存在，并且状态为4
        if(ordersDB == null && ordersDB.getStatus().equals(Orders.DELIVERY_IN_PROGRESS)){
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        Orders orders = new Orders();
        orders.setId(ordersDB.getId());//设置订单号Id

        // 更新订单状态,状态转为完成
        orders.setStatus(Orders.COMPLETED);
        orders.setDeliveryTime(LocalDateTime.now());

        orderMapper.update(orders);
    }


    //催单
    @Override
    public void reminder(Long id) {
        // 查询订单是否存在
        Orders orders = orderMapper.getById(id);
        if(orders == null){
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }

        //基于WebSocket实现催单
        Map map = new HashMap();
        map.put("type",2);
        map.put("orderId",orders.getId());
        map.put("content","订单号" + orders.getNumber());

        String json = JSON.toJSONString(map);
        webSocketServer.sendToAllClient(json);
    }


    /**
     * 检查客户的收货地址是否超出配送范围
     * @param
     */
    /*private void checkOutOfRange(String address) {
        Map map = new HashMap();
        map.put("address",shopAddress);
        map.put("output","json");
        map.put("ak",ak);

        //获取店铺的经纬度坐标
        String shopCoordinate = HttpClientUtil.doGet("https://api.map.baidu.com/geocoding/v3", map);

        JSONObject jsonObject = JSON.parseObject(shopCoordinate);
        if(!jsonObject.getString("status").equals("0")){
            throw new OrderBusinessException("店铺地址解析失败");
        }

        //数据解析
        JSONObject location = jsonObject.getJSONObject("result").getJSONObject("location");
        String lat = location.getString("lat");
        String lng = location.getString("lng");
        //店铺经纬度坐标
        String shopLngLat = lat + "," + lng;

        map.put("address",address);
        //获取用户收货地址的经纬度坐标
        String userCoordinate = HttpClientUtil.doGet("https://api.map.baidu.com/geocoding/v3", map);

        jsonObject = JSON.parseObject(userCoordinate);
        if(!jsonObject.getString("status").equals("0")){
            throw new OrderBusinessException("收货地址解析失败");
        }

        //数据解析
        location = jsonObject.getJSONObject("result").getJSONObject("location");
        lat = location.getString("lat");
        lng = location.getString("lng");
        //用户收货地址经纬度坐标
        String userLngLat = lat + "," + lng;

        map.put("origin",shopLngLat);
        map.put("destination",userLngLat);
        map.put("steps_info","0");

        //路线规划
        String json = HttpClientUtil.doGet("https://api.map.baidu.com/directionlite/v1/driving", map);

        jsonObject = JSON.parseObject(json);
        if(!jsonObject.getString("status").equals("0")){
            throw new OrderBusinessException("配送路线规划失败");
        }

        //数据解析
        JSONObject result = jsonObject.getJSONObject("result");
        JSONArray jsonArray = (JSONArray) result.get("routes");
        Integer distance = (Integer) ((JSONObject) jsonArray.get(0)).get("distance");

        if(distance > 5000){
            //配送距离超过5000米
            throw new OrderBusinessException("超出配送范围");
        }
    }*/




    private List<OrderVO> getOrderVOList(Page<Orders> page) {
        // 需要返回订单菜品信息，自定义OrderVO响应结果
        List<OrderVO> orderVOList = new ArrayList<>();

        List<Orders> ordersList = page.getResult();
        if (!CollectionUtils.isEmpty(ordersList)) {
            for (Orders orders : ordersList) {
                // 将共同字段复制到OrderVO
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orders, orderVO);
                String orderDishes = getOrderDishesStr(orders);

                // 将订单菜品信息封装到orderVO中，并添加到orderVOList
                orderVO.setOrderDishes(orderDishes);
                orderVOList.add(orderVO);
            }
        }
        return orderVOList;
    }

    /**
     * 根据订单id获取菜品信息字符串
     *
     * @param orders
     * @return
     */
    private String getOrderDishesStr(Orders orders) {
        // 查询订单菜品详情信息（订单中的菜品和数量）
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(orders.getId());

        // 将每一条订单菜品信息拼接为字符串（格式：宫保鸡丁*3；）
        List<String> orderDishList = orderDetailList.stream().map(x -> {
            String orderDish = x.getName() + "*" + x.getNumber() + ";";
            return orderDish;
        }).collect(Collectors.toList());
        // 将该订单对应的所有菜品信息拼接在一起
        return String.join("", orderDishList);
    }





}