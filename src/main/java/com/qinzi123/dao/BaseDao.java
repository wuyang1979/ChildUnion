package com.qinzi123.dao;

import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: BaseDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface BaseDao {

    List<LinkedHashMap> listBase(@Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> getBaseList(Map map);

    List<LinkedHashMap> listSearchBase(Map<String, Object> map);

    List<LinkedHashMap> screenBaseList(@Param("districtCode") int districtCode, @Param("typeCode") int typeCode, @Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> screenBaseListByDistrictCode(@Param("districtCode") int districtCode, @Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> screenBaseListByTypeCode(@Param("typeCode") int typeCode, @Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> pictureList(@Param("baseId") int baseId);

    List<LinkedHashMap> getAllConsultingeListByBaseId(@Param("baseId") int baseId);

    List<LinkedHashMap> getBaseTopicTypeList();

    String getTopicType(String topicTypeId);

    String getCardName(String cardId);

    String getConsultNumber(String id);

    String getBaseLevel(String level);

    Map getCardInfo(String cardId);

    String getActivityBaseName(String baseId);

    String getDistrictName(String district);

    int baseReserve(Map map);

    int addBaseEntity(Map<String, Object> map);

    int updateBaseEntity(Map<String, Object> map);

    List<LinkedHashMap> getBaseReserveList();

    List<LinkedHashMap> allBaseMapInfo();

    int readOrder(@Param("id") int id);
}
