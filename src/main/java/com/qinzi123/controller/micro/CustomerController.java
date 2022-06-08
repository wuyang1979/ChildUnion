package com.qinzi123.controller.micro;

import com.qinzi123.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: CustomerController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/5/30 0030 14:50
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "客户运营")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ApiOperation(value = "获取所有下单的客户信息", notes = "获取所有下单的客户信息")
    @RequestMapping(value = "/customer/getAllCustomerList", method = RequestMethod.POST)
    private List<LinkedHashMap> getAllCustomerList(@RequestBody Map map) {
        return customerService.getAllCustomerList(map);
    }

    @ApiOperation(value = "获取搜索下单的客户信息", notes = "获取搜索下单的客户信息")
    @RequestMapping(value = "/customer/getSearchCustomerList", method = RequestMethod.POST)
    private List<LinkedHashMap> getSearchCustomerList(@RequestBody Map map) {
        return customerService.getSearchCustomerList(map);
    }

    @ApiOperation(value = "获取宝贝列表", notes = "获取宝贝列表")
    @RequestMapping(value = "/customer/getBabyInfoById", method = RequestMethod.POST)
    private List<LinkedHashMap> getBabyInfoById(@RequestBody Map map) {
        return customerService.getBabyInfoById(map);
    }

    @ApiOperation(value = "获取收货地址", notes = "获取收货地址")
    @RequestMapping(value = "/customer/getReceiveAddressById", method = RequestMethod.POST)
    private List<LinkedHashMap> getReceiveAddressById(@RequestBody Map map) {
        return customerService.getReceiveAddressById(map);
    }

    @ApiOperation(value = "获取客户中心的订单列表", notes = "获取客户中心的订单列表")
    @RequestMapping(value = "/customer/getOrderListById", method = RequestMethod.POST)
    private List<LinkedHashMap> getOrderListById(@RequestBody Map map) {
        return customerService.getOrderListById(map);
    }

}
