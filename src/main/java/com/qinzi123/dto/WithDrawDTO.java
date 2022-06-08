package com.qinzi123.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @title: WithDrawDTO
 * @package: com.qinzi123.dto
 * @projectName: trunk
 * @description: 提现的业务实现dto
 * @author: jie.yuan
 * @date: 2022/5/5 0005 15:48
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class WithDrawDTO implements Serializable {
    @ApiModelProperty(value = "申请商户号的appid或商户号绑定的appid")
    private String mch_appid;
    @ApiModelProperty(value = "微信支付分配的商户号")
    private String mchid;
    @ApiModelProperty(value = "随机字符串，不长于32位")
    private String nonce_str;
    @ApiModelProperty(value = "签名")
    private String sign;
    @ApiModelProperty(value = "商户订单号，需保持唯一性")
    private String partner_trade_no;
    @ApiModelProperty(value = "openid是微信用户在公众号appid下的唯一用户标识（appid不同，则获取到的openid就不同），可用于永久标记一个用户。")
    private String openid;
    @ApiModelProperty(value = "NO_CHECK：不校验真实姓名,FORCE_CHECK：强校验真实姓名")
    private String check_name;
    @ApiModelProperty(value = "收款用户真实姓名。如果check_name设置为FORCE_CHECK，则必填用户真实姓名,如需电子回单，需要传入收款用户姓名")
    private String re_user_name;
    @ApiModelProperty(value = "付款金额，单位为分")
    private Integer amount;
    @ApiModelProperty(value = "付款备注，必填。注意：备注中的敏感词会被转成字符*")
    private String desc;
}

