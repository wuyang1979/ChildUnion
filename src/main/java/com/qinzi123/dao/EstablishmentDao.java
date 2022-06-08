package com.qinzi123.dao;

import com.qinzi123.dto.EstablishmentInfo;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: EstablishmentDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/12/6 0006 10:11
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface EstablishmentDao {

    List<LinkedHashMap> listEstablishment(@Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> getEstablishmentInfoById(@Param("establishmentId") int establishmentId);

    List<LinkedHashMap> getMemberList(@Param("establishmentId") int establishmentId);

    List<LinkedHashMap> getMainMember(@Param("establishmentId") int establishmentId);

    List<LinkedHashMap> listSearchEstablishment(Map<String, Object> map);

    List<LinkedHashMap> listScreenEstablishment(Map<String, Object> map);

    LinkedHashMap<String, Object> getCardInfo(@Param("cardId") String cardId);

    List<LinkedHashMap> getEstablishmentListByCompany(Map map);

    String getAdminName(Map map);

    String isExistEstablishmentMember(Map map);

    String isSelfEstablishmentLeague(Map map);

    int addEstablishment(EstablishmentInfo establishmentInfo);

    int updateEstablishment(Map map);

    int deleteWriteOffClerkById(Map map);

    int updateCompanyIdByCardId(Map map);

    List<LinkedHashMap> getEstablishmentInfoByCompanyId(Map map);

    List<LinkedHashMap> getLatestEstablishmentList(Map map);

    List<LinkedHashMap> getWriteOffClerkByOpenId(Map map);

    List<LinkedHashMap> getAllWriteOffClerkListByCompanyId(Map map);

    int addWriteOffClerk(Map map);

    String getLogoByShopId(@Param("shopId") int shopId);
}
