package com.qinzi123.controller.web;

import com.qinzi123.controller.micro.PayController;
import com.qinzi123.dao.ProductDao;
import com.qinzi123.dao.UserOrderDao;
import com.qinzi123.dto.TemplateData;
import com.qinzi123.dto.WxMssVo;
import com.qinzi123.service.RechargeMoneyService;
import com.qinzi123.util.DateUtils;
import com.qinzi123.util.Utils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    RechargeMoneyService rechargeMoneyService;

    @Resource
    private ProductDao productDao;

    @Resource
    private UserOrderDao userOrderDao;

    @RequestMapping(value = "/order/callback")
    private void callback(HttpServletRequest request, HttpServletResponse response) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
            String line;
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

    @RequestMapping(value = "/userOrder/callback")
    private void userOrderCallback(HttpServletRequest request, HttpServletResponse response) {
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
            Map resultMap = rechargeMoneyService.clientEndPayBack(notifyXml);

            int orderId = Integer.parseInt(resultMap.get("attach").toString());
            Map<String, Object> productMap = productDao.getProductInfoByOrderId(orderId);
            Map<String, Object> orderMap = userOrderDao.getOrderInfoById(orderId);
            int orderSaleType = Integer.parseInt(orderMap.get("order_sale_type").toString());
            if (orderSaleType == 1) {
                int primaryDistributionShopId = Integer.parseInt(orderMap.get("primary_distribution_shop_id").toString());
                String buyerOpenId = orderMap.get("open_id").toString();
                String buyerUserId = userOrderDao.getUserIdByOpenId(buyerOpenId);
                if (primaryDistributionShopId == -1) {
                    //查询分销者是否是分销合伙人
                    String primaryDistributionOpenId = orderMap.get("primary_distribution_open_id").toString();
                    String superiorId = userOrderDao.getUserIdByOpenId(primaryDistributionOpenId);
                    String primaryDistributionPhone = userOrderDao.getPhoneByOpenId(primaryDistributionOpenId);
                    List<LinkedHashMap> distributionPartnerList = userOrderDao.getDistributionPartnerListByPhone(primaryDistributionPhone);
                    if (distributionPartnerList.size() > 0) {
                        //是分销合伙人
                        //C端客户分销，且分销者是分销合伙人，添加客户级联
                        //先判断下单人是否已经存在客户级联
                        List<LinkedHashMap> cascadeList = userOrderDao.getCascadeListByUserId(buyerUserId);
                        if (cascadeList.size() == 0) {
                            //若已存在级联关系则不添加级联记录
                            //客户上级userId
                            Map<String, Object> cascadeParamsMap = new HashedMap();
                            cascadeParamsMap.put("superior_type", 1);
                            cascadeParamsMap.put("superior_id", superiorId);
                            cascadeParamsMap.put("subordinate_id", buyerUserId);
                            cascadeParamsMap.put("create_time", DateUtils.getAccurateDate());
                            userOrderDao.addCascade(cascadeParamsMap);
                        }
                    }
                } else if (primaryDistributionShopId != 0) {
                    //商户分销
                    //B端客户分销，添加客户级联
                    //先判断下单人是否已经存在客户级联
                    List<LinkedHashMap> cascadeList = userOrderDao.getCascadeListByUserId(buyerUserId);
                    if (cascadeList.size() == 0) {
                        //若已存在级联关系则不添加级联记录
                        //客户上级userId
                        Map<String, Object> cascadeParamsMap = new HashedMap();
                        cascadeParamsMap.put("superior_type", 0);
                        cascadeParamsMap.put("superior_id", primaryDistributionShopId);
                        cascadeParamsMap.put("subordinate_id", buyerUserId);
                        cascadeParamsMap.put("create_time", DateUtils.getAccurateDate());
                        userOrderDao.addCascade(cascadeParamsMap);
                    }
                }
            }

            int orderType = Integer.parseInt(orderMap.get("order_type").toString());
            String statusName;
            if (orderType == 0) {
                statusName = "待收货";
            } else {
                statusName = "待核销";
            }
            //订阅消息推送
            pushWxOrderTemplateMessage(orderMap.get("open_id").toString(), orderMap.get("order_no").toString(), productMap.get("name").toString(), statusName);

            success(response);
        } catch (IOException e) {
            logger.error("微信回调接口失败", e);
        }
    }

    @RequestMapping(value = "/userOrder/distributionPartnerCallback")
    private void userOrderDistributionPartnerCallback(HttpServletRequest request, HttpServletResponse response) {
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
            rechargeMoneyService.clientEndDistributionPartnerPayBack(notifyXml);

            success(response);
        } catch (IOException e) {
            logger.error("微信回调接口失败", e);
        }
    }

    @RequestMapping(value = "/userOrder/distributionPartnerToBeLeaderCallback")
    private void userOrderDistributionPartnerToBeLeaderCallback(HttpServletRequest request, HttpServletResponse response) {
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
            rechargeMoneyService.clientEndDistributionPartnerToBeLeaderPayBack(notifyXml);

            success(response);
        } catch (IOException e) {
            logger.error("微信回调接口失败", e);
        }
    }

    /**
     * @return java.lang.String
     * @Author: jie.yuan
     * @Description: 微信推送c端订单订阅消息
     * @Date: 2022/02/28 0015 11:43
     * @Param [openId, orderNo, productName,statusName]
     **/
    public String pushWxOrderTemplateMessage(String openId, String orderNo, String productName, String statusName) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + Utils.getWxChengZhangGoAccessToken();
        //拼接推送的模版
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setTouser(openId);
        //订阅消息模板id
        wxMssVo.setTemplate_id("prBHTyQaDjkxSc7uLKoWjRY4OvleCNtB_Lvo-lebEXI");
        wxMssVo.setPage("pages/order/list");

        Map<String, TemplateData> m = new HashMap<>(3);
        m.put("character_string1", new TemplateData(orderNo));
        m.put("thing2", new TemplateData(productName));
        m.put("phrase3", new TemplateData(statusName));
        m.put("time4", new TemplateData(DateUtils.getAccurateDate()));
        wxMssVo.setData(m);
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(url, wxMssVo, String.class);
        return responseEntity.getBody();
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
