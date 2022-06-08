package com.qinzi123.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: CertificateService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/3/16 0010 10:02
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface CertificateService {

    List<LinkedHashMap> getCertificateList(String userId, int start, int num);

    Map<String, Object> getProductInfoByProductId(Map map);

    int getChildrenNumById(Map map);

    Map<String, Object> getCertificateInfoById(int id);

    String getActivityNameById(Map map);

    String getInstitutionNameById(Map map);
}
