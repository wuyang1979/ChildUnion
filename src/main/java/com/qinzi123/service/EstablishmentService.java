package com.qinzi123.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: EstablishmentService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/12/6 0006 10:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface EstablishmentService {

    List<LinkedHashMap> listEstablishment(int start, int num);

    List<LinkedHashMap> getEstablishmentInfoById(int establishmentId);

    List<LinkedHashMap> getMemberList(int establishmentId);

    List<LinkedHashMap> getMainMember(int establishmentId);

    List<LinkedHashMap> listSearchEstablishment(Map<String, Object> map);

    List<LinkedHashMap> listScreenEstablishment(Map<String, Object> map);

    List<LinkedHashMap> getEstablishmentListByCompany(Map<String, Object> map);

    Map<String, Object> getAdminName(Map<String, Object> map);

    int addEstablishment(Map map);

    int updateEstablishment(Map map);

    int deleteWriteOffClerkById(Map map);

    List<LinkedHashMap> getEstablishmentInfoByCompanyId(Map map);

    Map<String, Object> isExistEstablishmentMember(Map map);

    Map<String, Object> isSelfEstablishmentLeague(Map map);

    List<LinkedHashMap> getLatestEstablishmentList(Map map);

    List<LinkedHashMap> getAllWriteOffClerkListByCompanyId(Map map);

    Map<String, Object> addWriteOffClerk(Map map) throws Exception;
}