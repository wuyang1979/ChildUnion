package com.qinzi123.exception;

/**
 * @title: Error
 * @package: com.qinzi123.exception
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface Error {

    String E_DEFAULT_SUCCESS = "00000000";

    String code();

    String msg();

}
