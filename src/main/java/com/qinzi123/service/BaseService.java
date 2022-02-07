package com.qinzi123.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: BaseService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface BaseService {

    List<LinkedHashMap> listBase(int start, int num);

    List<LinkedHashMap> getBaseList(Map map);

    List<LinkedHashMap> listSearchBase(Map<String, Object> map);

    List<LinkedHashMap> screenBaseList(int districtCode, int typeCode, int start, int num);

    List<LinkedHashMap> pictureList(int baseId);

    List<LinkedHashMap> getAllConsultingeListByBaseId(int baseId);

    List<LinkedHashMap> getBaseTopicTypeList();

    Object getTopicType(String topicTypeId);

    String getCardName(String cardId);

    String getConsultNumber(String id);

    String getBaseLevel(String level);

    String getDistrictName(String district);

    Map<String, Object> baseReserve(Map map);

    int addBaseEntity(Map<String, Object> map);

    int updateBaseEntity(Map<String, Object> map);

    List<LinkedHashMap> getBaseReserveList();

    int readOrder(int id);

    List<LinkedHashMap> allBaseMapInfo();
}
