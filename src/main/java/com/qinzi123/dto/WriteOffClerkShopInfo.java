package com.qinzi123.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @title: WriteOffClerkShopInfo
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/4/13 0013 11:41
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Data
public class WriteOffClerkShopInfo {

    @JSONField(name = "shop_id")
    private int shopId;
}
