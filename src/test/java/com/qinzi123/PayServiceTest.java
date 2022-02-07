package com.qinzi123;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @title: PayServiceTest
 * @projectName: trunk
 * @description: 亲子企服
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PayServiceTest {

    private static final String xml = "<xml><appid><![CDATA[wx2f3e800fce3fd438]]></appid><attach><![CDATA[19]]></attach><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1527081391]]></mch_id><nonce_str><![CDATA[34BbO9DObOYkCAwHGSJv89lZHcCCCdwh]]></nonce_str><openid><![CDATA[otofq0BpRR9cH0pWXfBzTx-TQ-bM]]></openid><out_trade_no><![CDATA[20190320143114]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[9FBCC740570367E1E73E85FD7E9008C5]]></sign><time_end><![CDATA[20190320143135]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4200000247201903200758435621]]></transaction_id></xml>";

    @Test
    @Ignore
    public void testUpdateStatus() {
        //Map map = payService.payBack(xml);
        //System.out.println(map.toString());
    }
}
