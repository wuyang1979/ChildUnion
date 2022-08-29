package com.qinzi123.service.impl;

import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.qinzi123.dao.CampaignDao;
import com.qinzi123.dao.CardDao;
import com.qinzi123.dao.UserOrderDao;
import com.qinzi123.exception.GlobalProcessException;
import com.qinzi123.service.RechargeMoneyService;
import com.qinzi123.service.ScoreService;
import com.qinzi123.util.DateUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: RechargeMoneyServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class RechargeMoneyServiceImpl extends AbstractWechatMiniProgramService implements RechargeMoneyService {

    private static final String TRADE_TYPE = "JSAPI";

    private static final String KEY = "Qinzi01234567890Qinzi01234567890";//555006250b4c09247ec02edce69f6a2d

    private Logger log = LoggerFactory.getLogger(RechargeMoneyServiceImpl.class);

    @Resource
    CampaignDao campaignDao;

    @Resource
    UserOrderDao userOrderDao;

    @Resource
    CardDao cardDao;

    @Resource
    ScoreService scoreService;

    private RestTemplate localRestTemplate;

    /**
     * 兑换积分的比率
     */
    @Value("${application.pay.rate}")
    private int rate;

    public RestTemplate getLocalRestTemplate() {
        if (localRestTemplate == null) {
            localRestTemplate = new RestTemplate();
            localRestTemplate.getMessageConverters().add(0,
                    new StringHttpMessageConverter(Charset.forName("UTF-8")));
        }
        return localRestTemplate;
    }

    /**
     * 构造支付信息
     *
     * @param map 前端传入的订单相关信息
     * @return 构建好的Map结构体
     * @throws Exception
     */
    Map generatePay(Map map) throws Exception {
        String nonceStr = WXPayUtil.generateNonceStr();
        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("appid", getAppId());
        packageParams.put("attach", map.get("id").toString());
        packageParams.put("body", map.get("body").toString());
        packageParams.put("mch_id", getMchId());
        packageParams.put("nonce_str", nonceStr);
        packageParams.put("notify_url", NOTIFY_URL);//支付成功后的回调地址
        packageParams.put("openid", map.get("openid").toString());//支付方式
        packageParams.put("out_trade_no", map.get("order").toString());//商户订单号
        packageParams.put("sign_type", WXPayConstants.MD5);
        packageParams.put("spbill_create_ip", getLocalIp());
        packageParams.put("total_fee", ServiceHelper.convertFee(map.get("total").toString()));//支付金额，这边需要转成字符串类型，否则后面的签名会失败
        packageParams.put("trade_type", TRADE_TYPE);//支付方式
        packageParams.put("sign", WXPayUtil.generateSignature(packageParams, KEY));
        log.info("构造的支付信息:" + map.toString());
        return packageParams;
    }

    /**
     * 构造支付信息
     *
     * @param map 前端传入的订单相关信息
     * @return 构建好的Map结构体
     * @throws Exception
     */
    Map generateLeaguePay(Map map) throws Exception {
        String nonceStr = WXPayUtil.generateNonceStr();
        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("appid", getAppId());
        packageParams.put("attach", map.get("id").toString());
        packageParams.put("body", map.get("body").toString());
        packageParams.put("mch_id", getMchId());
        packageParams.put("nonce_str", nonceStr);
        packageParams.put("notify_url", LEAGUE_NOTIFY_URL);//支付成功后的回调地址
        packageParams.put("openid", map.get("openid").toString());//支付方式
        packageParams.put("out_trade_no", map.get("order").toString());//商户订单号
        packageParams.put("sign_type", WXPayConstants.MD5);
        packageParams.put("spbill_create_ip", getLocalIp());
        packageParams.put("total_fee", ServiceHelper.convertFee(map.get("total").toString()));//支付金额，这边需要转成字符串类型，否则后面的签名会失败
        packageParams.put("trade_type", TRADE_TYPE);//支付方式
        packageParams.put("sign", WXPayUtil.generateSignature(packageParams, KEY));
        log.info("构造的支付信息:" + map.toString());
        return packageParams;
    }

    /**
     * 构造签名的结构体，返回给前端
     *
     * @param nonceStr 随机字符串
     * @param prepayId 预支付Id
     * @return 结构体
     * @throws Exception
     */
    Map generateSign(String nonceStr, String prepayId) throws Exception {
        Map returnClientMap = new HashMap<>();

        returnClientMap.put("appId", getAppId());
        returnClientMap.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
        returnClientMap.put("nonceStr", nonceStr);
        returnClientMap.put("package", String.format("prepay_id=%s", prepayId));
        returnClientMap.put("signType", WXPayConstants.MD5);
        returnClientMap.put("paySign", WXPayUtil.generateSignature(returnClientMap, KEY));

        log.info("构造的支付信息:" + returnClientMap.toString());
        return returnClientMap;
    }

    /**
     * 解析支付返回结构体
     *
     * @param result
     * @return
     */
    String parseResult4Id(String result) throws Exception {
        Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
        log.info("解析后的结果 {}", result.toString());
        String return_code = resultMap.get("return_code");
        String prepayId = "-1";
        if (StringUtils.isNotBlank(return_code) && return_code.equals("SUCCESS")) {
            String return_msg = resultMap.get("return_msg");
            if (StringUtils.isBlank(return_msg) || return_msg.equals("OK")) {
                prepayId = resultMap.get("prepay_id").toString();
            }
        }
        if (prepayId.equals("-1")) throw new GlobalProcessException("预支付流程失败");
        return prepayId;
    }

    /**
     * 成为会员接口
     *
     * @param map
     * @return
     */
    @Override
    public Map toLeaguer(Map map) {
        map.put("membershipTime", DateUtils.getAccurateDate());
        map.put("expirationTime", DateUtils.nextYear(1));
        int result = cardDao.toLeaguer(map);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return resultMap;
    }

    /**
     * 会员到期接口
     *
     * @param map
     * @return
     */
    @Override
    public Map membershipExpiration(Map map) {
        int result = cardDao.membershipExpiration(map);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return resultMap;
    }

    /**
     * 成为企业会员接口
     *
     * @param map
     * @return
     */
    @Override
    public Map toEnterpriseLeaguer(Map map) {
        map.put("membershipTime", DateUtils.getAccurateDate());
        map.put("expirationTime", DateUtils.nextYear(1));
        int result = cardDao.toEnterpriseLeaguer(map);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return resultMap;
    }

    /**
     * 成为黄金企业会员接口
     *
     * @param map
     * @return
     */
    @Override
    public Map toGoldEnterpriseLeaguer(Map map) {
        map.put("membershipTime", DateUtils.getAccurateDate());
        map.put("expirationTime", DateUtils.nextYear(1));
        int result = cardDao.toGoldEnterpriseLeaguer(map);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);
        return resultMap;
    }

    /**
     * 预支付接口
     *
     * @param map
     * @return
     */
    @Override
    public Map prepay(Map map) {
        try {
            Map params = generatePay(checkUser(map));
            String xml = WXPayUtil.mapToXml(params);
            log.info("预支付的 xml 结构体 {}", xml);
            String resultString = getLocalRestTemplate().postForObject(PREPAY_URL, xml, String.class);
            log.info("预订单的返回结果：{}", resultString);
            return generateSign(params.get("nonce_str").toString(), parseResult4Id(resultString));
        } catch (Exception e) {
            throw new GlobalProcessException("预支付流程失败", e);
        }
    }

    /**
     * 预支付接口
     *
     * @param map
     * @return
     */
    @Override
    public Map leaguePrepay(Map map) {
        try {
            Map params = generateLeaguePay(checkUser(map));
            String xml = WXPayUtil.mapToXml(params);
            log.info("预支付的 xml 结构体 {}", xml);
            String resultString = getLocalRestTemplate().postForObject(PREPAY_URL, xml, String.class);
            log.info("预订单的返回结果：{}", resultString);
            return generateSign(params.get("nonce_str").toString(), parseResult4Id(resultString));
        } catch (Exception e) {
            throw new GlobalProcessException("预支付流程失败", e);
        }
    }

    private int addPayment(String id, String total_fee, String orderNo, String result) {
        Map pay = new HashMap<>();
        pay.put("orderId", id);
        pay.put("orderNo", orderNo);
        pay.put("payment", total_fee);
        pay.put("message", result);
        int addId = campaignDao.addPayment(pay);
        log.info("增加订单{} 的支付记录 {}, 支付金额 {}", id, addId, total_fee);
        return addId;
    }

    private int addClientEndPayment(String id, String total_fee, String orderNo, String result) {
        Map pay = new HashMap<>();
        pay.put("orderId", id);
        pay.put("orderNo", orderNo);
        pay.put("payment", total_fee);
        pay.put("message", result);
        pay.put("createTime", DateUtils.getAccurateDate());
        int addId = campaignDao.addClientEndPayment(pay);
        log.info("增加订单{} 的支付记录 {}, 支付金额 {}", id, addId, total_fee);
        return addId;
    }

    private int updateOrder(String id) {
        Map order = new HashMap<>();
        order.put("id", id);
        int updateId = campaignDao.updateOrder(order);
        log.info("更新订单{} 的支付结果", id);
        return updateId;
    }

    private int updateClientEndOrder(String id) {
        Map order = new HashMap<>();
        order.put("id", id);
        order.put("payTime", DateUtils.getAccurateDate());
        int updateId = userOrderDao.updateClientEndOrder(order);
        log.info("更新订单{} 的支付结果", id);
        return updateId;
    }

    private int updateClientEndDistributionPartnerOrder(String id) {
        Map order = new HashMap<>();
        order.put("id", id);
        order.put("payTime", DateUtils.getAccurateDate());
        int updateId = userOrderDao.updateClientEndDistributionPartnerOrder(order);
        log.info("更新订单{} 的支付结果", id);
        return updateId;
    }

    private int updateProductInfoById(String id) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("orderId", id);
        Map<String, Object> orderMap = userOrderDao.getOrderInfoByOrderId(paramMap);
        return userOrderDao.updateProductInfoById(orderMap);
    }

    private int addScore(String openid, String total_fee) {
        int card = getIdByOpenId(openid);
        int score = ServiceHelper.scoreByRate(Integer.parseInt(total_fee), rate);
        int addId = scoreService.payAddScore(card, score);
        log.info("增加用户 {} 的积分兑换记录 {}, 增加积分 {}", card, addId, score);
        return addId;
    }

    @Override
    public Map payBack(String result) {
        try {
            log.info("微信回调函数入口 {}", result);
            Map callBackMap = WXPayUtil.xmlToMap(result);
            String result_code = callBackMap.get("result_code").toString();
            boolean isPaySuccess = StringUtils.isNotBlank(result_code) && result_code.equals("SUCCESS");
            if (!isPaySuccess) throw new GlobalProcessException("支付失败");

            String id = callBackMap.get("attach").toString();
            String total_fee = callBackMap.get("total_fee").toString();
            String orderNo = callBackMap.get("out_trade_no").toString();
            String openId = callBackMap.get("openid").toString();

            addPayment(id, total_fee, orderNo, result);
            updateOrder(id);
            addScore(openId, total_fee);

            return callBackMap;
        } catch (Exception e) {
            throw new GlobalProcessException("回调接口失败", e.getMessage());
        }
    }

    @Override
    public Map leaguePayBack(String result) {
        try {
            log.info("微信回调函数入口 {}", result);
            Map callBackMap = WXPayUtil.xmlToMap(result);
            String result_code = callBackMap.get("result_code").toString();
            boolean isPaySuccess = StringUtils.isNotBlank(result_code) && result_code.equals("SUCCESS");
            if (!isPaySuccess) throw new GlobalProcessException("支付失败");

            String id = callBackMap.get("attach").toString();
            String total_fee = callBackMap.get("total_fee").toString();
            String orderNo = callBackMap.get("out_trade_no").toString();
            String openId = callBackMap.get("openid").toString();

            addPayment(id, total_fee, orderNo, result);
            updateOrder(id);

            return callBackMap;
        } catch (Exception e) {
            throw new GlobalProcessException("回调接口失败", e.getMessage());
        }
    }

    @Override
    public Map clientEndPayBack(String result) {
        try {
            log.info("微信回调函数入口 {}", result);
            Map callBackMap = WXPayUtil.xmlToMap(result);
            String result_code = callBackMap.get("result_code").toString();
            boolean isPaySuccess = StringUtils.isNotBlank(result_code) && result_code.equals("SUCCESS");
            if (!isPaySuccess) throw new GlobalProcessException("支付失败");

            String id = callBackMap.get("attach").toString();
            String total_fee = callBackMap.get("total_fee").toString();
            String orderNo = callBackMap.get("out_trade_no").toString();
            String openId = callBackMap.get("openid").toString();

            addClientEndPayment(id, total_fee, orderNo, result);
            updateClientEndOrder(id);
            updateProductInfoById(id);

            return callBackMap;
        } catch (Exception e) {
            throw new GlobalProcessException("回调接口失败", e.getMessage());
        }
    }

    @Override
    public Map clientEndDistributionPartnerPayBack(String result) {
        try {
            log.info("微信回调函数入口 {}", result);
            Map callBackMap = WXPayUtil.xmlToMap(result);
            String result_code = callBackMap.get("result_code").toString();
            boolean isPaySuccess = StringUtils.isNotBlank(result_code) && result_code.equals("SUCCESS");
            if (!isPaySuccess) throw new GlobalProcessException("支付失败");

            String id = callBackMap.get("attach").toString();
            String userId = userOrderDao.getUserIdByDistributionRecordId(id);
//            String total_fee = callBackMap.get("total_fee").toString();
//            String orderNo = callBackMap.get("out_trade_no").toString();
//            String openId = callBackMap.get("openid").toString();

            //更新分销合伙人记录
            updateClientEndDistributionPartnerOrder(id);

            Map paramsMap = new HashedMap();
            //判断该二级团长是否存在客户级联
            List<LinkedHashMap> cascadeList = userOrderDao.getCascadeListByUserId(userId);
            if (cascadeList.size() > 0) {
                //存在级联，查询上级类型和上级id
                int superiorType = Integer.parseInt(cascadeList.get(0).get("superior_type").toString());
                String shopId = cascadeList.get(0).get("superior_id").toString();
                if (superiorType == 0) {
                    //该申请成为分销合伙人的二级团长的上级为小店
                    paramsMap.put("firstCommanderType", 1);
                } else if (superiorType == 1) {
                    //该申请成为分销合伙人的二级团长的上级为分销合伙人
                    paramsMap.put("firstCommanderType", 2);
                }
                paramsMap.put("shopId", shopId);
            } else {
                //不存在级联
                //添加二级团长（平台所属）
                paramsMap.put("firstCommanderType", 0);
                paramsMap.put("shopId", 0);
            }
            paramsMap.put("userId", userId);
            paramsMap.put("createTime", DateUtils.getAccurateDate());
            userOrderDao.addSecondRegimentalCommander(paramsMap);

            return callBackMap;
        } catch (Exception e) {
            throw new GlobalProcessException("回调接口失败", e.getMessage());
        }
    }

    @Override
    public Map clientEndDistributionPartnerToBeLeaderPayBack(String result) {
        try {
            log.info("微信回调函数入口 {}", result);
            Map callBackMap = WXPayUtil.xmlToMap(result);
            String result_code = callBackMap.get("result_code").toString();
            boolean isPaySuccess = StringUtils.isNotBlank(result_code) && result_code.equals("SUCCESS");
            if (!isPaySuccess) throw new GlobalProcessException("支付失败");

            String attach = callBackMap.get("attach").toString();
            String[] attachArr = attach.split("-");
            String id = attachArr[0];
            String shopId = attachArr[1];
            String userId = userOrderDao.getUserIdByDistributionRecordId(id);

            //更新分销合伙人记录
            updateClientEndDistributionPartnerOrder(id);


            Map paramsMap = new HashedMap();
            //判断该二级团长是否存在客户级联
            List<LinkedHashMap> cascadeList = userOrderDao.getCascadeListByUserId(userId);
            if (cascadeList.size() > 0) {
                //存在级联，查询上级类型和上级id
                int superiorType = Integer.parseInt(cascadeList.get(0).get("superior_type").toString());
                String topShopId = cascadeList.get(0).get("superior_id").toString();
                if (superiorType == 0) {
                    //该申请成为分销合伙人的二级团长的上级为小店
                    paramsMap.put("firstCommanderType", 1);
                } else if (superiorType == 1) {
                    //该申请成为分销合伙人的二级团长的上级为分销合伙人
                    paramsMap.put("firstCommanderType", 2);
                }
                paramsMap.put("shopId", topShopId);
            } else {
                //不存在级联
                //添加二级团长（小店所属）
                paramsMap.put("firstCommanderType", 1);
                paramsMap.put("shopId", shopId);
            }
            paramsMap.put("userId", userId);
            paramsMap.put("createTime", DateUtils.getAccurateDate());
            userOrderDao.addSecondRegimentalCommander(paramsMap);

            return callBackMap;
        } catch (Exception e) {
            throw new GlobalProcessException("回调接口失败", e.getMessage());
        }
    }
}
