package com.qinzi123.dao;

import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: ProfitDao
 * @package: com.qinzi123.dao
 * @projectName: childunion
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/6/10 0010 10:19
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface ProfitDao {

    Map getDistributionPartnerProfitInfo(Map map);

    List<LinkedHashMap> getSelfProfitListByUserId(@Param("userId") String userId);

    List<LinkedHashMap> getSelfProfitCustomerOpenIdListByUserId(@Param("userId") String userId, @Param("start") int start, @Param("num") int num);

    List<LinkedHashMap> getMemberProfitCustomerOpenIdListByUserId(@Param("param") List<String> param, @Param("start") int start, @Param("num") int num);

    Map<String, Object> getCustomerInfoByOpenId(@Param("openId") String openId);

    List<String> getMemberListByUserId(@Param("userId") String userId);

}
