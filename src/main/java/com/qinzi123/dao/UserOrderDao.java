package com.qinzi123.dao;

import org.apache.ibatis.annotations.Param;

import javax.annotation.security.PermitAll;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: UserOrderDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/2/16 0016 11:21
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface UserOrderDao {

    Map<String, Object> getUserInfoById(Map map);

    Map getProductInfoByProductId(Map map);

    Map<String, Object> getProductStandardInfoByStandardId(@Param("standardId") int standardId);

    Map<String, Object> getActivityStandardInfoByStandardId(@Param("standardId") int standardId);

    String getIssuerPhoneByProductId(@Param("productId") int productId);

    List<LinkedHashMap> getPrimaryDistributionShopIdByPhone(@Param("phone") String phone);

    List<LinkedHashMap> getDistributionPartnerListByPhone(@Param("phone") String phone);

    String getOpenIdByPhone(@Param("posterSharerPhone") String posterSharerPhone);

    String getPhoneByUserId(@Param("userId") String userId);

    int addUserOrder(Map map);

    int addUserFreeOrder(Map map);

    int addUserActivityOrder(Map map);

    int addUserFreeActivityOrder(Map map);

    int reduceProductInventory(Map map);

    int reduceActivityInventory(Map map);

    int updateClientEndOrder(Map map);

    int updateClientEndDistributionPartnerOrder(Map map);

    Map<String, Object> getProductInfoByOrderId(Map map);

    Map<String, Object> getOrderInfoByOrderId(Map map);

    int checkOrderStatus(Map map);

    int updateIssuerShopWithdrawalAmount(Map map);

    String getPrimaryDistributionUserIdByOpenId(@Param("openId") String openId);

    int updateExclusiveIssuerShopWithdrawalAmount(Map map);

    int updateDistributorShopWithdrawalAmount(Map map);

    int updateDistributionPartnerAccountByUserId(Map map);

    int updateDistributorShopWithdrawalAmountToAlready(Map map);

    String getTargetOpenIdByPrimaryDistributionShopId(@Param("shopId") int shopId);

    int addClientEndDistributorWithdrawalRecord(Map map);

    int updateExclusiveDistributorShopWithdrawalAmount(Map map);

    int getShopIdByProductId(Map map);

    int getTypeByShopId(@Param("shopId") int shopId);

    Map getIssuerShopInfoByShopId(@Param("shopId") int shopId);

    int getShopTypeByShopId(@Param("shopId") int shopId);

    Map<String, Object> getStandardInfoByOrderId(Map map);

    Map<String, Object> getProductStandardInfoByOrderId(Map map);

    Map<String, Object> getOrderInfoById(@Param("orderId") int orderId);

    Map<String, Object> getAddressInfoById(Map map);

    int updateProductInfoById(Map map);

    int updateProductBuyCountById(Map map);

    List<LinkedHashMap> getReceiveAddress(Map map);

    List<LinkedHashMap> getAllOrderList(@Param("userId") String userId, @Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> getSelectedOrderList(@Param("userId") String userId, @Param("selectType") int selectType, @Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> getWriteOffClerkByOpenId(Map map);

    List<LinkedHashMap> getPendingPayList();

    List<LinkedHashMap> getOrderListByUserIdAndProductId(Map map);

    int confirmReceipt(Map map);

    int writeOffUserOrder(Map map);

    int updateOrderStatusById(Map map);

    Map getShopFormByOrderId(@Param("orderId") int orderId);

    List<LinkedHashMap> getAllUnConfirmReceiveOrderList(Map map);

    int autoConfirmReceiveProductOrder(Map map);

    int updateProductInventoryByStandardId(@Param("standardId") int standard, @Param("num") int num);

    int updateActivityInventoryByStandardId(@Param("standardId") int standard, @Param("num") int num);

    Map getProductNameByOrderId(Map map);

    Map getCompanyIdByOrderId(Map map);

    Map getProductIdByOrderId(Map map);

    Map getReceiveAddressByUserId(@Param("userId") String userId);

    int addCertificateInfo(Map map);

    String getUserIdByOpenId(@Param("openId") String openId);

    List<LinkedHashMap> getOrderListByProductIdAndPhone(Map map);

    int updateOrderInfoByProductIdAndPhone(Map map);

    String getOpenIdByOrderId(@Param("orderId") int orderId);

    int getStandardIdByOrderId(@Param("orderId") int orderId);

    String getUserIdByOrderId(@Param("orderId") int orderId);

    int getShopIdByCard(@Param("card") int card);

    int getShopIdByOrderId(@Param("orderId") int orderId);

    Map<String, Object> loadBuyerInfoByOpenId(@Param("openId") String openId);

    Map<String, Object> loadStandardByStandardId(@Param("standardId") int standardId);

    Map<String, Object> loadReceiveAddressByUserId(@Param("userId") String userId);

}
