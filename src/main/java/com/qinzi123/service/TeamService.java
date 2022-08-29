package com.qinzi123.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: CustomerService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/8/03 0030 11:44
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface TeamService {

    List<LinkedHashMap> getAllTeamMemberList(Map map);

    List<LinkedHashMap> getSearchTeamMemberList(Map map);

    List<LinkedHashMap> getDistributionOrderListById(Map map);

    int becomeTeamMemberByInvited(Map map) throws Exception;

}
