package com.qinzi123.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.jni.Mmap;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: CertificateDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/3/16 0010 10:05
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface CertificateDao {

    List<LinkedHashMap> getCertificateList(@Param("userId") String userId, @Param("start") int start, @Param("num") int num);

    Map<String, Object> getProductInfoByProductId(Map map);

    int getChildrenNumById(Map map);

    Map<String, Object> getCertificateInfoById(@Param("id") int id);

    String getActivityNameById(Map map);

    String getInstitutionNameById(Map map);

}
