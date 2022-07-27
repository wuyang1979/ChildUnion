package com.qinzi123.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.wxpay.sdk.WXPayUtil;
import com.qinzi123.dao.WithdrawalDao;
import com.qinzi123.dto.WithDrawDTO;
import com.qinzi123.dto.WithDrawEnum;
import com.qinzi123.util.DateUtils;
import com.qinzi123.util.WithDrawUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: WithdrawalServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/4/1 0001 17:29
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class WithdrawalServiceImpl extends AbstractWechatMiniProgramService implements WithdrawalService {

    @Resource
    private WithdrawalDao withdrawalDao;

    @Override
    public int startWithdrawal(Map map) throws Exception {
        int card = Integer.parseInt(map.get("card").toString());
        Map<String, Object> shopInfo = withdrawalDao.getShopInfoByCard(card);
        int shopType = Integer.parseInt(shopInfo.get("shop_type").toString());
        if (shopType == 0) {
            //供应小店
            map.put("createTime", DateUtils.getAccurateDate());
            //提现状态  0：提现中；1：已提现
            map.put("status", 0);
            withdrawalDao.updateShopMoneyByCardId(map);
        } else if (shopType == 1) {
            //分销小店
            //1.企业付款至零钱
            String targetOpenId = withdrawalDao.getTargetOpenIdByCard(card);
            Map<String, Object> wxWithdrawalMap = enterprisePayToChange(Double.parseDouble(map.get("actualAmount").toString()), targetOpenId, "", "", "");
            String msg = wxWithdrawalMap.get("msg").toString();
            if ("success".equals(msg)) {
                //提现成功
                map.put("createTime", DateUtils.getAccurateDate());
                //提现状态  0：提现中；1：已提现
                map.put("status", 1);
                withdrawalDao.updateDistributionShopMoneyByCardId(map);
            }
        }
        return withdrawalDao.startWithdrawal(map);
    }

    @Override
    public int distributionPartnerStartWithdrawal(Map map) throws Exception {
        String userId = map.get("userId").toString();
        String targetOpenId = withdrawalDao.getTargetOpenIdByUserId(userId);
        Map<String, Object> wxWithdrawalMap = enterprisePayToChangeForChengZhangGo(Double.parseDouble(map.get("actualAmount").toString()), targetOpenId, "", "", "");
        String msg = wxWithdrawalMap.get("msg").toString();
        if ("success".equals(msg)) {
            //提现成功
            map.put("createTime", DateUtils.getAccurateDate());
            //提现状态  0：提现中；1：已提现
            map.put("status", 1);
            withdrawalDao.updateDistributionShopMoneyByUserId(map);
        }
        return withdrawalDao.distributionPartnerStartWithdrawal(map);
    }

    Map<String, Object> enterprisePayToChange(double amount, String openId, String independentMchid, String independentApiKey, String apiclientCertPath) throws Exception {
        //开始提现，生成订单号
        String orderNumber = WithDrawUtils.getOrderNumber();
        //自定义的将提现所需要的参数封装的实体类
        WithDrawDTO withDrawDTO = new WithDrawDTO();
        withDrawDTO.setPartner_trade_no(orderNumber);
        withDrawDTO.setDesc("提现通知");
        //金额单位为分
        withDrawDTO.setAmount((int) (amount * 100));
        //此参数代表，开启真实姓名校验，也可以关闭，详看官方文档的参数说明
        withDrawDTO.setCheck_name("NO_CHECK");
        //withDrawDTO.setRe_user_name(appletWithDrawDTO.getName());
        //微信小程序用户的openid
        withDrawDTO.setOpenid(openId);
        Map<String, String> params = JSON.parseObject(JSON.toJSONString(withDrawDTO), new TypeReference<Map<String, String>>() {
        });
        params = WithDrawUtils.fillRequest(independentMchid, independentApiKey, params);
        withDrawDTO.setNonce_str(params.get("nonce_str"));
        withDrawDTO.setMchid(params.get("mchid"));
        withDrawDTO.setMch_appid(params.get("mch_appid"));
        withDrawDTO.setSign(params.get("sign"));
        String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
        String post = WithDrawUtils.getRestInstance(independentMchid, apiclientCertPath, url, WithDrawUtils.convertToXml(withDrawDTO));
        Map<String, String> result = WXPayUtil.xmlToMap(post);
        //result为调用接口之后的返回参数，可以根据返回参数判断是否成功
        Map<String, Object> resultMap = new HashedMap();
        if ("SUCCESS".equals(result.get("result_code"))) {
            //提现成功
            resultMap.put("msg", "success");
            return resultMap;
        } else {
            //提现失败
            resultMap.put("msg", WithDrawEnum.fromText(result.get("err_code")).getMsg());
            return resultMap;
        }
    }

    Map<String, Object> enterprisePayToChangeForChengZhangGo(double amount, String openId, String independentMchid, String independentApiKey, String apiclientCertPath) throws Exception {
        //开始提现，生成订单号
        String orderNumber = WithDrawUtils.getOrderNumber();
        //自定义的将提现所需要的参数封装的实体类
        WithDrawDTO withDrawDTO = new WithDrawDTO();
        withDrawDTO.setPartner_trade_no(orderNumber);
        withDrawDTO.setDesc("提现通知");
        //金额单位为分
        withDrawDTO.setAmount((int) (amount * 100));
        //此参数代表，开启真实姓名校验，也可以关闭，详看官方文档的参数说明
        withDrawDTO.setCheck_name("NO_CHECK");
        //withDrawDTO.setRe_user_name(appletWithDrawDTO.getName());
        //微信小程序用户的openid
        withDrawDTO.setOpenid(openId);
        Map<String, String> params = JSON.parseObject(JSON.toJSONString(withDrawDTO), new TypeReference<Map<String, String>>() {
        });
        params = WithDrawUtils.fillRequestForChengZhangGo(independentMchid, independentApiKey, params);
        withDrawDTO.setNonce_str(params.get("nonce_str"));
        withDrawDTO.setMchid(params.get("mchid"));
        withDrawDTO.setMch_appid(params.get("mch_appid"));
        withDrawDTO.setSign(params.get("sign"));
        String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
        String post = WithDrawUtils.getRestInstance(independentMchid, apiclientCertPath, url, WithDrawUtils.convertToXml(withDrawDTO));
        Map<String, String> result = WXPayUtil.xmlToMap(post);
        //result为调用接口之后的返回参数，可以根据返回参数判断是否成功
        Map<String, Object> resultMap = new HashedMap();
        if ("SUCCESS".equals(result.get("result_code"))) {
            //提现成功
            resultMap.put("msg", "success");
            return resultMap;
        } else {
            //提现失败
            resultMap.put("msg", WithDrawEnum.fromText(result.get("err_code")).getMsg());
            return resultMap;
        }
    }

    @Override
    public Map getTotalOfCurrentDay(Map map) {
        map.put("startTime", DateUtils.getAccurateDay() + " 00:00:00");
        map.put("endTime", DateUtils.getAccurateDay() + " 23:59:59");
        List<LinkedHashMap> withdrawalList = withdrawalDao.getCurrentDayWithdrawalRecordList(map);
        Map<String, Object> resultMap = new HashedMap();
        if (withdrawalList.size() > 0) {
            Double total = 0.00;
            for (int i = 0; i < withdrawalList.size(); i++) {
                total += Double.parseDouble(withdrawalList.get(i).get("initiate_amount").toString());
            }
            resultMap.put("total", total);
        } else {
            resultMap.put("total", 0);
        }
        return resultMap;
    }

    @Override
    public Map getDistributionPartnerTotalOfCurrentDay(Map map) {
        map.put("startTime", DateUtils.getAccurateDay() + " 00:00:00");
        map.put("endTime", DateUtils.getAccurateDay() + " 23:59:59");
        List<LinkedHashMap> withdrawalList = withdrawalDao.getDistributionPartnerCurrentDayWithdrawalRecordList(map);
        Map<String, Object> resultMap = new HashedMap();
        if (withdrawalList.size() > 0) {
            Double total = 0.00;
            for (int i = 0; i < withdrawalList.size(); i++) {
                total += Double.parseDouble(withdrawalList.get(i).get("initiate_amount").toString());
            }
            resultMap.put("total", total);
        } else {
            resultMap.put("total", 0);
        }
        return resultMap;
    }

    @Override
    public Map getAllAmount(Map map) {
        List<LinkedHashMap> withdrawalList = withdrawalDao.getAllRecordList(map);
        Map<String, Object> resultMap = new HashedMap();
        if (withdrawalList.size() > 0) {
            Double total = 0.00;
            for (int i = 0; i < withdrawalList.size(); i++) {
                total += Double.parseDouble(withdrawalList.get(i).get("initiate_amount").toString());
            }
            resultMap.put("total", total);
        } else {
            resultMap.put("total", 0.00);
        }
        return resultMap;
    }

    @Override
    public Map getDistributionPartnerAllAmount(Map map) {
        List<LinkedHashMap> withdrawalList = withdrawalDao.getAllDistributionPartnerRecordList(map);
        Map<String, Object> resultMap = new HashedMap();
        if (withdrawalList.size() > 0) {
            Double total = 0.00;
            for (int i = 0; i < withdrawalList.size(); i++) {
                total += Double.parseDouble(withdrawalList.get(i).get("initiate_amount").toString());
            }
            resultMap.put("total", total);
        } else {
            resultMap.put("total", 0.00);
        }
        return resultMap;
    }

    @Override
    public List<LinkedHashMap> getRecordList(Map map) {
        String date = map.get("year").toString();
        String endTime = date + "-12-31 23:59:59";
        map.put("startTime", map.get("year").toString() + "-01-01 00:00:00");
        map.put("endTime", endTime);
        return withdrawalDao.getRecordList(map);
    }

    @Override
    public List<LinkedHashMap> getDistributionPartnerRecordList(Map map) {
        String date = map.get("year").toString();
        String endTime = date + "-12-31 23:59:59";
        map.put("startTime", map.get("year").toString() + "-01-01 00:00:00");
        map.put("endTime", endTime);
        return withdrawalDao.getDistributionPartnerRecordList(map);
    }
}
