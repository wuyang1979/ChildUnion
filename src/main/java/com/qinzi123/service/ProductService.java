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

    int updateProduct(Map map);

    int deleteProductById(Map map);

    List<LinkedHashMap> listProduct(int card, int start, int num);

    List<LinkedHashMap> getOtherImagesById(int productId);

    Map<String, Object> getReleaseCompanyInfoByCardId(Map map);

    List<LinkedHashMap> getReleaseCompanyInfoByCompanyId(Map map);
}
