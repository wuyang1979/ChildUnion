package com.qinzi123.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: IndexService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/2/10 0010 10:02
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface IndexService {

    List<LinkedHashMap> getUserInfoById(String id);

    int updatePhoneByCard(Map map);

    int addDistributionPartnerOrder(Map map);

    Map<String, Object> registerNewUser(Map map);

    Map getCertificateNum(Map map);

    List<LinkedHashMap> getDistributionPartnerListByUserId(Map map);

    List<LinkedHashMap> getShopListByUserId2(Map map);

    List<LinkedHashMap> getDistributionShopByUserId(Map map);

    int test();
}
