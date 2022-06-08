package com.qinzi123.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: CustomerService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/5/30 0030 14:51
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface CustomerService {

    List<LinkedHashMap> getAllCustomerList(Map map);

    List<LinkedHashMap> getSearchCustomerList(Map map);

    List<LinkedHashMap> getBabyInfoById(Map map);

    List<LinkedHashMap> getReceiveAddressById(Map map);

    List<LinkedHashMap> getOrderListById(Map map);

}
