package com.qinzi123.dao;

import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: CustomerDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/5/30 0030 14:53
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface CustomerDao {

    List<LinkedHashMap> getAllCustomerList(Map map);

    List<LinkedHashMap> getSearchCustomerList(Map map);

    List<LinkedHashMap> getBabyInfoById(Map map);

    List<LinkedHashMap> getReceiveAddressById(Map map);

    List<LinkedHashMap> getOrderListById(Map map);

    List<LinkedHashMap> getProductStandardNameById(@Param("standardId") int standardId);

    List<LinkedHashMap> getActivityStandardNameById(@Param("standardId") int standardId);
}
