package com.qinzi123.dao;

import com.qinzi123.dto.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: ProductDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/1/22 0022 11:30
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface ProductDao {

    int addProduct(ProductInfo productInfo);

    int addActivity(ProductInfo productInfo);

    int updateProduct(ProductInfo productInfo);

    int updateActivity(ProductInfo productInfo);

    int addOtherImagesToProduct(Map<String, Object> map);

    int deleteStandardByStandardId(@Param("standardId") int standardId);

    int deleteProductStandardByStandardId(@Param("standardId") int standardId);

    List<LinkedHashMap> getWriteOffListByCard(@Param("cardId") int cardId);

    List<LinkedHashMap> getAllDistributionShopList();

    List<LinkedHashMap> getOldStandardListByProductId(@Param("productId") int productId);

    List<LinkedHashMap> getOldActivityStandardListByProductId(@Param("productId") int productId);

    Map getUserInfoById(@Param("cardId") int cardId);

    int addActivityStandards(Map<String, Object> map);

    int updateActivityStandards(Map<String, Object> map);

    int updateProductStandards(Map<String, Object> map);

    int addProductStandards(Map<String, Object> map);

    int addWriteOffClerk(Map map);

    Map<String, Object> getBuyCountById(Map map);

    int deleteProductById(Map map);

    int upProduct(Map map);

    int upActivity(Map map);

    int downProduct(Map map);

    int downActivity(Map map);

    List<LinkedHashMap> listProduct(@Param("card") int card, @Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> listActivity(@Param("card") int card, @Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> listReleasedProduct(@Param("card") int card, @Param("start") int start, @Param("num") int num);

    int getShopFormByShopId(@Param("shopId") int shopId);

    List<LinkedHashMap> listDistributionProduct(@Param("card") int card, @Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> listSearchDistributionProduct(@Param("card") int card, @Param("start") int start, @Param("num") int num, @Param("searchValue") String searchValue);

    List<LinkedHashMap> listSearchReleasedProduct(@Param("card") int card, @Param("start") int start, @Param("num") int num, @Param("searchValue") String searchValue);

    int getPictureCountByProductId(Map map);

    List<LinkedHashMap> getAllListProduct(@Param("type") int type, @Param("activityType1") String activityType1, @Param("activityType2") String activityType2, @Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> getExclusiveAllList(@Param("shopId") int shopId, @Param("type") int type, @Param("start") int start, @Param("num") int num);

    String getCompanyNameByCompanyId(Map map);

    int getReleaserShopIdByProductId(Map map);

    int getShopIdByCard(@Param("card") int card);

    List<LinkedHashMap> getAllAllianceProductList(@Param("card") int card, @Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> getSearchAllianceProductList(@Param("card") int card, @Param("start") int start, @Param("num") int num, @Param("searchValue") String searchValue);

    List<LinkedHashMap> getSearchAllianceProductListOrderByBuyCount(@Param("card") int card, @Param("start") int start, @Param("num") int num, @Param("searchValue") String searchValue);

    List<LinkedHashMap> getAllianceProductListOrderByBuyCount(@Param("card") int card, @Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> getSearchList(Map map);

    List<LinkedHashMap> getExclusiveSearchList(Map map);

    List<LinkedHashMap> getOtherImagesById(@Param("productId") int productId);

    Map<String, Object> getReleaseShopInfoByShopId(Map map);

    List<LinkedHashMap> getReleaseCompanyInfoByCompanyId(Map map);

    int getOrderCountByproductId(Map map);

    List<LinkedHashMap> getStandardListByProductId(Map map);

    int getProductOrderCountByStandardId(@Param("standardId") int standardId);

    int getActivityOrderCountByStandardId(@Param("standardId") int standardId);

    List<LinkedHashMap> getProductStandardListByProductId(Map map);

    List<LinkedHashMap> getStandardsList(Map map);

    List<LinkedHashMap> getStandardsListOrderByDistribution(Map map);

    List<LinkedHashMap> getStandardsListOrderByDistributionMin(Map map);

    List<LinkedHashMap> getProductStandardsList(Map map);

    String getShopNameByShopId(@Param("shopId") int shopId);

    List<LinkedHashMap> getProductStandardsListOrderByDistribution(Map map);

    List<LinkedHashMap> getProductStandardsListOrderByDistributionMin(Map map);

    List<LinkedHashMap> getDistributionProductListByShopId(@Param("shopId") int shopId);

    int addDistributionRecord(Map map);

    int deleteDistributionRecordByProductId(@Param("productId") int productId);

    String getProductInventoryByStandardId(Map map);

    String getActivityInventoryByStandardId(Map map);

    List<LinkedHashMap> getPeddingPayCouontByCardId(Map map);

    List<LinkedHashMap> getPeddingPayCouontByUserId(Map map);

    List<LinkedHashMap> getPeddingconfirmByCardId(Map map);

    List<LinkedHashMap> getPeddingconfirmByUserId(Map map);

    List<LinkedHashMap> getAllOrderList(@Param("card") int card, @Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> getAllDistributionOrderList(@Param("userId") String userId, @Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> getSelectedOrderList(@Param("card") int card, @Param("selectType") int selectType, @Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> getSelectedDistributionOrderList(@Param("userId") String userId, @Param("selectType") int selectType, @Param("start") int start, @Param("num") int num);

    int getCardByUserId(@Param("userId") String userId);

    Map<String, Object> getProductInfoByID(Map map);

    Map<String, Object> getUserInfoByOpenID(Map map);

    Map getProductInfoByOrderId(@Param("orderId") int orderId);

    Map loadProductInfoById(Map map);

    int getIssuerShopIdByProductId(@Param("productId") int productId);

    Map<String, Object> getIssuerShopInfoByProductId(@Param("productId") int productId);

    int getMyShopIdByCard(@Param("card") int card);

    String getPhoneByUserId(@Param("userId") String userId);

    List<LinkedHashMap> getAllActivityList(@Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> getSearchActivityList(Map map);

    List<LinkedHashMap> getRecommendProductList(Map map);

    List<LinkedHashMap> getRecommendProductListByShopId(Map map);

    List<LinkedHashMap> getActivityPictureByProductId(Map map);

    int deleteAitivityPictureByProductId(Map map);

    int addAvtivityPicture(Map map);

    int deleteDistributionRecord(Map map);

    int checkProductStatus(Map map);
}
