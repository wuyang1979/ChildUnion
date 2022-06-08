package com.qinzi123.dao;

import com.qinzi123.dto.ShopInfo;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: ShopDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/3/22 0022 13:52
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface ShopDao {

    List<LinkedHashMap> getShopInfoByCardId(Map map);

    List<LinkedHashMap> checkShopName(Map map);

    List<LinkedHashMap> checkCompanyShop(Map map);

    List<LinkedHashMap> getAllAllowDistributionProductList();

    int addShop(ShopInfo shopInfo);

    int addDistributionShop(ShopInfo shopInfo);

    int updateShopInfoById(Map map);

    List<LinkedHashMap> getShopInfo(Map map);

    String getShopownerPhoneByShopId(@Param("shopId") int shopId);

    int getVisitCountByShopId(@Param("shopId") int shopId);

    List<LinkedHashMap> getAllWriteOffClerkListByCompanyId(Map map);

    List<LinkedHashMap> listProduct(@Param("shopId") int shopId, @Param("start") int start, @Param("num") int num);

    int getCompanyIdInnerShopByProductId(Map map);

    String getQrCodeByProductId(Map map);

    Map getUserInfoByCardId(@Param("cardId") int cardId);

    Map getShopTypeByCardId(Map map);

    int addShopownerToWriteOff(Map map);

    int addVisitCountByShopId(Map map);

}
