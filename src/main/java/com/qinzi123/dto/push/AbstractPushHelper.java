package com.qinzi123.dto.push;

/**
 * @title: AbstractPushHelper
 * @package: com.qinzi123.dto.push
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public abstract class AbstractPushHelper {

    protected Keyword getKeyword(String value) {
        return new Keyword(value);
    }

}
