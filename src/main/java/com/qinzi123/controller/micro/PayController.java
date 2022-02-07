package com.qinzi123.controller.micro;

import com.qinzi123.service.PayScoreService;
import com.qinzi123.service.RechargeMoneyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @title: PayController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Api(value = "支付")
@RestController
public class PayController {

    private static final Logger logger = LoggerFactory.getLogger(PayController.class);

    @Autowired
    PayScoreService payScoreService;

    @Autowired
    RechargeMoneyService rechargeMoneyService;

    @ApiOperation(value = "充值积分", notes = "充值积分")
    @RequestMapping(value = "/order/payScore", method = RequestMethod.POST)
    private Map payScore(@RequestBody Map map) {
        Map result = new HashMap<>();
        result = payScoreService.payScore(map);
        return result;
    }

    @ApiOperation(value = "仅仅兑付查看功能", notes = "充值积分")
    @RequestMapping(value = "/card/show/payScore/{card}-{showCard}", method = RequestMethod.GET)
    private Map payScore4ShowCard(@PathVariable int card, @PathVariable int showCard) {
        return payScoreService.payShowCardScore(card, showCard);
    }

    @ApiOperation(value = "预支付", notes = "预支付")
    @RequestMapping(value = "/order/prepay", method = RequestMethod.POST)
    private Map prepay(@RequestBody Map map) {
        Map result = new HashMap<>();
        result = rechargeMoneyService.prepay(map);
        return result;
    }

    @ApiOperation(value = "会员充值预支付", notes = "会员充值预支付")
    @RequestMapping(value = "/order/leaguePrepay", method = RequestMethod.POST)
    private Map leaguePrepay(@RequestBody Map map) {
        Map result = new HashMap<>();
        result = rechargeMoneyService.leaguePrepay(map);
        return result;
    }

    @ApiOperation(value = "成为会员", notes = "成为会员")
    @RequestMapping(value = "/order/toLeaguer", method = RequestMethod.POST)
    private Map toLeaguer(@RequestBody Map map) {
        return rechargeMoneyService.toLeaguer(map);
    }

    @ApiOperation(value = "会员到期", notes = "会员到期")
    @RequestMapping(value = "/order/membershipExpiration", method = RequestMethod.POST)
    private Map membershipExpiration(@RequestBody Map map) {
        return rechargeMoneyService.membershipExpiration(map);
    }

    @ApiOperation(value = "成为企业会员", notes = "成为企业会员")
    @RequestMapping(value = "/order/toEnterpriseLeaguer", method = RequestMethod.POST)
    private Map toEnterpriseLeaguer(@RequestBody Map map) {
        return rechargeMoneyService.toEnterpriseLeaguer(map);
    }

    @ApiOperation(value = "成为黄金企业会员", notes = "成为黄金企业会员")
    @RequestMapping(value = "/order/toGoldEnterpriseLeaguer", method = RequestMethod.POST)
    private Map toGoldEnterpriseLeaguer(@RequestBody Map map) {
        return rechargeMoneyService.toGoldEnterpriseLeaguer(map);
    }

}
