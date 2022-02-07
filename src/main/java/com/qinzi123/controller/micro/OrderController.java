package com.qinzi123.controller.micro;

import com.qinzi123.service.CampaignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: OrderController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "订单")
public class OrderController {

    @Autowired
    CampaignService campaignService;

    @ApiOperation(value = "所有订单列表", notes = "所有订单列表")
    @RequestMapping(value = "/order/allList", method = RequestMethod.POST)
    private List<LinkedHashMap> listAllOrder(@RequestBody Map map) {
        int card = Integer.parseInt(map.get("card").toString());
        return campaignService.listAllOrder(card);
    }

    @ApiOperation(value = "产品订单列表", notes = "产品订单列表")
    @RequestMapping(value = "/order/list", method = RequestMethod.POST)
    private List<LinkedHashMap> listOrder(@RequestBody Map map) {
        int card = Integer.parseInt(map.get("card").toString());
        return campaignService.listOrder(card);
    }

    @ApiOperation(value = "获取订单", notes = "获取订单")
    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    private List<LinkedHashMap> oneOrder(@PathVariable int id) {
        return campaignService.oneOrder(id);
    }

    @ApiOperation(value = "新获取订单", notes = "新获取订单")
    @RequestMapping(value = "/getOneOrder", method = RequestMethod.POST)
    private List<LinkedHashMap> getOneOrder(@RequestBody Map map) {
        return campaignService.getOneOrder(map);
    }

    @ApiOperation(value = "新获取订单", notes = "新获取订单")
    @RequestMapping(value = "/oneOrder/{id}", method = RequestMethod.GET)
    private List<LinkedHashMap> getOneOrder(@PathVariable int id) {
        return campaignService.getOneOrder(id);
    }

    @ApiOperation(value = "新获取知识库订单", notes = "新获取知识库订单")
    @RequestMapping(value = "/oneKnowledgeOrder/{id}", method = RequestMethod.GET)
    private List<LinkedHashMap> getOneKnowledgeOrder(@PathVariable int id) {
        return campaignService.getOneKnowledgeOrder(id);
    }

    @ApiOperation(value = "积分订单新增", notes = "积分订单新增")
    @RequestMapping(value = "/order/data", method = RequestMethod.POST)
    private int addOrder(@RequestBody Map map) {
        return campaignService.addOrder(map);
    }

    @ApiOperation(value = "知识库订单新增", notes = "知识库订单新增")
    @RequestMapping(value = "/knowledgeOrder/data", method = RequestMethod.POST)
    private int addKnowledgeOrder(@RequestBody Map map) {
        return campaignService.addKnowledgeOrder(map);
    }

    @ApiOperation(value = "现金订单新增", notes = "现金订单新增")
    @RequestMapping(value = "/cashOrder/data", method = RequestMethod.POST)
    private int addcashOrder(@RequestBody Map map) {
        return campaignService.addcashOrder(map);
    }
}
