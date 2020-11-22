package com.wxy.wjl.testspringboot2.global;

import com.alibaba.fastjson.JSON;
import com.wxy.wjl.testspringboot2.sqlcheck.model.ResultT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理器
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理所有Exception 返回json数据
     * @param e
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(value=Exception.class)
    @ResponseBody
    String handleException(Exception e, HttpServletRequest request, HttpServletResponse response){
        log.error("url:{}, msg:{}",request.getRequestURL(), e.getMessage(), e);
        response.setStatus(400);
        return JSON.toJSONString(new ResultT("Exception"));
    }

}
