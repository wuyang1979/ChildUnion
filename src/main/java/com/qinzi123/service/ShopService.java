package com.qinzi123.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: ShopService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/3/22 0022 13:50
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface ShopService {

    List<LinkedHashMap> getShopInfoByCardId(Map map);

    List<LinkedHashMap> checkShopName(Map map);

    List<LinkedHashMap> checkCompanyShop(Map map);

    int addShop(Map map);

    int updateShopInfoById(Map map);

    List<LinkedHashMap> getShopInfo(Map map);

    Map<String, Object> addWriteOffClerk(Map map) throws Exception;

    List<LinkedHashMap> getAllWriteOffClerkListByCompanyId(Map map);

    Map<String, Object> getShopShowInfoById(Map map);

    Map<String, Object> getShopTypeByCardId(Map map);

    List<LinkedHashMap> listProuct(int shopId, int start, int num);

    Map<String, Object> getCompanyIdInnerShopByProductId(Map map);

    Map<String, Object> getQrCodeByProductId(Map map);

    int addVisitCountByShopId(Map map);

}
