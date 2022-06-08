package com.qinzi123.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: UserInfoService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/2/11 0011 13:20
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface UserInfoService {

    int updateBirthdayById(Map map);

    int updateGenderById(Map map);

    List<LinkedHashMap> getReceiveAddressListById(Map map);

    List<LinkedHashMap> getBabyListById(Map map);

    int saveOrUpdateAddressInfoByUserId(Map map);

    int addBabyInfo(Map map);

    int updateBabyInfo(Map map);

    Map getBaseName(Map map);

}
