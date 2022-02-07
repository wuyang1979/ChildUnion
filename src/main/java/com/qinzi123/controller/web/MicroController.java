package com.qinzi123.controller.web;

import com.qinzi123.controller.micro.PayController;
import com.qinzi123.service.RechargeMoneyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @title: MicroController
 * @package: com.qinzi123.controller.web
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
public class MicroController {

    private static final Logger logger = LoggerFactory.getLogger(PayController.class);

    @Autowired
    RechargeMoneyService rechargeMoneyService;

    @RequestMapping(value = "/order/callback")
    private void callback(HttpServletRequest request, HttpServletResponse response) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            //sb为微信返回的xml
            String notifyXml = sb.toString();
            logger.info(notifyXml);
            rechargeMoneyService.payBack(notifyXml);
            success(response);
        } catch (IOException e) {
            logger.error("微信回调接口失败", e);
        }
    }

    @RequestMapping(value = "/leagueOrder/callback")
    private void leagueOrderCallback(HttpServletRequest request, HttpServletResponse response) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            //sb为微信返回的xml
            String notifyXml = sb.toString();
            logger.info(notifyXml);
            rechargeMoneyService.leaguePayBack(notifyXml);
            success(response);
        } catch (IOException e) {
            logger.error("微信回调接口失败", e);
        }
    }

    private void success(HttpServletResponse response) throws IOException {
        String resXml =
                "<xml>"
                        + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>"
                        + "</xml> ";
        logger.info("返回成功记录给微信, {}", resXml);
        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }
}
