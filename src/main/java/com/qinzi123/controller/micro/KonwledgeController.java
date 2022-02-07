package com.qinzi123.controller.micro;

import com.qinzi123.service.KnowledgeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: KonwledgeController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "知识库")
public class KonwledgeController {

    private Logger logger = LoggerFactory.getLogger(KonwledgeController.class);

    @Autowired
    private KnowledgeService knowledgeService;

    @ApiOperation(value = "知识库列表", notes = "知识库列表")
    @RequestMapping(value = "/knowledge/list", method = RequestMethod.POST)
    private List<LinkedHashMap> getKnowledgeList(@RequestBody Map map) {
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        return knowledgeService.getKnowledgeList();
    }

    @ApiOperation(value = "更新阅读量", notes = "更新阅读量")
    @RequestMapping(value = "/knowledge/updateReadCount", method = RequestMethod.POST)
    private int updateReadCount(@RequestBody Map map) {
        return knowledgeService.updateReadCount(map);
    }

    @ApiOperation(value = "更新购买量", notes = "更新购买量")
    @RequestMapping(value = "/knowledge/updateBuyCount", method = RequestMethod.POST)
    private int updateBuyCount(@RequestBody Map map) {
        return knowledgeService.updateBuyCount(map);
    }

    @ApiOperation(value = "是否会员", notes = "是否会员")
    @RequestMapping(value = "/knowledge/isLeaguer", method = RequestMethod.POST)
    private Map<String, Object> isLeaguer(@RequestBody Map map) {
        return knowledgeService.isLeaguer(map);
    }

    @ApiOperation(value = "是否购买文件", notes = "是否购买文件")
    @RequestMapping(value = "/knowledge/hasBoughtKnowledge", method = RequestMethod.POST)
    private int hasBoughtKnowledge(@RequestBody Map map) {
        return knowledgeService.hasBoughtKnowledge(map);
    }
}
