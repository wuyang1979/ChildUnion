package com.qinzi123.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.qinzi123.dao.ProductDao;
import com.qinzi123.dao.UserOrderDao;
import com.qinzi123.dto.*;
import com.qinzi123.exception.GlobalProcessException;
import com.qinzi123.happiness.util.StringUtil;
import com.qinzi123.service.PushMiniProgramService;
import com.qinzi123.service.UserOrderService;
import com.qinzi123.util.DateUtils;
import com.qinzi123.util.Utils;
import com.qinzi123.util.WithDrawUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @title: UserOrderServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/2/16 0016 11:20
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class UserOrderServiceImpl extends AbstractWechatMiniProgramService implements UserOrderService {

    @Autowired
    private UserOrderDao userOrderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private PushMiniProgramService pushService;

    private final int PLATFORM_SERVICE_RATE = 0;

    private Logger log = LoggerFactory.getLogger(RechargeMoneyServiceImpl.class);

    private static final String TRADE_TYPE = "JSAPI";

    private static final String KEY = "Qinzi01234567890Qinzi01234567890";

    private static final String LEDUODUOAPIKEY = "Leduoduo01234567Leduoduo01234567";

    private static final int ORDER_TIMEOUT_DAYS = 7;

    private RestTemplate localRestTemplate;

    public RestTemplate getLocalRestTemplate() {
        if (localRestTemplate == null) {
            localRestTemplate = new RestTemplate();
            localRestTemplate.getMessageConverters().add(0,
                    new StringHttpMessageConverter(Charset.forName("UTF-8")));
        }
        return localRestTemplate;
    }

    @Override
    public int addUserOrder(Map map) {
        Map<String, Object> userInfoMap = userOrderDao.getUserInfoById(map);
        Map<String, Object> productInfo = userOrderDao.getProductInfoByProductId(map);
        map.put("openId", userInfoMap.get("open_id"));
        map.put("phone", userInfoMap.get("phone"));
        map.put("orderNo", Utils.getCurrentDateNoFlag());
        map.put("status", OrderStatusType.PendingPayment.getType());
        map.put("createTime", DateUtils.getAccurateDate());
        map.put("orderSaleType", 0);
        map.put("retail_commission", "");
        map.put("retail_commission_income", "");
        map.put("primary_distribution_shop_id", "");
        map.put("platform_service_fee", "");
        map.put("primary_distribution_open_id", "");
        if (map.get("receiveAddress") == null) {
            map.put("receiveAddress", "");
        }
        int standardId = Integer.parseInt(map.get("standardId").toString());
        Map<String, Object> standardInfo = userOrderDao.getProductStandardInfoByStandardId(standardId);
        //判断该商品是否允许分销
        if (Integer.parseInt(productInfo.get("is_allow_distribution").toString()) == 1) {
            //判断该订单是否由平台分销
            boolean isPlatDistribute = Boolean.parseBoolean(map.get("isPlatDistribute").toString());
            boolean enterFromShop = Boolean.parseBoolean(map.get("enterFromShop").toString());
            boolean enterFromQr = Boolean.parseBoolean(map.get("enterFromQr").toString());
            boolean enterFromForwardDetailPage = Integer.parseInt(map.get("enterFromForwardDetailPage").toString()) == 1;
            String posterSharerPhone = map.get("posterSharerPhone").toString();
            int disShopId = Integer.parseInt(map.get("disShopId").toString());
            int productId = Integer.parseInt(map.get("productId").toString());
            int num = Integer.parseInt(map.get("num").toString());

            if (isPlatDistribute) {
                //平台分销
                map.put("orderSaleType", 1);
                Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                DecimalFormat df = new DecimalFormat("#0.00");
                String retail_commission = df.format(distribution * num);
                map.put("retail_commission", retail_commission);
                map.put("primary_distribution_shop_id", 0);
                map.put("platform_service_fee", "0.00");
                map.put("retail_commission_income", retail_commission);
            } else {
                //非平台分销
                //C端客户分销--满足条件 enterFromShop=false; 海报分享人手机号查询不到小店信息但是已成为分销合伙人
                if (StringUtils.isNotEmpty(posterSharerPhone)) {
                    //分销合伙人通过分享海报进行分销
                    List<LinkedHashMap> shopIdList = userOrderDao.getPrimaryDistributionShopIdByPhone(posterSharerPhone);
                    List<LinkedHashMap> distributionPartnerList = userOrderDao.getDistributionPartnerListByPhone(posterSharerPhone);
                    if (shopIdList.size() == 0 && distributionPartnerList.size() > 0) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        String primary_distribution_open_id = userOrderDao.getOpenIdByPhone(posterSharerPhone);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", -1);
                        map.put("primary_distribution_open_id", primary_distribution_open_id);
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", df.format(Double.parseDouble(retail_commission) - Double.parseDouble(platform_service_fee)));
                    } else if (shopIdList.size() == 0 && distributionPartnerList.size() == 0) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", -1);
                        map.put("primary_distribution_open_id", "");
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", "0.00");
                    }
                } else if (enterFromForwardDetailPage) {
                    //分销合伙人通过转发产品详情页进行分销
                    String forwardUserId = map.get("forwardUserId").toString();
                    String forwardPhone = userOrderDao.getPhoneByUserId(forwardUserId);
                    List<LinkedHashMap> shopIdList = userOrderDao.getPrimaryDistributionShopIdByPhone(forwardPhone);
                    List<LinkedHashMap> distributionPartnerList = userOrderDao.getDistributionPartnerListByPhone(forwardPhone);
                    if (shopIdList.size() == 0 && distributionPartnerList.size() > 0) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        String primary_distribution_open_id = userOrderDao.getOpenIdByPhone(forwardPhone);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", -1);
                        map.put("primary_distribution_open_id", primary_distribution_open_id);
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", df.format(Double.parseDouble(retail_commission) - Double.parseDouble(platform_service_fee)));
                    } else if (shopIdList.size() == 0 && distributionPartnerList.size() == 0) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", -1);
                        map.put("primary_distribution_open_id", "");
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", "0.00");
                    }
                } else {
                    //B端商户分销
                    //产品二维码分销：由商户分享的分销产品二维码完成下单
                    boolean productQrDistribution = enterFromQr && disShopId != -1;
                    //小店二维码分销：由商户分享的小店二维码中的分销商品完成下单
                    boolean shopQrDistribution = enterFromShop && StringUtils.isEmpty(posterSharerPhone) && disShopId != -1;
                    //海报分销：商户进入自己的成长GO小店页面，再进入分销商品页面生成海报分享客户完成下单
                    boolean posterFromShopDistribution = false;
                    if (StringUtils.isNotEmpty(posterSharerPhone)) {
                        String issuerPhone = userOrderDao.getIssuerPhoneByProductId(productId);
                        //判断发布人手机号与分享海报人手机号是否相同，不同为分销订单，相同为供应商自己分享的海报
                        if (!posterSharerPhone.equals(issuerPhone)) {
                            posterFromShopDistribution = true;
                        }
                    }
                    boolean bEndDistributeFlag = productQrDistribution || shopQrDistribution || posterFromShopDistribution;
                    if (bEndDistributeFlag) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", disShopId);
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", df.format(Double.parseDouble(retail_commission) - Double.parseDouble(platform_service_fee)));
                    }
                }
            }
        }
        userOrderDao.addUserOrder(map);
        userOrderDao.reduceProductInventory(map);
        return Integer.parseInt(map.get("id").toString());
    }

    @Override
    public int addUserActivityOrder(Map map) {
        Map<String, Object> userInfoMap = userOrderDao.getUserInfoById(map);
        Map<String, Object> productInfo = userOrderDao.getProductInfoByProductId(map);
        map.put("openId", userInfoMap.get("open_id"));
        map.put("phone", userInfoMap.get("phone"));
        map.put("orderNo", Utils.getCurrentDateNoFlag());
        map.put("status", OrderStatusType.PendingPayment.getType());
        map.put("createTime", DateUtils.getAccurateDate());
        map.put("orderSaleType", 0);
        map.put("retail_commission", "");
        map.put("retail_commission_income", "");
        map.put("primary_distribution_shop_id", "");
        map.put("platform_service_fee", "");
        map.put("primary_distribution_open_id", "");
        if (map.get("receiveAddress") == null) {
            map.put("receiveAddress", "");
        }
        int standardId = Integer.parseInt(map.get("standardId").toString());
        Map<String, Object> standardInfo = userOrderDao.getActivityStandardInfoByStandardId(standardId);
        //判断该商品是否允许分销
        if (Integer.parseInt(productInfo.get("is_allow_distribution").toString()) == 1) {
            //判断该订单是否由平台分销
            boolean isPlatDistribute = Boolean.parseBoolean(map.get("isPlatDistribute").toString());
            boolean enterFromShop = Boolean.parseBoolean(map.get("enterFromShop").toString());
            boolean enterFromQr = Boolean.parseBoolean(map.get("enterFromQr").toString());
            boolean enterFromForwardDetailPage = Integer.parseInt(map.get("enterFromForwardDetailPage").toString()) == 1;
            String posterSharerPhone = map.get("posterSharerPhone").toString();
            int disShopId = Integer.parseInt(map.get("disShopId").toString());
            int productId = Integer.parseInt(map.get("productId").toString());
            int num = Integer.parseInt(map.get("num").toString());

            if (isPlatDistribute) {
                //平台分销
                map.put("orderSaleType", 1);
                Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                DecimalFormat df = new DecimalFormat("#0.00");
                String retail_commission = df.format(distribution * num);
                map.put("retail_commission", retail_commission);
                map.put("primary_distribution_shop_id", 0);
                map.put("platform_service_fee", "0.00");
                map.put("retail_commission_income", retail_commission);
            } else {
                //非平台分销
                //C端客户分销--满足条件 enterFromShop=false; 海报分享人手机号查询不到小店信息但是已成为分销合伙人
                if (StringUtils.isNotEmpty(posterSharerPhone)) {
                    List<LinkedHashMap> shopIdList = userOrderDao.getPrimaryDistributionShopIdByPhone(posterSharerPhone);
                    List<LinkedHashMap> distributionPartnerList = userOrderDao.getDistributionPartnerListByPhone(posterSharerPhone);
                    if (shopIdList.size() == 0 && distributionPartnerList.size() > 0) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        String primary_distribution_open_id = userOrderDao.getOpenIdByPhone(posterSharerPhone);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", -1);
                        map.put("primary_distribution_open_id", primary_distribution_open_id);
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", df.format(Double.parseDouble(retail_commission) - Double.parseDouble(platform_service_fee)));
                    } else if (shopIdList.size() == 0 && distributionPartnerList.size() == 0) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", -1);
                        map.put("primary_distribution_open_id", "");
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", "0.00");
                    }
                } else if (enterFromForwardDetailPage) {
                    //分销合伙人通过转发产品详情页进行分销
                    String forwardUserId = map.get("forwardUserId").toString();
                    String forwardPhone = userOrderDao.getPhoneByUserId(forwardUserId);
                    List<LinkedHashMap> shopIdList = userOrderDao.getPrimaryDistributionShopIdByPhone(forwardPhone);
                    List<LinkedHashMap> distributionPartnerList = userOrderDao.getDistributionPartnerListByPhone(forwardPhone);
                    if (shopIdList.size() == 0 && distributionPartnerList.size() > 0) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        String primary_distribution_open_id = userOrderDao.getOpenIdByPhone(forwardPhone);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", -1);
                        map.put("primary_distribution_open_id", primary_distribution_open_id);
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", df.format(Double.parseDouble(retail_commission) - Double.parseDouble(platform_service_fee)));
                    } else if (shopIdList.size() == 0 && distributionPartnerList.size() == 0) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", -1);
                        map.put("primary_distribution_open_id", "");
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", "0.00");
                    }
                } else {
                    //B端商户分销
                    //产品二维码分销：由商户分享的分销产品二维码完成下单
                    boolean productQrDistribution = enterFromQr && disShopId != -1;
                    //小店二维码分销：由商户分享的小店二维码中的分销商品完成下单
                    boolean shopQrDistribution = enterFromShop && StringUtils.isEmpty(posterSharerPhone) && disShopId != -1;
                    //海报分销：商户进入自己的成长GO小店页面，再进入分销商品页面生成海报分享客户完成下单
                    boolean posterFromShopDistribution = false;
                    if (StringUtils.isNotEmpty(posterSharerPhone)) {
                        String issuerPhone = userOrderDao.getIssuerPhoneByProductId(productId);
                        //判断发布人手机号与分享海报人手机号是否相同，不同为分销订单，相同为供应商自己分享的海报
                        if (!posterSharerPhone.equals(issuerPhone)) {
                            posterFromShopDistribution = true;
                        }
                    }
                    boolean bEndDistributeFlag = productQrDistribution || shopQrDistribution || posterFromShopDistribution;
                    if (bEndDistributeFlag) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", disShopId);
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", df.format(Double.parseDouble(retail_commission) - Double.parseDouble(platform_service_fee)));
                    }
                }
            }
        }
        userOrderDao.addUserActivityOrder(map);
        userOrderDao.reduceActivityInventory(map);
        return Integer.parseInt(map.get("id").toString());
    }

    @Override
    public int addUserFreeOrder(HashMap map) {
        Map<String, Object> userInfoMap = userOrderDao.getUserInfoById(map);
        Map<String, Object> productInfo = userOrderDao.getProductInfoByProductId(map);
        map.put("openId", userInfoMap.get("open_id"));
        map.put("phone", userInfoMap.get("phone"));
        map.put("orderNo", Utils.getCurrentDateNoFlag());
        map.put("status", OrderStatusType.PendingConfirm.getType());
        map.put("createTime", DateUtils.getAccurateDate());
        map.put("payTime", DateUtils.getAccurateDate());
        map.put("orderSaleType", 0);
        map.put("retail_commission", "");
        map.put("retail_commission_income", "");
        map.put("primary_distribution_shop_id", "");
        map.put("platform_service_fee", "");
        map.put("primary_distribution_open_id", "");
        if (map.get("receiveAddress") == null) {
            map.put("receiveAddress", "");
        }

        int standardId = Integer.parseInt(map.get("standardId").toString());
        int orderType = Integer.parseInt(map.get("orderType").toString());
        Map<String, Object> standardInfo = userOrderDao.getProductStandardInfoByStandardId(standardId);
        //判断该商品是否允许分销
        if (Integer.parseInt(productInfo.get("is_allow_distribution").toString()) == 1) {
            //判断该订单是否由平台分销
            boolean isPlatDistribute = Boolean.parseBoolean(map.get("isPlatDistribute").toString());
            boolean enterFromShop = Boolean.parseBoolean(map.get("enterFromShop").toString());
            boolean enterFromQr = Boolean.parseBoolean(map.get("enterFromQr").toString());
            boolean enterFromForwardDetailPage = Integer.parseInt(map.get("enterFromForwardDetailPage").toString()) == 1;
            String posterSharerPhone = map.get("posterSharerPhone").toString();
            int disShopId = Integer.parseInt(map.get("disShopId").toString());
            int productId = Integer.parseInt(map.get("productId").toString());
            int num = Integer.parseInt(map.get("num").toString());
            if (isPlatDistribute) {
                //平台分销
                map.put("orderSaleType", 1);
                Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                DecimalFormat df = new DecimalFormat("#0.00");
                String retail_commission = df.format(distribution * num);
                map.put("retail_commission", retail_commission);
                map.put("primary_distribution_shop_id", 0);
                map.put("platform_service_fee", "0.00");
                map.put("retail_commission_income", retail_commission);
            } else {
                //非平台分销
                //C端客户分销--满足条件 enterFromShop=false; 海报分享人手机号查询不到小店信息但是已成为分销合伙人
                if (StringUtils.isNotEmpty(posterSharerPhone)) {
                    List<LinkedHashMap> shopIdList = userOrderDao.getPrimaryDistributionShopIdByPhone(posterSharerPhone);
                    List<LinkedHashMap> distributionPartnerList = userOrderDao.getDistributionPartnerListByPhone(posterSharerPhone);
                    if (shopIdList.size() == 0 && distributionPartnerList.size() > 0) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        String primary_distribution_open_id = userOrderDao.getOpenIdByPhone(posterSharerPhone);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", -1);
                        map.put("primary_distribution_open_id", primary_distribution_open_id);
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", df.format(Double.parseDouble(retail_commission) - Double.parseDouble(platform_service_fee)));
                    } else if (shopIdList.size() == 0 && distributionPartnerList.size() == 0) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", -1);
                        map.put("primary_distribution_open_id", "");
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", "0.00");
                    }
                } else if (enterFromForwardDetailPage) {
                    //分销合伙人通过转发产品详情页进行分销
                    String forwardUserId = map.get("forwardUserId").toString();
                    String forwardPhone = userOrderDao.getPhoneByUserId(forwardUserId);
                    List<LinkedHashMap> shopIdList = userOrderDao.getPrimaryDistributionShopIdByPhone(forwardPhone);
                    List<LinkedHashMap> distributionPartnerList = userOrderDao.getDistributionPartnerListByPhone(forwardPhone);
                    if (shopIdList.size() == 0 && distributionPartnerList.size() > 0) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        String primary_distribution_open_id = userOrderDao.getOpenIdByPhone(forwardPhone);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", -1);
                        map.put("primary_distribution_open_id", primary_distribution_open_id);
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", df.format(Double.parseDouble(retail_commission) - Double.parseDouble(platform_service_fee)));
                    } else if (shopIdList.size() == 0 && distributionPartnerList.size() == 0) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", -1);
                        map.put("primary_distribution_open_id", "");
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", "0.00");
                    }
                } else {
                    //B端商户分销
                    //产品二维码分销：由商户分享的分销产品二维码完成下单
                    boolean productQrDistribution = enterFromQr && disShopId != -1;
                    //小店二维码分销：由商户分享的小店二维码中的分销商品完成下单
                    boolean shopQrDistribution = enterFromShop && StringUtils.isEmpty(posterSharerPhone) && disShopId != -1;
                    //海报分销：商户进入自己的成长GO小店页面，再进入分销商品页面生成海报分享客户完成下单
                    boolean posterFromShopDistribution = false;
                    if (StringUtils.isNotEmpty(posterSharerPhone)) {
                        String issuerPhone = userOrderDao.getIssuerPhoneByProductId(productId);
                        //判断发布人手机号与分享海报人手机号是否相同，不同为分销订单，相同为供应商自己分享的海报
                        if (!posterSharerPhone.equals(issuerPhone)) {
                            posterFromShopDistribution = true;
                        }
                    }
                    boolean bEndDistributeFlag = productQrDistribution || shopQrDistribution || posterFromShopDistribution;
                    if (bEndDistributeFlag) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", disShopId);
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", df.format(Double.parseDouble(retail_commission) - Double.parseDouble(platform_service_fee)));
                    }
                }

            }
        }
        userOrderDao.addUserFreeOrder(map);
        userOrderDao.reduceProductInventory(map);
        userOrderDao.updateProductBuyCountById(map);

        Map<String, Object> productMap = productDao.getProductInfoByID(map);
        String statusName;
        if (orderType == 0) {
            statusName = "待收货";
        } else {
            statusName = "待核销";
        }
        //订阅消息推送
        pushWxOrderTemplateMessage(map.get("openId").toString(), map.get("orderNo").toString(), productMap.get("name").toString(), statusName);
        return Integer.parseInt(map.get("id").toString());
    }

    @Override
    public int addUserFreeActivityOrder(Map map) {
        Map<String, Object> userInfoMap = userOrderDao.getUserInfoById(map);
        Map<String, Object> productInfo = userOrderDao.getProductInfoByProductId(map);
        map.put("openId", userInfoMap.get("open_id"));
        map.put("phone", userInfoMap.get("phone"));
        map.put("orderNo", Utils.getCurrentDateNoFlag());
        map.put("status", OrderStatusType.PendingConfirm.getType());
        map.put("createTime", DateUtils.getAccurateDate());
        map.put("payTime", DateUtils.getAccurateDate());
        map.put("orderSaleType", 0);
        map.put("retail_commission", "");
        map.put("retail_commission_income", "");
        map.put("primary_distribution_shop_id", "");
        map.put("platform_service_fee", "");
        map.put("primary_distribution_open_id", "");
        if (map.get("receiveAddress") == null) {
            map.put("receiveAddress", "");
        }
        int standardId = Integer.parseInt(map.get("standardId").toString());
        int orderType = Integer.parseInt(map.get("orderType").toString());
        Map<String, Object> standardInfo = userOrderDao.getActivityStandardInfoByStandardId(standardId);
        if (Integer.parseInt(productInfo.get("is_allow_distribution").toString()) == 1) {
            //判断该订单是否由平台分销
            boolean isPlatDistribute = Boolean.parseBoolean(map.get("isPlatDistribute").toString());
            boolean enterFromShop = Boolean.parseBoolean(map.get("enterFromShop").toString());
            boolean enterFromQr = Boolean.parseBoolean(map.get("enterFromQr").toString());
            boolean enterFromForwardDetailPage = Integer.parseInt(map.get("enterFromForwardDetailPage").toString()) == 1;
            String posterSharerPhone = map.get("posterSharerPhone").toString();
            int disShopId = Integer.parseInt(map.get("disShopId").toString());
            int productId = Integer.parseInt(map.get("productId").toString());
            int num = Integer.parseInt(map.get("num").toString());
            if (isPlatDistribute) {
                //平台分销
                map.put("orderSaleType", 1);
                Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                DecimalFormat df = new DecimalFormat("#0.00");
                String retail_commission = df.format(distribution * num);
                map.put("retail_commission", retail_commission);
                map.put("primary_distribution_shop_id", 0);
                map.put("platform_service_fee", "0.00");
                map.put("retail_commission_income", retail_commission);
            } else {
                //非平台分销
                //C端客户分销--满足条件 enterFromShop=false; 海报分享人手机号查询不到小店信息但是已成为分销合伙人
                if (StringUtils.isNotEmpty(posterSharerPhone)) {
                    List<LinkedHashMap> shopIdList = userOrderDao.getPrimaryDistributionShopIdByPhone(posterSharerPhone);
                    List<LinkedHashMap> distributionPartnerList = userOrderDao.getDistributionPartnerListByPhone(posterSharerPhone);
                    if (shopIdList.size() == 0 && distributionPartnerList.size() > 0) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        String primary_distribution_open_id = userOrderDao.getOpenIdByPhone(posterSharerPhone);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", -1);
                        map.put("primary_distribution_open_id", primary_distribution_open_id);
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", df.format(Double.parseDouble(retail_commission) - Double.parseDouble(platform_service_fee)));
                    } else if (shopIdList.size() == 0 && distributionPartnerList.size() == 0) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", -1);
                        map.put("primary_distribution_open_id", "");
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", "0.00");
                    }
                } else if (enterFromForwardDetailPage) {
                    //分销合伙人通过转发产品详情页进行分销
                    String forwardUserId = map.get("forwardUserId").toString();
                    String forwardPhone = userOrderDao.getPhoneByUserId(forwardUserId);
                    List<LinkedHashMap> shopIdList = userOrderDao.getPrimaryDistributionShopIdByPhone(forwardPhone);
                    List<LinkedHashMap> distributionPartnerList = userOrderDao.getDistributionPartnerListByPhone(forwardPhone);
                    if (shopIdList.size() == 0 && distributionPartnerList.size() > 0) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        String primary_distribution_open_id = userOrderDao.getOpenIdByPhone(forwardPhone);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", -1);
                        map.put("primary_distribution_open_id", primary_distribution_open_id);
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", df.format(Double.parseDouble(retail_commission) - Double.parseDouble(platform_service_fee)));
                    } else if (shopIdList.size() == 0 && distributionPartnerList.size() == 0) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", -1);
                        map.put("primary_distribution_open_id", "");
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", "0.00");
                    }
                } else {
                    //B端商户分销
                    //产品二维码分销：由商户分享的分销产品二维码完成下单
                    boolean productQrDistribution = enterFromQr && disShopId != -1;
                    //小店二维码分销：由商户分享的小店二维码中的分销商品完成下单
                    boolean shopQrDistribution = enterFromShop && StringUtils.isEmpty(posterSharerPhone) && disShopId != -1;
                    //海报分销：商户进入自己的成长GO小店页面，再进入分销商品页面生成海报分享客户完成下单
                    boolean posterFromShopDistribution = false;
                    if (StringUtils.isNotEmpty(posterSharerPhone)) {
                        String issuerPhone = userOrderDao.getIssuerPhoneByProductId(productId);
                        //判断发布人手机号与分享海报人手机号是否相同，不同为分销订单，相同为供应商自己分享的海报
                        if (!posterSharerPhone.equals(issuerPhone)) {
                            posterFromShopDistribution = true;
                        }
                    }
                    boolean bEndDistributeFlag = productQrDistribution || shopQrDistribution || posterFromShopDistribution;
                    if (bEndDistributeFlag) {
                        map.put("orderSaleType", 1);
                        Double distribution = Double.parseDouble(standardInfo.get("distribution").toString());
                        DecimalFormat df = new DecimalFormat("#0.00");
                        String retail_commission = df.format(distribution * num);
                        map.put("retail_commission", retail_commission);
                        map.put("primary_distribution_shop_id", disShopId);
                        String platform_service_fee = df.format(Double.parseDouble(retail_commission) * PLATFORM_SERVICE_RATE / 100);
                        map.put("platform_service_fee", platform_service_fee);
                        map.put("retail_commission_income", df.format(Double.parseDouble(retail_commission) - Double.parseDouble(platform_service_fee)));
                    }
                }
            }
        }
        userOrderDao.addUserFreeActivityOrder(map);
        userOrderDao.reduceActivityInventory(map);
        userOrderDao.updateProductBuyCountById(map);

        Map<String, Object> productMap = productDao.getProductInfoByID(map);
        String statusName;
        if (orderType == 0) {
            statusName = "待收货";
        } else {
            statusName = "待核销";
        }
        //订阅消息推送
        pushWxOrderTemplateMessage(map.get("openId").toString(), map.get("orderNo").toString(), productMap.get("name").toString(), statusName);
        return Integer.parseInt(map.get("id").toString());
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

    @Override
    public Map getShowInfo(Map map) {
        Map<String, Object> userInfoMap = userOrderDao.getUserInfoById(map);
        Map<String, Object> productInfoMap = userOrderDao.getProductInfoByOrderId(map);
        Map<String, Object> orderInfoMap = userOrderDao.getOrderInfoByOrderId(map);
        Map<String, Object> userAddressMap = userOrderDao.getAddressInfoById(map);
        int orderType = Integer.parseInt(orderInfoMap.get("order_type").toString());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("phone", userInfoMap.get("phone"));
        resultMap.put("main_image", productInfoMap.get("main_image"));
        if (orderType == 1) {
            //活动订单，name为规格名称
            Map<String, Object> standardMap = userOrderDao.getStandardInfoByOrderId(map);
            resultMap.put("standardName", "亲子【" + standardMap.get("adults_num").toString() + "大" + standardMap.get("children_num").toString() + "小】组合套票");
        } else if (orderType == 0) {
            Map<String, Object> standardMap = userOrderDao.getProductStandardInfoByOrderId(map);
            resultMap.put("standardName", standardMap.get("name"));
            resultMap.put("product_style", productInfoMap.get("product_style"));
        }
        resultMap.put("name", productInfoMap.get("name"));
        resultMap.put("introduce", new String(Base64.getDecoder().decode(productInfoMap.get("introduce").toString())));
        resultMap.put("num", orderInfoMap.get("num"));
        resultMap.put("total", orderInfoMap.get("total"));
        resultMap.put("order_type", orderType);
        if (userAddressMap != null && userAddressMap.get("province") != null && userAddressMap.get("city") != null && userAddressMap.get("area") != null && userAddressMap.get("address") != null) {
            resultMap.put("address", userAddressMap.get("province").toString() + userAddressMap.get("city").toString() + userAddressMap.get("area").toString() + userAddressMap.get("address").toString());
        } else {
            resultMap.put("address", "");
        }
        return resultMap;
    }

    @Override
    public Map prepay(Map map) {
        try {
            //该字段表示是否由成长GO平台下单  0：是；1：否
            String appName = map.get("appName").toString();
            if ("chengzhanggo".equals(appName)) {
                map.put("appid", getChengZhangGoAppId());
                map.put("mch_id", getChengZhangGoMchId());
                map.put("key", KEY);
            } else if ("leduoduo".equals(appName)) {
                map.put("appid", getLeDuoDuoAppId());
                map.put("mch_id", getLeDuoDuoMchId());
                map.put("key", LEDUODUOAPIKEY);
            }
            Map params = generatePay(checkUserForClientEnd(map));
            String xml = WXPayUtil.mapToXml(params);
            log.info("预支付的 xml 结构体 {}", xml);
            String resultString = getLocalRestTemplate().postForObject(PREPAY_URL, xml, String.class);
            log.info("预订单的返回结果：{}", resultString);
            String appId = "";
            String key = "";
            if ("chengzhanggo".equals(appName)) {
                appId = getChengZhangGoAppId();
                key = KEY;
            } else if ("leduoduo".equals(appName)) {
                appId = getLeDuoDuoAppId();
                key = LEDUODUOAPIKEY;
            }
            return generateSign(appId, key, params.get("nonce_str").toString(), parseResult4Id(resultString));
        } catch (Exception e) {
            throw new GlobalProcessException("预支付流程失败", e);
        }
    }

    @Override
    public Map joinDistributionPartnerPrePay(Map map) {
        try {
            map.put("appid", getChengZhangGoAppId());
            map.put("mch_id", getChengZhangGoMchId());
            map.put("key", KEY);
            Map params = generateDistributionPay(checkUserForDistributionPartner(map));
            String xml = WXPayUtil.mapToXml(params);
            log.info("预支付的 xml 结构体 {}", xml);
            String resultString = getLocalRestTemplate().postForObject(PREPAY_URL, xml, String.class);
            log.info("预订单的返回结果：{}", resultString);
            String appId = getChengZhangGoAppId();
            String key = KEY;
            return generateSign(appId, key, params.get("nonce_str").toString(), parseResult4Id(resultString));
        } catch (Exception e) {
            throw new GlobalProcessException("预支付流程失败", e);
        }
    }

    @Override
    public List<LinkedHashMap> getReceiveAddress(Map map) {
        return userOrderDao.getReceiveAddress(map);
    }

    @Override
    public List<LinkedHashMap> getSelectedOrderList(Map map) {
        int selectType = Integer.parseInt(map.get("selectType").toString());
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        String userId = map.get("userId").toString();
        List<LinkedHashMap> list = null;
        if (selectType == -1) {
            //全部订单
            list = userOrderDao.getAllOrderList(userId, start, num);
            list.forEach(item -> {
                item.put("introduce", new String(Base64.getDecoder().decode(item.get("introduce").toString())));
            });
            return list;
        } else {
            list = userOrderDao.getSelectedOrderList(userId, selectType, start, num);
            list.forEach(item -> {
                item.put("introduce", new String(Base64.getDecoder().decode(item.get("introduce").toString())));
            });
            return list;
        }
    }

    @Override
    public List<LinkedHashMap> getWriteOffClerkByOpenId(Map map) {
        return userOrderDao.getWriteOffClerkByOpenId(map);
    }

    @Override
    public List<LinkedHashMap> getPendingPayList() {
        return userOrderDao.getPendingPayList();
    }

    @Override
    public List<LinkedHashMap> getOrderListByUserIdAndProductId(Map map) {
        Map<String, Object> userInfo = userOrderDao.getUserInfoById(map);
        Map<String, Object> paramsMap = new HashedMap();
        paramsMap.put("openId", userInfo.get("open_id"));
        paramsMap.put("phone", userInfo.get("phone"));
        paramsMap.put("productId", map.get("productId"));
        return userOrderDao.getOrderListByUserIdAndProductId(paramsMap);
    }

    @Override
    public Map<String, Object> checkWhiteOffInfo(Map map) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        Map productMap = userOrderDao.getProductInfoByOrderId(map);
        if (productMap.get("shop_id") == null) {
            throw new Exception("订单信息错误");
        }
        int shopIdInnerOrder = Integer.parseInt(productMap.get("shop_id").toString());
        String clerkShopIdStr = map.get("clerkShopId").toString().replace("=", ":");
        JSONArray clerkShopIdJsonArr = JSONArray.parseArray(clerkShopIdStr);
        List<WriteOffClerkShopInfo> clerkShopIdList = clerkShopIdJsonArr.toJavaList(WriteOffClerkShopInfo.class);
        boolean result = false;
        for (int i = 0; i < clerkShopIdList.size(); i++) {
            if (clerkShopIdList.get(i).getShopId() == shopIdInnerOrder) {
                result = true;
                break;
            }
        }
        resultMap.put("result", result);
        return resultMap;
    }

    @Override
    public int confirmReceipt(Map map) throws Exception {
        Map<String, Object> orderInfo = userOrderDao.getOrderInfoByOrderId(map);
        //供应商小店id
        int issuerShopId = userOrderDao.getShopIdByProductId(orderInfo);
        Map<String, Object> paramsMap = new HashedMap();
        int orderSaleType = Integer.parseInt(orderInfo.get("order_sale_type").toString());
        if (orderSaleType == 0) {
            //销售订单，结算金额全部到供应商
            paramsMap.put("issuerShopId", issuerShopId);
            paramsMap.put("issuerIncome", Double.parseDouble(orderInfo.get("total").toString()));
            int type = userOrderDao.getTypeByShopId(issuerShopId);
            if (type == 0) {
                //普通小店
                userOrderDao.updateIssuerShopWithdrawalAmount(paramsMap);
            } else if (type == 1) {
                //独立小店
                userOrderDao.updateExclusiveIssuerShopWithdrawalAmount(paramsMap);
            }
        } else if (orderSaleType == 1) {
            //分销订单,分开结算
            int primaryDistributionShopId = Integer.parseInt(orderInfo.get("primary_distribution_shop_id").toString());
            if (primaryDistributionShopId == -1) {
                //客户分销
                String primaryDistributionOpenId = orderInfo.get("primary_distribution_open_id").toString();
                if (StringUtils.isNotEmpty(primaryDistributionOpenId)) {
                    //只要一级分销客户的openId不为空，则说明该客户已成为分销合伙人
                    Map<String, Object> wxWithdrawalMap = enterprisePayToChange(Double.parseDouble(orderInfo.get("retail_commission_income").toString()), primaryDistributionOpenId, "", "", "");
                    String msg = wxWithdrawalMap.get("msg").toString();
                    if ("success".equals(msg)) {
                        //提现成功
                        map.put("createTime", DateUtils.getAccurateDate());
                        map.put("openId", primaryDistributionOpenId);
                        map.put("amount", Double.parseDouble(orderInfo.get("retail_commission_income").toString()));
                        userOrderDao.addClientEndDistributorWithdrawalRecord(map);
                    }
                }
            } else if (primaryDistributionShopId == 0) {
                //平台分销
            } else {
                //商户分销
                paramsMap.put("primaryDistributionShopId", primaryDistributionShopId);
                paramsMap.put("primaryDistributorIncome", Double.parseDouble(orderInfo.get("retail_commission_income").toString()));
                int type = userOrderDao.getTypeByShopId(primaryDistributionShopId);
                if (type == 0) {
                    //普通小店
                    userOrderDao.updateDistributorShopWithdrawalAmount(paramsMap);
                } else if (type == 1) {
                    //2.结算金额计入可提现金额
                    userOrderDao.updateDistributorShopWithdrawalAmount(paramsMap);
                }
            }
            paramsMap.put("issuerShopId", issuerShopId);
            paramsMap.put("issuerIncome", Double.parseDouble(orderInfo.get("total").toString()) - Double.parseDouble(orderInfo.get("retail_commission").toString()));
            int type = userOrderDao.getTypeByShopId(issuerShopId);
            if (type == 0) {
                //普通小店
                userOrderDao.updateIssuerShopWithdrawalAmount(paramsMap);
            } else if (type == 1) {
                //独立小店，结算金额计入可提现金额
                userOrderDao.updateExclusiveIssuerShopWithdrawalAmount(paramsMap);
            }
        }
        return userOrderDao.confirmReceipt(map);
    }

    @Override
    public int checkOrderStatus(Map map) {
        return userOrderDao.checkOrderStatus(map);
    }

    @Override
    public int writeOffUserOrder(Map map) throws Exception {
        //这里有product_id
        Map<String, Object> orderInfo = userOrderDao.getOrderInfoByOrderId(map);
        Map<String, Object> productIdMap = userOrderDao.getProductIdByOrderId(map);
        Map<String, Object> companyIdMap = userOrderDao.getCompanyIdByOrderId(map);
        if (Integer.parseInt(orderInfo.get("order_type").toString()) == 1) {
            //活动订单，添加证书信息
            Map<String, Object> paramsMap = new HashedMap();
            paramsMap.put("openId", orderInfo.get("open_id"));
            paramsMap.put("activity_id", productIdMap.get("product_id"));
            paramsMap.put("institution_id", companyIdMap.get("company_id"));
            paramsMap.put("certificate_time", DateUtils.getAccurateDate());
            paramsMap.put("create_time", DateUtils.getAccurateDate());
            int rows = userOrderDao.addCertificateInfo(paramsMap);
        }
        //供应商小店id
        int issuerShopId = userOrderDao.getShopIdByProductId(orderInfo);
        Map<String, Object> paramsMap = new HashedMap();
        int orderSaleType = Integer.parseInt(orderInfo.get("order_sale_type").toString());
        if (orderSaleType == 0) {
            //销售订单，结算金额全部到供应商
            paramsMap.put("issuerShopId", issuerShopId);
            paramsMap.put("issuerIncome", Double.parseDouble(orderInfo.get("total").toString()));
            int type = userOrderDao.getTypeByShopId(issuerShopId);
            if (type == 0) {
                //普通小店
                userOrderDao.updateIssuerShopWithdrawalAmount(paramsMap);
            } else if (type == 1) {
                //独立小店
                userOrderDao.updateExclusiveIssuerShopWithdrawalAmount(paramsMap);
            }
        } else if (orderSaleType == 1) {
            //分销订单,分开结算
            int primaryDistributionShopId = Integer.parseInt(orderInfo.get("primary_distribution_shop_id").toString());
            if (primaryDistributionShopId == -1) {
                //客户分销
                String primaryDistributionOpenId = orderInfo.get("primary_distribution_open_id").toString();
                if (StringUtils.isNotEmpty(primaryDistributionOpenId)) {
                    //只要一级分销客户的openId不为空，则说明该客户已成为分销合伙人
                    Map<String, Object> wxWithdrawalMap = enterprisePayToChange(Double.parseDouble(orderInfo.get("retail_commission_income").toString()), primaryDistributionOpenId, "", "", "");
                    String msg = wxWithdrawalMap.get("msg").toString();
                    if ("success".equals(msg)) {
                        //提现成功
                        map.put("createTime", DateUtils.getAccurateDate());
                        map.put("openId", primaryDistributionOpenId);
                        map.put("amount", Double.parseDouble(orderInfo.get("retail_commission_income").toString()));
                        userOrderDao.addClientEndDistributorWithdrawalRecord(map);
                    }
                }
            } else if (primaryDistributionShopId == 0) {
                //平台分销
            } else {
                //商户分销
                paramsMap.put("primaryDistributionShopId", primaryDistributionShopId);
                paramsMap.put("primaryDistributorIncome", Double.parseDouble(orderInfo.get("retail_commission_income").toString()));
                int type = userOrderDao.getTypeByShopId(primaryDistributionShopId);
                if (type == 0) {
                    //普通小店
                    userOrderDao.updateDistributorShopWithdrawalAmount(paramsMap);
                } else if (type == 1) {
                    //独立小店，结算金额计入可提现金额
                    userOrderDao.updateDistributorShopWithdrawalAmount(paramsMap);
                }
            }
            paramsMap.put("issuerShopId", issuerShopId);
            paramsMap.put("issuerIncome", Double.parseDouble(orderInfo.get("total").toString()) - Double.parseDouble(orderInfo.get("retail_commission").toString()));
            int type = userOrderDao.getTypeByShopId(issuerShopId);
            if (type == 0) {
                //普通小店
                userOrderDao.updateIssuerShopWithdrawalAmount(paramsMap);
            } else if (type == 1) {
                //独立小店
                userOrderDao.updateExclusiveIssuerShopWithdrawalAmount(paramsMap);
            }
        }
        return userOrderDao.writeOffUserOrder(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int refreshOrder() {
        String currentTimeMinus7Days = DateUtils.formatDate(DateUtils.beforeDay(ORDER_TIMEOUT_DAYS), DateUtils.DATETIME_FORMAT);
        Map<String, Object> paramsMap = new HashedMap();
        paramsMap.put("currentTimeMinus7Days", currentTimeMinus7Days);
        List<LinkedHashMap> unConfirmReceiveOrderList = userOrderDao.getAllUnConfirmReceiveOrderList(paramsMap);
        unConfirmReceiveOrderList.forEach(item -> {
            Map map = new HashedMap();
            map.put("orderId", item.get("id"));
            Map<String, Object> orderInfo = userOrderDao.getOrderInfoByOrderId(map);
            int orderSaleType = Integer.parseInt(orderInfo.get("order_sale_type").toString());
            //供应商小店id
            int issuerShopId = userOrderDao.getShopIdByProductId(orderInfo);
            Map<String, Object> newParamsMap = new HashedMap();
            if (orderSaleType == 0) {
                //销售订单，结算金额全部到供应商
                newParamsMap.put("issuerShopId", issuerShopId);
                newParamsMap.put("issuerIncome", Double.parseDouble(orderInfo.get("total").toString()));
                int type = userOrderDao.getTypeByShopId(issuerShopId);
                if (type == 0) {
                    //普通小店
                    userOrderDao.updateIssuerShopWithdrawalAmount(newParamsMap);
                } else if (type == 1) {
                    //独立小店
                    userOrderDao.updateExclusiveIssuerShopWithdrawalAmount(newParamsMap);
                }
            } else if (orderSaleType == 1) {
                //分销订单,分开结算
                int primaryDistributionShopId = Integer.parseInt(orderInfo.get("primary_distribution_shop_id").toString());
                if (primaryDistributionShopId == -1) {
                    //客户分销
                    String primaryDistributionOpenId = orderInfo.get("primary_distribution_open_id").toString();
                    if (StringUtils.isNotEmpty(primaryDistributionOpenId)) {
                        //只要一级分销客户的openId不为空，则说明该客户已成为分销合伙人
                        Map<String, Object> wxWithdrawalMap;
                        try {
                            wxWithdrawalMap = enterprisePayToChange(Double.parseDouble(orderInfo.get("retail_commission_income").toString()), primaryDistributionOpenId, "", "", "");
                            String msg = wxWithdrawalMap.get("msg").toString();
                            if ("success".equals(msg)) {
                                //提现成功
                                map.put("createTime", DateUtils.getAccurateDate());
                                map.put("openId", primaryDistributionOpenId);
                                map.put("amount", Double.parseDouble(orderInfo.get("retail_commission_income").toString()));
                                userOrderDao.addClientEndDistributorWithdrawalRecord(map);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if (primaryDistributionShopId == 0) {
                    //平台分销
                } else {
                    //商户分销
                    newParamsMap.put("primaryDistributionShopId", primaryDistributionShopId);
                    newParamsMap.put("primaryDistributorIncome", Double.parseDouble(orderInfo.get("retail_commission_income").toString()));
                    int type = userOrderDao.getTypeByShopId(primaryDistributionShopId);
                    if (type == 0) {
                        //普通小店
                        userOrderDao.updateDistributorShopWithdrawalAmount(newParamsMap);
                    } else if (type == 1) {
                        //独立小店，结算金额计入可提现金额
                        userOrderDao.updateDistributorShopWithdrawalAmount(newParamsMap);
                    }
                }
                newParamsMap.put("issuerShopId", issuerShopId);
                newParamsMap.put("issuerIncome", Double.parseDouble(orderInfo.get("total").toString()) - Double.parseDouble(orderInfo.get("retail_commission").toString()));
                int type = userOrderDao.getTypeByShopId(issuerShopId);
                if (type == 0) {
                    //普通小店
                    userOrderDao.updateIssuerShopWithdrawalAmount(newParamsMap);
                } else if (type == 1) {
                    //独立小店
                    userOrderDao.updateExclusiveIssuerShopWithdrawalAmount(newParamsMap);
                }
            }
            userOrderDao.autoConfirmReceiveProductOrder(item);
        });
        return unConfirmReceiveOrderList.size();
    }

    @Override
    public int updateOrderStatusById(Map map) {
        return userOrderDao.updateOrderStatusById(map);
    }

    @Override
    public int updateProductInventoryByStandardId(int standardId, int num) {
        return userOrderDao.updateProductInventoryByStandardId(standardId, num);
    }

    @Override
    public int updateActivityInventoryByStandardId(int standardId, int num) {
        return userOrderDao.updateActivityInventoryByStandardId(standardId, num);
    }

    @Override
    public Map<String, Object> updateOrderByProductIdAndPhone(Map map) {
        List<LinkedHashMap> orderList = userOrderDao.getOrderListByProductIdAndPhone(map);
        Map<String, Object> resultMap = new HashedMap();
        if (orderList.size() == 0) {
            resultMap.put("checkResult", false);
        } else {
            int rows = userOrderDao.updateOrderInfoByProductIdAndPhone(map);
            resultMap.put("checkResult", true);
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> loadBuyerInfoByOrderId(Map map) {
        int orderId = Integer.parseInt((map.get("orderId").toString()));
        String openId = userOrderDao.getOpenIdByOrderId(orderId);
        Map<String, Object> resultMap = userOrderDao.loadBuyerInfoByOpenId(openId);
        byte[] decode = Base64.getDecoder().decode(resultMap.get("nick_name").toString());
        resultMap.put("nick_name", new String(decode));
        return resultMap;
    }

    @Override
    public Map<String, Object> loadStandardByOrderId(Map map) {
        int orderId = Integer.parseInt((map.get("orderId").toString()));
        int standardId = userOrderDao.getStandardIdByOrderId(orderId);
        return userOrderDao.loadStandardByStandardId(standardId);
    }

    @Override
    public Map<String, Object> loadReceiveAddressByOrderId(Map map) {
        int orderId = Integer.parseInt((map.get("orderId").toString()));
        String userId = userOrderDao.getUserIdByOrderId(orderId);
        return userOrderDao.loadReceiveAddressByUserId(userId);
    }

    @Override
    public Map<String, Object> differentiateSupplier(Map map) {
        int card = Integer.parseInt(map.get("card").toString());
        int orderId = Integer.parseInt(map.get("orderId").toString());
        int myShopId = userOrderDao.getShopIdByCard(card);
        int productShopId = userOrderDao.getShopIdByOrderId(orderId);
        Map<String, Object> resultMap = new HashedMap();
        resultMap.put("isSupplier", myShopId == productShopId);
        return resultMap;
    }

    @Override
    public Map getProductNameByOrderId(Map map) {
        Map<String, Object> productMap = userOrderDao.getProductNameByOrderId(map);
        Map<String, Object> orderMap = userOrderDao.getOrderInfoByOrderId(map);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("name", productMap.get("name"));
        resultMap.put("phone", orderMap.get("phone"));
        resultMap.put("num", orderMap.get("num"));
        return resultMap;
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
        packageParams.put("appid", map.get("appid").toString());
        packageParams.put("attach", map.get("id").toString());
        packageParams.put("body", map.get("body").toString());
        packageParams.put("mch_id", map.get("mch_id").toString());
        packageParams.put("nonce_str", nonceStr);
        packageParams.put("notify_url", C_END_NOTIFY_URL);//支付成功后的回调地址
        packageParams.put("openid", map.get("openid").toString());//支付方式
        packageParams.put("out_trade_no", map.get("order").toString());//商户订单号
        packageParams.put("sign_type", WXPayConstants.MD5);
        packageParams.put("spbill_create_ip", getLocalIp());
        packageParams.put("total_fee", ServiceHelper.convertFee(map.get("total").toString()));//支付金额，这边需要转成字符串类型，否则后面的签名会失败
        packageParams.put("trade_type", TRADE_TYPE);//支付方式
        packageParams.put("sign", WXPayUtil.generateSignature(packageParams, map.get("key").toString()));
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
    Map generateDistributionPay(Map map) throws Exception {
        String nonceStr = WXPayUtil.generateNonceStr();
        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("appid", map.get("appid").toString());
        packageParams.put("attach", map.get("id").toString());
        packageParams.put("body", map.get("body").toString());
        packageParams.put("mch_id", map.get("mch_id").toString());
        packageParams.put("nonce_str", nonceStr);
        packageParams.put("notify_url", DISTRIBUTION_PARTNER_URL);//支付成功后的回调地址
        packageParams.put("openid", map.get("openid").toString());//支付方式
        packageParams.put("out_trade_no", map.get("order").toString());//商户订单号
        packageParams.put("sign_type", WXPayConstants.MD5);
        packageParams.put("spbill_create_ip", getLocalIp());
        packageParams.put("total_fee", ServiceHelper.convertFee(map.get("total").toString()));//支付金额，这边需要转成字符串类型，否则后面的签名会失败
        packageParams.put("trade_type", TRADE_TYPE);//支付方式
        packageParams.put("sign", WXPayUtil.generateSignature(packageParams, map.get("key").toString()));
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
    Map generateSign(String appId, String key, String nonceStr, String prepayId) throws Exception {
        Map returnClientMap = new HashMap<>();

        returnClientMap.put("appId", appId);
        returnClientMap.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
        returnClientMap.put("nonceStr", nonceStr);
        returnClientMap.put("package", String.format("prepay_id=%s", prepayId));
        returnClientMap.put("signType", WXPayConstants.MD5);
        returnClientMap.put("paySign", WXPayUtil.generateSignature(returnClientMap, key));

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
        if (prepayId.equals("-1")) {
            throw new GlobalProcessException("预支付流程失败");
        }
        return prepayId;
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
}
