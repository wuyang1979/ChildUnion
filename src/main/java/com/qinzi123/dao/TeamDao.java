package com.qinzi123.dao;


import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: CustomerDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/8/03 0030 11:46
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface TeamDao {

    List<LinkedHashMap> getAllTeamMemberList(Map map);

    List<LinkedHashMap> getSearchTeamMemberList(Map map);

    List<LinkedHashMap> getDistributionOrderListById(Map map);

    int addCommanderInfoRecord(Map map);

    int updateNewMembersJoinAuthRecordByUserId(@Param("userId") String userId);

    String getOpenIdByUserId(@Param("userId") String userId);

    String getNickNameByUserId(@Param("userId") String userId);

}
