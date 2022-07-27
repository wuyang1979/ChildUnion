package com.qinzi123.dao;

import com.qinzi123.dto.ActivityStandardDto;
import com.qinzi123.dto.EnterpriseInfo;
import com.qinzi123.dto.ProductInfo;
import com.qinzi123.dto.ProductStandardDto;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: EntityDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface EntityDao {

    int addEntity(@Param("tableName") String tableName, @Param("tableColumns") String tableColumns, @Param("tableValues") String tableValues);

    int updateEntity(@Param("tableName") String tableName, @Param("updateColumns") String updateColumns, @Param("keyColumns") String keyColumns);

    int updateBaseEntity(Map<String, Object> map);

    int updateEnterpriseEntity(Map<String, Object> map);

    int updateEstablishmentEntity(Map<String, Object> map);

    int updateProductInfoService(Map<String, Object> map);

    int updateCEndOrderService(Map<String, Object> map);

    int updateShopInfoService(Map<String, Object> map);

    int updateProductEntity(Map<String, Object> map);

    int deleteEntity(@Param("tableName") String tableName, @Param("keyColumns") String keyColumns);

    int deleteBaseEntity(Map<String, Object> map);

    int deleteBaseReserveEntity(Map<String, Object> map);

    int deleteEnterpriseService(Map<String, Object> map);

    int deleteEnterpriseConsultingService(Map<String, Object> map);

    int deleteEnterpriseCommentService(Map<String, Object> map);

    int deleteEstablishmentService(Map<String, Object> map);

    int deleteProductInfoService(Map<String, Object> map);

    int deleteProductStandardsService(Map<String, Object> map);

    int deleteActivityStandardsService(Map<String, Object> map);

    int deleteProductPicturesService(Map<String, Object> map);

    int deleteDistributionRecordsService(Map<String, Object> map);

    int deleteCEndOrderService(Map<String, Object> map);

    int deleteShopInfoService(Map<String, Object> map);

    List<LinkedHashMap> findEntityByKey(@Param("tableName") String tableName, @Param("keyColumns") String keyColumns);

    List<LinkedHashMap> findEntitys(@Param("tableName") String tableName);

    List<LinkedHashMap> getProductStandardByProductId(@Param("productId") int productId);

    List<LinkedHashMap> getAcitivityStandardByProductId(@Param("productId") int productId);

    List<LinkedHashMap> findBaseEntity(@Param("tableName") String tableName);

    List<LinkedHashMap> findSpecialEntitys(@Param("selectList") String selectList, @Param("tableName") String tableName);

    int addOtherImages(Map<String, Object> map);

    int addOtherImagesToBase(Map<String, Object> map);

    int addOtherImagesToEnterprise(Map<String, Object> map);

    int addOtherImagesToProductInfo(Map<String, Object> map);

    int addOtherImagesToProduct(Map<String, Object> map);

    int getProductId(@Param("name") String name);

    int getBaseId(@Param("name") String name);

    int addBaseService(Map<String, Object> map);

    int addEnterpriseService(EnterpriseInfo enterpriseInfo);

    int addEstablishmentService(Map<String, Object> map);

    int addProductStandard(ProductStandardDto productStandardDto);

    int addActivityStandard(ActivityStandardDto activityStandardDto);

    int addProductInfoService(ProductInfo productInfo);

    int addActivityInfoService(ProductInfo productInfo);

    int addCEndOrderService(Map map);

    int addShopInfoService(Map map);

    List<LinkedHashMap> pictureList(@Param("baseId") int baseId);

    int deleteBasePicList(Map<String, Object> map);

    int deleteEnterprisePicList(Map<String, Object> map);

    int deleteProductInfoPicList(Map<String, Object> map);

    int deleteProductPicList(Map<String, Object> map);

    List<LinkedHashMap> campaignPictureList(int productId);

    List<LinkedHashMap> productPictureList(int productId);

    List<LinkedHashMap> enterprisePictureList(@Param("enterpriseId") int enterpriseId);

    List<LinkedHashMap> getLuckDrawMemberList();

    List<LinkedHashMap> getOutsideLuckDrawMemberList();

    int clearAllJoiner();

    List<LinkedHashMap> getProductStandards();

    List<LinkedHashMap> getActivityStandards();

}
