package com.qinzi123.dao;

import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: IndexDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description:
 * @author: jie.yuan
 * @date: 2022/2/10 0010 10:05
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface IndexDao {

    List<LinkedHashMap> getUserInfoById(@Param("id") String id);

    int updatePhoneByCard(Map map);

    int addDistributionPartnerOrder(Map map);

    String getUuid();

    int registerNewUser(Map map);

    int addOfflineRecord(Map map);

    List<LinkedHashMap> getShopListByPhone(@Param("phone") String phone);

    List<LinkedHashMap> getShopListByShopId(@Param("shopId") int shopId);

    String getOrderNoById(Map map);

    String getDistributionPartnerOrderNoById(Map map);

    int getCertificateNum(Map map);

    List<LinkedHashMap> getShopListByUserId(Map map);

    List<LinkedHashMap> getDistributionShopByUserId(Map map);

    List<LinkedHashMap> getDistributionPartnerListByUserId(Map map);

    List<LinkedHashMap> getAllDistributionShopList();

    int deleteDistributionRecordByDistributionShopId(@Param("shopId") int shopId);
}
