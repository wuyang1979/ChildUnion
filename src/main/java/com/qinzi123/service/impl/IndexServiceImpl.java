package com.qinzi123.service.impl;

import com.github.wxpay.sdk.WXPayUtil;
import com.qinzi123.dao.IndexDao;
import com.qinzi123.dao.ProductDao;
import com.qinzi123.dao.ShopDao;
import com.qinzi123.dao.UserInfoDao;
import com.qinzi123.service.IndexService;
import com.qinzi123.util.DateUtils;
import com.qinzi123.util.Utils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @title: IndexServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/2/10 0010 10:03
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class IndexServiceImpl extends AbstractWechatMiniProgramService implements IndexService {

    @Resource
    private IndexDao indexDao;

    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private ShopDao shopDao;
    @Resource
    private ProductDao productDao;

    @Override
    public List<LinkedHashMap> getUserInfoById(String id) {
        List<LinkedHashMap> list = indexDao.getUserInfoById(id);
        list.forEach(item -> {
            byte[] decode = Base64.getDecoder().decode(item.get("nick_name").toString());
            item.put("nick_name", new String(decode));
        });
        return list;
    }

    @Override
    public int updatePhoneByCard(Map map) {
        return indexDao.updatePhoneByCard(map);
    }

    @Override
    public int addDistributionPartnerOrder(Map map) {
        map.put("createTime", DateUtils.getAccurateDate());
        map.put("orderNo", Utils.getCurrentDateNoFlag());
        indexDao.addDistributionPartnerOrder(map);
        return Integer.parseInt(map.get("id").toString());
    }

    @Override
    public int addDistributionRecordForShopkeeper(Map map) {
        String createTime = DateUtils.getAccurateDate();
        map.put("createTime", createTime);
        map.put("payTime", createTime);
        map.put("orderNo", Utils.getCurrentDateNoFlag());
        return indexDao.addFreeDistributionPartnerOrder(map);
    }

    @Override
    public int addOrUpdateNewMembersJoinAuthAcceptRecord(Map map) {
        String userId = map.get("userId").toString();
        List<LinkedHashMap> newMembersJoinAuthList = indexDao.getNewMembersJoinAuthListByUserId(userId);
        int rows;
        if (newMembersJoinAuthList.size() == 0) {
            Map paramsMap = new HashMap();
            paramsMap.put("userId", userId);
            paramsMap.put("type", 1);
            paramsMap.put("authStatus", 1);
            paramsMap.put("createTime", DateUtils.getAccurateDate());
            rows = indexDao.addCEndAuthAcceptRecord(paramsMap);
        } else {
            rows = indexDao.updateNewMembersJoinAuthStatusByUserId(userId);
        }
        return rows;
    }

    @Override
    public List<LinkedHashMap> getDistributionRecordByUserId(Map map) {
        return indexDao.getDistributionRecordByUserId(map);
    }

    @Override
    public Map<String, Object> registerNewUser(Map map) {
        String id = getUuid();
        int beOffline = 0;
        if (map.get("beOffline") != null) {
            beOffline = Integer.parseInt(map.get("beOffline").toString());
        }

        map.put("id", id);
        map.put("createTime", DateUtils.getDateTime());
        String nickNameBase64 = Base64.getEncoder().encodeToString(map.get("nickName").toString().getBytes());
        map.put("nickName", nickNameBase64);
        int rows = indexDao.registerNewUser(map);
        Map<String, Object> resultMap = new HashMap<>();
        if (rows == 1) {
            //新用户是否成为下线  0：否；1：是
            if (beOffline == 1) {
                if (StringUtils.isEmpty(map.get("posterSharerPhone").toString())) {
                    int onlineShopId = Integer.parseInt(map.get("onlineShopId").toString());
                    List<LinkedHashMap> shopList = indexDao.getShopListByShopId(onlineShopId);
                    if (shopList.size() > 0) {
                        Map<String, Object> paramsMap = new HashedMap();
                        paramsMap.put("offlineUserId", id);
                        paramsMap.put("onlineShopId", onlineShopId);
                        paramsMap.put("onlineCardId", shopList.get(0).get("card_id"));
                        paramsMap.put("createTime", DateUtils.getAccurateDate());
                        indexDao.addOfflineRecord(paramsMap);
                    }
                } else {
                    String posterSharerPhone = map.get("posterSharerPhone").toString();
                    List<LinkedHashMap> shopList = indexDao.getShopListByPhone(posterSharerPhone);
                    //根据分享海报人的手机号码判断是否开通过小店，只有开通过小店的店主才能发展下线
                    if (shopList.size() > 0) {
                        Map<String, Object> paramsMap = new HashedMap();
                        paramsMap.put("offlineUserId", id);
                        paramsMap.put("onlineShopId", shopList.get(0).get("id"));
                        paramsMap.put("onlineCardId", shopList.get(0).get("card_id"));
                        paramsMap.put("createTime", DateUtils.getAccurateDate());
                        indexDao.addOfflineRecord(paramsMap);
                    }
                }
            }
            resultMap.put("id", id);
            return resultMap;
        } else {
            return null;
        }
    }

    @Override
    public Map getCertificateNum(Map map) {
        Map<String, Object> resultMap = new HashedMap();
        resultMap.put("certificateNum", indexDao.getCertificateNum(map));
        return resultMap;
    }

    @Override
    public List<LinkedHashMap> getDistributionPartnerListByUserId(Map map) {
        return indexDao.getDistributionPartnerListByUserId(map);
    }

    @Override
    public List<LinkedHashMap> getShopListByUserId(Map map) {
        return indexDao.getShopListByUserId(map);
    }

    @Override
    public List<LinkedHashMap> getShopListByUserId2(Map map) {
        return indexDao.getShopListByUserId(map);
    }

    @Override
    public List<LinkedHashMap> getDistributionShopByUserId(Map map) {
        List<LinkedHashMap> shopList = indexDao.getDistributionShopByUserId(map);
        List<LinkedHashMap> distributionPartnerList = indexDao.getDistributionPartnerListByUserId(map);
        shopList.addAll(distributionPartnerList);
        return shopList;
    }

    @Override
    public List<LinkedHashMap> getCEndUnAuthRecordList(Map map) {
        return indexDao.getCEndUnAuthRecordList(map);
    }

    public String getUuid() {
        return indexDao.getUuid();
    }

    @Override
    public int test() {
        List<LinkedHashMap> distributionShopList = indexDao.getAllDistributionShopList();
        List<LinkedHashMap> allowDistributionProductList = shopDao.getAllAllowDistributionProductList();
        distributionShopList.forEach(shopItem -> {
            indexDao.deleteDistributionRecordByDistributionShopId(Integer.parseInt(shopItem.get("id").toString()));
            allowDistributionProductList.forEach(item -> {
                Map<String, Object> map1 = new HashedMap();
                map1.put("productId", item.get("id"));
                Map<String, Object> paramsMap = new HashedMap();
                paramsMap.put("productId", item.get("id"));
                paramsMap.put("releaser_shop_id", productDao.getReleaserShopIdByProductId(map1));
                paramsMap.put("primary_shop_id", shopItem.get("id"));
                paramsMap.put("create_time", DateUtils.getAccurateDate());
                productDao.addDistributionRecord(paramsMap);
            });
        });

        return 1;
    }
}
