package com.qinzi123.service;

import com.qinzi123.dto.EnterpriseInfo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: EntityService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface EntityService {

    int addService(String tableName, Map<String, Object> map);

    int getProductId(String name);

    int getBaseId(String name);

    int addBaseService(Map<String, Object> map);

    int addEnterpriseService(EnterpriseInfo enterpriseInfo);

    int addOtherImages(Map<String, Object> map);

    int addOtherImagesToBase(Map<String, Object> map);

    int addOtherImagesToEnterprise(Map<String, Object> map);

    int addOtherImagesToProductInfo(Map<String, Object> map);

    int addEstablishmentService(Map<String, Object> map);

    int addProductInfoService(Map<String, Object> map);

    int addCEndOrderService(Map<String, Object> map);

    int addOtherImagesToProduct(Map<String, Object> map);

    int deleteBasePicList(Map<String, Object> map);

    int deleteEnterprisePicList(Map<String, Object> map);

    int deleteProductPicList(Map<String, Object> map);

    int deleteProductInfoPicList(Map<String, Object> map);

    int updateService(String tableName, Map<String, Object> map);

    int updateBaseService(Map<String, Object> map);

    int updateProductService(Map<String, Object> map);

    int updateEnterpriseService(Map<String, Object> map);

    int updateEstablishmentService(Map<String, Object> map);

    int updateProductInfoService(Map<String, Object> map);

    int updateCEndOrderService(Map<String, Object> map);

    int deleteService(String tableName, List<Map<String, Object>> list);

    int deleteBaseService(List<Map<String, Object>> list);

    int deleteBaseReserveService(List<Map<String, Object>> list);

    int deleteEnterpriseService(List<Map<String, Object>> list);

    int deleteEnterpriseConsultingService(List<Map<String, Object>> list);

    int deleteEnterpriseCommentService(List<Map<String, Object>> list);

    int deleteEstablishmentService(List<Map<String, Object>> list);

    int deleteProductInfoService(List<Map<String, Object>> list);

    int deleteCEndOrderService(List<Map<String, Object>> list);

    List<LinkedHashMap> showService(String tableName, Map<String, Object> map);

    List<LinkedHashMap> showAllService(String tableName);

    List<LinkedHashMap> showBaseService(String tableName);

    List<LinkedHashMap> showSpecialService(Map<String, Object> map);

    List<LinkedHashMap> showSaaSService(String tableName, String condition);

    List<LinkedHashMap> pictureList(int baseId);

    List<LinkedHashMap> campaignPictureList(int productId);

    List<LinkedHashMap> productPictureList(int productId);

    List<LinkedHashMap> enterprisePictureList(int enterpriseId);

    List<LinkedHashMap> getLuckDrawMemberList();

    List<LinkedHashMap> getOutsideLuckDrawMemberList();

    int clearAllJoiner();
}


