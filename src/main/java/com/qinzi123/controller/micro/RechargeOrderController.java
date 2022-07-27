package com.qinzi123.controller.micro;

import com.qinzi123.service.CampaignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: RechargeOrderController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "充值")
public class RechargeOrderController {

    private static final int PRODUCT_ID = 1;
    private static final String DEFAULT_PRODUCT = "productId";

    @Resource
    CampaignService campaignService;

    @ApiOperation(value = "充值订单列表", notes = "充值订单列表")
    @RequestMapping(value = "/rechargeOrder/list", method = RequestMethod.POST)
    private List<LinkedHashMap> listRechargeOrder(@RequestBody Map map) {
        int card = Integer.parseInt(map.get("card").toString());
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        return campaignService.listRechargeOrder(card, start, num);
    }

    @ApiOperation(value = "充值订单", notes = "充值订单")
    @RequestMapping(value = "/rechargeOrder/{id}", method = RequestMethod.GET)
    private List<LinkedHashMap> oneRechargeOrder(@PathVariable int id) {
        return campaignService.oneRechargeOrder(id);
    }

    @ApiOperation(value = "充值订单新增", notes = "充值订单新增")
    @RequestMapping(value = "/rechargeOrder/data", method = RequestMethod.POST)
    private int addPayOrder(@RequestBody Map map) {
        map.put(DEFAULT_PRODUCT, PRODUCT_ID);
        return campaignService.addPayOrder(map);
    }

    @ApiOperation(value = "充值订单新增", notes = "充值订单新增")
    @RequestMapping(value = "/rechargeOrder/leagueData", method = RequestMethod.POST)
    private int addLeaguePayOrder(@RequestBody Map map) {
        map.put(DEFAULT_PRODUCT, PRODUCT_ID);
        return campaignService.addLeaguePayOrder(map);
    }

}
