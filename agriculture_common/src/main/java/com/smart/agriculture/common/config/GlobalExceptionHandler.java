package com.smart.agriculture.common.config;

import com.smart.agriculture.common.result.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 自定义异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理空指针的异常
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public CommonResult<String> exceptionHandler(NullPointerException e){
        e.printStackTrace();
        return CommonResult.failed("服务器异常！");
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public CommonResult<String> exceptionHandler( Exception e){
        e.printStackTrace();
        return CommonResult.failed("服务器出现异常，请联系管理员！");
    }
}
