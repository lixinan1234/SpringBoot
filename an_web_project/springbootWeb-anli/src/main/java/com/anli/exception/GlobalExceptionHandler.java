package com.anli.exception;

import com.anli.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author:lixinan
 * @email:2489460735@qq.com
 * @desc:
 * @datetime: 2024/7/10 9:45
 */


/*全局异常处理器*/
    @RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(Exception.class)//捕捉所有异常
        public Result ex(Exception ex){
            ex.printStackTrace();
            return Result.error("对不起操作失败，请联系管理员");
        }
}
