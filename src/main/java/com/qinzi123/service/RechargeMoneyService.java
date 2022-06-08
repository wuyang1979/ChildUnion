package com.qinzi123.service;

import java.util.Map;

/**
 * @title: RechargeMoneyService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface RechargeMoneyService {

    Map prepay(Map map);

    Map leaguePrepay(Map map);

    Map payBack(String result);

    Map leaguePayBack(String result);

    Map clientEndPayBack(String result);

    Map clientEndDistributionPartnerPayBack(String result);

    Map toLeaguer(Map map);

    Map membershipExpiration(Map map);

    Map toEnterpriseLeaguer(Map map);

    Map toGoldEnterpriseLeaguer(Map map);
}
