package com.qinzi123.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: WithdrawalService
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/4/1 0001 17:29
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface WithdrawalService {

    int startWithdrawal(Map map) throws Exception;

    Map getTotalOfCurrentDay(Map map);

    List<LinkedHashMap> getRecordList(Map map);

    Map getAllAmount(Map map);
}
