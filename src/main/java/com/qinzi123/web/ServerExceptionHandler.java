package com.qinzi123.web;

import com.qinzi123.exception.GlobalProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @title: ServerExceptionHandler
 * @package: com.qinzi123.web
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@ControllerAdvice
public class ServerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServerExceptionHandler.class);
    private static final String GLOBAL_CODE = "FF000001";

    @ExceptionHandler(GlobalProcessException.class)
    @ResponseBody
    public Result globalExceptionHandler(GlobalProcessException e) {
        return Result.error(GLOBAL_CODE, e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Result unsuportMessageExceptionHandler(HttpRequestMethodNotSupportedException e) {
        return Result.error("FF0000F1", "不支持的请求格式");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Result missingServletRequestParameterException(MissingServletRequestParameterException e) {
        return Result.error("FF0000F2", "缺少参数");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result unkownException(Exception e) {
        logger.error("未知的后台错误", e);
        return Result.error("FFFFFFFF", "未知的后台错误");
    }
}
