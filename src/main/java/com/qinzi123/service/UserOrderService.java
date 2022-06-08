package com.qinzi123.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: UserOrderService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/2/16 0016 11:19
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface UserOrderService {

    int addUserOrder(Map map);

    int addUserActivityOrder(Map map);

    int addUserFreeOrder(HashMap map);

    int addUserFreeActivityOrder(Map map);

    Map getShowInfo(Map map);

    Map prepay(Map map);

    Map joinDistributionPartnerPrePay(Map map);

    List<LinkedHashMap> getReceiveAddress(Map map);

    List<LinkedHashMap> getSelectedOrderList(Map map);

    List<LinkedHashMap> getWriteOffClerkByOpenId(Map map);

    Map<String, Object> checkWhiteOffInfo(Map map) throws Exception;

    int confirmReceipt(Map map) throws Exception;

    int checkOrderStatus(Map map);

    int writeOffUserOrder(Map map) throws Exception;

    Map getProductNameByOrderId(Map map);

    List<LinkedHashMap> getPendingPayList();

    List<LinkedHashMap> getOrderListByUserIdAndProductId(Map map);

    int updateOrderStatusById(Map map);

    int refreshOrder();

    int updateProductInventoryByStandardId(int standardId, int num);

    int updateActivityInventoryByStandardId(int standardId, int num);

    Map<String, Object> updateOrderByProductIdAndPhone(Map map);

    Map<String, Object> loadBuyerInfoByOrderId(Map map);

    Map<String, Object> loadStandardByOrderId(Map map);

    Map<String, Object> loadReceiveAddressByOrderId(Map map);

    Map<String, Object> differentiateSupplier(Map map);

}
