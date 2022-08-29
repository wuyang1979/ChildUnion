package com.qinzi123.service.impl;

import com.qinzi123.dao.EstablishmentDao;
import com.qinzi123.dao.ProductDao;
import com.qinzi123.dao.ShopDao;
import com.qinzi123.dto.ShopInfo;
import com.qinzi123.service.ShopService;
import com.qinzi123.util.DateUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: ShopServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/3/22 0022 13:51
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class ShopServiceImpl extends AbstractWechatMiniProgramService implements ShopService {

    @Resource
    private ShopDao shopDao;
    @Resource
    private EstablishmentDao establishmentDao;
    @Resource
    private ProductDao productDao;

    @Override
    public List<LinkedHashMap> getShopInfoByCardId(Map map) {
        return shopDao.getShopInfoByCardId(map);
    }

    @Override
    public List<LinkedHashMap> checkShopName(Map map) {
        return shopDao.checkShopName(map);
    }

    @Override
    public List<LinkedHashMap> checkCompanyShop(Map map) {
        return shopDao.checkCompanyShop(map);
    }

    @Override
    public int addShop(Map map) {
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setCardId(Integer.parseInt(map.get("card").toString()));
        shopInfo.setName(map.get("shopName").toString());
        shopInfo.setCanWithdrawalMoney("0.00");
        shopInfo.setAlreadyWithdrawalMoney("0.00");
        shopInfo.setProgressWithdrawalMoney("0.00");
        shopInfo.setShopType(Integer.parseInt(map.get("shopType").toString()));
        shopInfo.setCreateTime(DateUtils.getAccurateDate());
        int rows = 0;
        if (shopInfo.getShopType() == 0) {
            rows = shopDao.addShop(shopInfo);
        } else if (shopInfo.getShopType() == 1) {
            rows = shopDao.addDistributionShop(shopInfo);
            //允许分销的商品自动加入分销小店
            List<LinkedHashMap> allowDistributionProductList = shopDao.getAllAllowDistributionProductList();
            allowDistributionProductList.forEach(item -> {
                Map<String, Object> map1 = new HashedMap();
                map1.put("productId", item.get("id"));
                Map<String, Object> paramsMap = new HashedMap();
                paramsMap.put("productId", item.get("id"));
                paramsMap.put("releaser_shop_id", productDao.getReleaserShopIdByProductId(map1));
                paramsMap.put("primary_shop_id", shopInfo.getId());
                paramsMap.put("create_time", DateUtils.getAccurateDate());
                productDao.addDistributionRecord(paramsMap);
            });
        }
        //店长自动成为核销员
        Map<String, Object> userInfo = shopDao.getUserInfoByCardId(shopInfo.getCardId());
        Map<String, Object> params = new HashedMap();
        params.put("open_id", userInfo.get("openid"));
        params.put("shop_id", shopInfo.getId());
        params.put("nick_name", userInfo.get("realname"));
        params.put("head_img_url", userInfo.get("headimgurl"));
        params.put("create_time", DateUtils.getAccurateDate());
        shopDao.addShopownerToWriteOff(params);
        return rows;
    }

    @Override
    public int updateShopInfoById(Map map) {
        return shopDao.updateShopInfoById(map);
    }

    @Override
    public List<LinkedHashMap> getShopInfo(Map map) {
        return shopDao.getShopInfo(map);
    }

    @Override
    public Map<String, Object> addWriteOffClerk(Map map) throws Exception {
        List<LinkedHashMap> clerkList = establishmentDao.getWriteOffClerkByOpenId(map);
        Map<String, Object> resultMap = new HashMap<>();
        if (clerkList.size() > 0) {
            resultMap.put("result", -1);
            return resultMap;
        }
        map.put("createTime", DateUtils.getAccurateDate());
        int rows = establishmentDao.addWriteOffClerk(map);
        resultMap.put("result", rows);
        return resultMap;
    }

    @Override
    public List<LinkedHashMap> getAllWriteOffClerkListByCompanyId(Map map) {
        return shopDao.getAllWriteOffClerkListByCompanyId(map);
    }

    @Override
    public Map<String, Object> getShopShowInfoById(Map map) {
        int shopId = Integer.parseInt(map.get("shopId").toString());
        Map<String, Object> shopInfo = shopDao.getShopInfo(map).get(0);
        Map<String, Object> resultMap = new HashedMap();
        resultMap.put("logopic", establishmentDao.getLogoByShopId(shopId));
        resultMap.put("shopName", shopInfo.get("name"));
        resultMap.put("phone", shopDao.getShopownerPhoneByShopId(shopId));
        resultMap.put("visitCount", shopDao.getVisitCountByShopId(shopId));
        resultMap.put("shopType", shopInfo.get("shop_type"));
        return resultMap;
    }

    @Override
    public Map<String, Object> getShopTypeByCardId(Map map) {
        return shopDao.getShopTypeByCardId(map);
    }

    @Override
    public List<LinkedHashMap> listProuct(int shopId, int start, int num) {
        List<LinkedHashMap> list = shopDao.listProduct(shopId, start, num);
        list.forEach(item -> {
            int issuerShopId = Integer.parseInt(item.get("shop_id").toString());
            item.put("disShopId", shopId == issuerShopId ? "-1" : shopId);
        });
        return list;
    }

    @Override
    public Map<String, Object> getCompanyIdInnerShopByProductId(Map map) {
        Map<String, Object> resultMap = new HashedMap();
        resultMap.put("companyId", shopDao.getCompanyIdInnerShopByProductId(map));
        return resultMap;
    }

    @Override
    public Map<String, Object> getQrCodeByProductId(Map map) {
        Map<String, Object> resultMap = new HashedMap();
        resultMap.put("qrUrl", shopDao.getQrCodeByProductId(map));
        return resultMap;
    }

    @Override
    public int addVisitCountByShopId(Map map) {
        return shopDao.addVisitCountByShopId(map);
    }

}
