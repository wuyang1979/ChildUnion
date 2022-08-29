package com.qinzi123.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: ProfitService
 * @package: com.qinzi123.service
 * @projectName: childunion
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/6/10 0010 10:18
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface ProfitService {


    Map getDistributionPartnerProfitInfo(Map map);

    List<LinkedHashMap> loadSelfProfitList(Map map);

    List<LinkedHashMap> loadTeamProfitList(Map map);

}
