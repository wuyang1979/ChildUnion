package com.qinzi123.controller.micro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.wxpay.sdk.WXPayUtil;
import com.qinzi123.dto.WithDrawDTO;
import com.qinzi123.dto.WithDrawEnum;
import com.qinzi123.service.UserInfoService;
import com.qinzi123.util.WithDrawUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: UserInfoController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/2/11 0011 13:20
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "用户信息")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "更新生日", notes = "更新生日")
    @RequestMapping(value = "/userInfo/updateBirthdayById", method = RequestMethod.POST)
    private int updateBirthdayById(@RequestBody Map map) {
        return userInfoService.updateBirthdayById(map);
    }

    @ApiOperation(value = "更新性别", notes = "更新性别")
    @RequestMapping(value = "/userInfo/updateGenderById", method = RequestMethod.POST)
    private int updateGenderById(@RequestBody Map map) {
        return userInfoService.updateGenderById(map);
    }

    @ApiOperation(value = "获取收货地址列表", notes = "获取收货地址列表")
    @RequestMapping(value = "/userInfo/getReceiveAddressListById", method = RequestMethod.POST)
    private List<LinkedHashMap> getReceiveAddressListById(@RequestBody Map map) {
        return userInfoService.getReceiveAddressListById(map);
    }

    @ApiOperation(value = "新增或修改收货地址", notes = "新增或修改收货地址")
    @RequestMapping(value = "/userInfo/saveOrUpdateAddressInfoByUserId", method = RequestMethod.POST)
    private int saveOrUpdateAddressInfoByUserId(@RequestBody Map map) {
        return userInfoService.saveOrUpdateAddressInfoByUserId(map);
    }

    @ApiOperation(value = "获取宝贝列表", notes = "获取宝贝列表")
    @RequestMapping(value = "/userInfo/getBabyListById", method = RequestMethod.POST)
    private List<LinkedHashMap> getBabyListById(@RequestBody Map map) {
        return userInfoService.getBabyListById(map);
    }

    @ApiOperation(value = "添加宝贝信息", notes = "添加宝贝信息")
    @RequestMapping(value = "/userInfo/addBabyInfo", method = RequestMethod.POST)
    private int addBabyInfo(@RequestBody Map map) {
        return userInfoService.addBabyInfo(map);
    }

    @ApiOperation(value = "更新宝贝信息", notes = "更新宝贝信息")
    @RequestMapping(value = "/userInfo/updateBabyInfo", method = RequestMethod.POST)
    private int updateBabyInfo(@RequestBody Map map) {
        return userInfoService.updateBabyInfo(map);
    }

    @RequestMapping(value = "/userInfo/getBaseName", method = RequestMethod.POST)
    private Map<String, Object> getBaseName(@RequestBody Map map) {
        return userInfoService.getBaseName(map);
    }
}
