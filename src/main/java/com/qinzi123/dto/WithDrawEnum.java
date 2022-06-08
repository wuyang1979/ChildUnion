package com.qinzi123.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum WithDrawEnum {
    SUCCESS(0, "提现成功"),
    FAIL(400, "提现失败，余额小于提现金额"),
    HANDLE_FRE(5000, "操作太频繁"),
    NO_AUTH(5001, "没有该接口权限"),
    AMOUNT_LIMIT(5002, "金额超限"),
    PARAM_ERROR(5003, "参数错误"),
    OPENID_ERROR(5004, "Openid错误"),
    SEND_FAILED(5005, "付款错误"),
    NOTENOUGH(5006, "余额不足"),
    SYSTEMERROR(5007, "系统繁忙，请稍后再试。"),
    NAME_MISMATCH(5008, "姓名校验出错"),
    SIGN_ERROR(5009, "签名错误"),
    XML_ERROR(5010, "发送内容出错"),
    FATAL_ERROR(5011, "两次发送参数不一致"),
    FREQ_LIMIT(5012, "超过频率限制，请稍后再试。"),
    MONEY_LIMIT(5013, "已经达到今日付款总额上限/已达到付款给此用户额度上限"),
    CA_ERROR(5014, "商户证书校验出错"),
    V2_ACCOUNT_SIMPLE_BAN(5015, "无法给未实名用户付款"),
    PARAM_IS_NOT_UTF8(5016, "发送参数中包含不规范字符"),
    SENDNUM_LIMIT(5017, "该用户今日付款次数超过限制, 如有需要请进入【微信支付商户平台-产品中心-付款到零钱-产品设置】进行修改"),
    RECV_ACCOUNT_NOT_ALLOWED(5018, "收款账户不在收款账户列表"),
    PAY_CHANNEL_NOT_ALLOWED(5019, "本商户号未配置此功能"),
    SEND_MONEY_LIMIT(5020, "已达到今日商户付款额度上限"),
    RECEIVED_MONEY_LIMIT(5021, "已达到今日付款给此用户额度上限");
    private int code;
    private String msg;

    public static WithDrawEnum fromText(String text) {
        if (text != null) {
            for (WithDrawEnum b : WithDrawEnum.values()) {
                if (text.equalsIgnoreCase(b.name())) {
                    return b;
                }
            }
        }
        return null;
    }
}
