package com.qinzi123.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @title: ResponseBodyHandler
 * @package: com.qinzi123.web
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@ControllerAdvice
public class ResponseBodyHandler implements ResponseBodyAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ResponseBodyHandler.class);

    private static final String MICRO_PROGRAM_PATH = "com.qinzi123.controller.micro";

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter,
                                  MediaType mediaType, Class c, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        //logger.info("统一返回结构体给客户端" + body.toString());
        boolean hasContainMicroResponse = methodParameter.getDeclaringClass().getName().
                contains(MICRO_PROGRAM_PATH);
        //logger.info("小程序才需要统一返回结构体, 是否小程序请求? " + hasContainMicroResponse);
        return hasContainMicroResponse ? (body instanceof Result ? body : Result.ok(body)) : body;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        //return returnType.hasMethodAnnotation(ResponseBody.class);
        return true;
    }

}
