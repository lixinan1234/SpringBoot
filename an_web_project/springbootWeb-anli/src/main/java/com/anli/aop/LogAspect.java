package com.anli.aop;

import com.alibaba.fastjson.JSONObject;
import com.anli.mapper.OperateLogMapper;
import com.anli.pojo.OperateLog;
import com.anli.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpOptions;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Target;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/7/12 11:06
 */

@Slf4j
@Component
@Aspect//切面类
public class LogAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(com.anli.anno.Log)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {

        //操作ID(当前登入员工的ID)
        //获取请求头中的jwt令牌，解析令牌
        String jwt =  request.getHeader("token");
        Map<String, Object> claims = JwtUtils.parseJWT(jwt);
        Integer operateUser = (Integer) claims.get("id");

        //操作时间
        LocalDateTime operateTime = LocalDateTime.now();

        //操作类名
        String className = joinPoint.getTarget().getClass().getName();

        //操作方法名
        String methodName = joinPoint.getSignature().getName();

        //操作方法参数
        Object[] args = joinPoint.getArgs();
        String methodParams = Arrays.toString(args);


        //记录方法执行开始时间
        long begin = System.currentTimeMillis();
        //调用原始方法运行
        Object result =   joinPoint.proceed();
        //记录方法执行结束时间
        long end = System.currentTimeMillis();


        //操作方法返回值
        String returnValue = JSONObject.toJSONString(result);


        //操作耗时
        //计算方法执行耗时
        Long costTime = end - begin;




        //记录操作日志
        OperateLog operateLog = new OperateLog(null,operateUser,operateTime,className,methodName,methodParams,returnValue,costTime);
        operateLogMapper.insert(operateLog);

        log.info("AOP记录操作日志：{}",operateLog);

        return result;
    }

}
