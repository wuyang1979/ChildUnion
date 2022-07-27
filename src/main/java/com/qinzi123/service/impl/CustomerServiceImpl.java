package com.qinzi123.service.impl;

import com.qinzi123.dao.CustomerDao;
import com.qinzi123.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: CustomerServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/5/30 0030 14:51
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class CustomerServiceImpl extends AbstractWechatMiniProgramService implements CustomerService {

    @Resource
    private CustomerDao customerDao;

    @Override
    public List<LinkedHashMap> getAllCustomerList(Map map) {
        List<LinkedHashMap> allCustomerList = customerDao.getAllCustomerList(map);
        allCustomerList.forEach(item -> {
            item.put("nick_name", new String(Base64.getDecoder().decode(item.get("nick_name").toString())));
        });
        return allCustomerList;
    }

    @Override
    public List<LinkedHashMap> getSearchCustomerList(Map map) {
        List<LinkedHashMap> allCustomerList = customerDao.getSearchCustomerList(map);
        allCustomerList.forEach(item -> {
            item.put("nick_name", new String(Base64.getDecoder().decode(item.get("nick_name").toString())));
        });
        return allCustomerList;
    }

    @Override
    public List<LinkedHashMap> getBabyInfoById(Map map) {
        return customerDao.getBabyInfoById(map);
    }

    @Override
    public List<LinkedHashMap> getReceiveAddressById(Map map) {
        return customerDao.getReceiveAddressById(map);
    }

    @Override
    public List<LinkedHashMap> getOrderListById(Map map) {
        List<LinkedHashMap> orderList = customerDao.getOrderListById(map);
        orderList.forEach(item -> {
            int standardId = Integer.parseInt(item.get("standard_id").toString());
            int productType = Integer.parseInt(item.get("product_type").toString());
            if (productType == 0) {
                //产品订单
                List<LinkedHashMap> standardList = customerDao.getProductStandardNameById(standardId);
                item.put("standardName", standardList.get(0).get("name"));
            } else if (productType == 1) {
                //活动订单
                List<LinkedHashMap> standardList = customerDao.getActivityStandardNameById(standardId);
                item.put("standardName", standardList.get(0).get("adults_num") + "大" + standardList.get(0).get("children_num") + "小");
            }
        });
        return orderList;
    }
}
