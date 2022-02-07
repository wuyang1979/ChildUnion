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

    int updateProduct(ProductInfo productInfo);

    int addOtherImagesToProduct(Map<String, Object> map);

    int deleteProductById(Map map);

    List<LinkedHashMap> listProduct(@Param("card") int card, @Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> getOtherImagesById(@Param("productId") int productId);

    Map<String, Object> getReleaseCompanyInfoByCardId(Map map);

    List<LinkedHashMap> getReleaseCompanyInfoByCompanyId(Map map);
}
