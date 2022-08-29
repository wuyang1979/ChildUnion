package com.qinzi123.controller.micro;

import com.qinzi123.service.impl.WithdrawalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: WithdrawalController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/4/1 0001 17:25
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */

@RestController
@Api(value = "提现管理")
public class WithdrawalController {

    @Resource
    private WithdrawalService withdrawalService;

    @ApiOperation(value = "商户发起提现", notes = "商户发起提现")
    @RequestMapping(value = "/withdrawal/startWithdrawal", method = RequestMethod.POST)
    private int startWithdrawal(@RequestBody Map map) throws Exception {
        return withdrawalService.startWithdrawal(map);
    }

    @ApiOperation(value = "分销合伙人发起提现", notes = "分销合伙人发起提现")
    @RequestMapping(value = "/withdrawal/distributionPartnerStartWithdrawal", method = RequestMethod.POST)
    private Map distributionPartnerStartWithdrawal(@RequestBody Map map) throws Exception {
        return withdrawalService.distributionPartnerStartWithdrawal(map);
    }

    @ApiOperation(value = "获取当天提现总金额", notes = "获取当天提现总金额")
    @RequestMapping(value = "/withdrawal/getTotalOfCurrentDay", method = RequestMethod.POST)
    private Map getTotalOfCurrentDay(@RequestBody Map map) {
        return withdrawalService.getTotalOfCurrentDay(map);
    }

    @ApiOperation(value = "获取分销合伙人当天提现总金额", notes = "获取分销合伙人当天提现总金额")
    @RequestMapping(value = "/withdrawal/getDistributionPartnerTotalOfCurrentDay", method = RequestMethod.POST)
    private Map getDistributionPartnerTotalOfCurrentDay(@RequestBody Map map) {
        return withdrawalService.getDistributionPartnerTotalOfCurrentDay(map);
    }

    @ApiOperation(value = "获取指定年月的提现记录", notes = "获取指定年月的提现记录")
    @RequestMapping(value = "/withdrawal/getRecordList", method = RequestMethod.POST)
    private List<LinkedHashMap> getRecordList(@RequestBody Map map) {
        return withdrawalService.getRecordList(map);
    }

    @ApiOperation(value = "获取分销合伙人指定年月的提现记录", notes = "获取分销合伙人指定年月的提现记录")
    @RequestMapping(value = "/withdrawal/getDistributionPartnerRecordList", method = RequestMethod.POST)
    private List<LinkedHashMap> getDistributionPartnerRecordList(@RequestBody Map map) {
        return withdrawalService.getDistributionPartnerRecordList(map);
    }

    @ApiOperation(value = "获取已发起提现总金额", notes = "获取已发起提现总金额")
    @RequestMapping(value = "/withdrawal/getAllAmount", method = RequestMethod.POST)
    private Map getAllAmount(@RequestBody Map map) {
        return withdrawalService.getAllAmount(map);
    }

    @ApiOperation(value = "获取分销合伙人已发起提现总金额", notes = "获取分销合伙人已发起提现总金额")
    @RequestMapping(value = "/withdrawal/getDistributionPartnerAllAmount", method = RequestMethod.POST)
    private Map getDistributionPartnerAllAmount(@RequestBody Map map) {
        return withdrawalService.getDistributionPartnerAllAmount(map);
    }
}
