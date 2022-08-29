package com.qinzi123.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: ProductService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/1/22 0022 11:29
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface ProductService {

    int addProduct(Map map);

    int addActivity(Map map);

    Map<String, Object> getBuyCountById(Map map);

    int updateProduct(Map map);

    int updateActivity(Map map);

    int deleteProductById(Map map);

    int upProduct(Map map);

    int upActivity(Map map);

    int downProduct(Map map);

    int downActivity(Map map);

    List<LinkedHashMap> listProduct(int card, int start, int num);

    List<LinkedHashMap> listActivity(int card, int start, int num);

    List<LinkedHashMap> listReleasedProduct(int card, int start, int num);

    List<LinkedHashMap> listDistributionProduct(int card, int start, int num);

    List<LinkedHashMap> listSearchDistributionProduct(int card, int start, int num, String searchValue);

    List<LinkedHashMap> listSearchReleasedProduct(int card, int start, int num, String searchValue);

    List<LinkedHashMap> getAllListProduct(int type, String activityType1, String activityType2, int start, int num);

    List<LinkedHashMap> getExclusiveAllList(int shopId, int type, int start, int num);

    String getCompanyNameByCompanyId(Map map);

    List<LinkedHashMap> getAllAllianceProductList(int card, int start, int num);

    List<LinkedHashMap> getAllianceProductListOrderByBuyCount(int card, int start, int num);

    List<LinkedHashMap> getSearchAllianceProductList(int card, int start, int num, String searchValue);

    List<LinkedHashMap> getSearchAllianceProductListOrderByBuyCount(int card, int start, int num, String searchValue);

    int addDistributionRecord(Map map);

    List<LinkedHashMap> getSearchList(Map map);

    List<LinkedHashMap> getExclusiveSearchList(Map map);

    List<LinkedHashMap> getOtherImagesById(int productId);

    Map<String, Object> getReleaseShopInfoByShopId(Map map);

    List<LinkedHashMap> getReleaseCompanyInfoByCompanyId(Map map);

    List<LinkedHashMap> getSelectedOrderList(Map map);

    List<LinkedHashMap> getSelectedOrderListByUserId(Map map);

    Map<String, Object> getOrderCount(Map map);

    Map<String, Object> loadOrderCountByUserId(Map map);

    int getInventoryByStandardId(Map map);

    List<LinkedHashMap> getStandardListByProductId(Map map);

    int getOrderCountByproductId(Map map);

    List<LinkedHashMap> getProductStandardListByProductId(Map map);

    List<LinkedHashMap> getStandardsList(Map map);

    Map loadProductInfoById(Map map);

    int getIssuerShopIdByProductId(int productId);

    Map<String, Object> getIssuerShopInfoByProductId(int productId);

    int getMyShopIdByCard(int card);

    String getPhoneByUserId(String userId);

    List<LinkedHashMap> getAllActivityList(int start, int num);

    List<LinkedHashMap> getSearchActivityList(Map map);

    List<LinkedHashMap> getRecommendProductList(Map map);

    List<LinkedHashMap> getRecommendProductListByShopId(Map map);

    List<LinkedHashMap> getActivityPictureByProductId(Map map);

    int saveActivityPictureById(Map map);

    int deleteDistributionRecord(Map map);

    int checkProductStatus(Map map);
}
