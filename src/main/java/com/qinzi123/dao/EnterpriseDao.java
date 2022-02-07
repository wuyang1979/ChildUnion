package com.qinzi123.dao;

import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: EnterpriseDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/11/25 0025 15:11
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface EnterpriseDao {

    List<LinkedHashMap> listEnterprise(@Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> screenList(@Param("type") String type, @Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> pictureList(Map map);

    List<LinkedHashMap> getAllEnterpriseCommentByEnterpriseId(@Param("enterpriseId") int enterpriseId);

    List<LinkedHashMap> getAllEnterpriseConsultingByEnterpriseId(@Param("enterpriseId") int enterpriseId);

    int getConsultingCount(int enterpriseId);

    int getCommentCount(int enterpriseId);

    int addComment(Map map);

    int addEnterpriseConsulting(Map map);

    String getTitleById(@Param("enterpriseId") int enterpriseId);

    List<LinkedHashMap> getEnterpriseOrderList();

    int readOrder(@Param("id") int id);
}
