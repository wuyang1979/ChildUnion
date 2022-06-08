package com.qinzi123.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: UserInfoDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/2/11 0011 13:22
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface UserInfoDao {

    int updateBirthdayById(Map map);

    int updateGenderById(Map map);

    List<LinkedHashMap> getReceiveAddressListById(Map map);

    List<LinkedHashMap> getBabyListById(Map map);

    int addReceiveAddress(Map map);

    int updateReceiveAddress(Map map);

    int addBabyInfo(Map map);

    int updateBabyInfo(Map map);

    String getBaseName(Map map);

    Map getProductList(Map map);
}
