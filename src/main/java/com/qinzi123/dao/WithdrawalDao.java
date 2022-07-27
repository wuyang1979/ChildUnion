package com.qinzi123.dao;

import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: WithdrawalDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/4/1 0001 17:30
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface WithdrawalDao {

    int startWithdrawal(Map map);

    int distributionPartnerStartWithdrawal(Map map);

    int updateShopMoneyByCardId(Map map);

    int updateDistributionShopMoneyByCardId(Map map);

    int updateDistributionShopMoneyByUserId(Map map);

    List<LinkedHashMap> getCurrentDayWithdrawalRecordList(Map map);

    List<LinkedHashMap> getDistributionPartnerCurrentDayWithdrawalRecordList(Map map);

    Map getShopInfoByCard(@Param("card") int card);

    String getTargetOpenIdByCard(@Param("card") int card);

    String getTargetOpenIdByUserId(@Param("userId") String userId);

    List<LinkedHashMap> getAllRecordList(Map map);

    List<LinkedHashMap> getAllDistributionPartnerRecordList(Map map);

    List<LinkedHashMap> getRecordList(Map map);

    List<LinkedHashMap> getDistributionPartnerRecordList(Map map);

}
