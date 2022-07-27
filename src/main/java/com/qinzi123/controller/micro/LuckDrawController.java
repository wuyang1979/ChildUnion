package com.qinzi123.controller.micro;

import com.qinzi123.service.LuckDrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: LuckDrawController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/12/24 0024 16:37
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "幸运抽奖")
public class LuckDrawController {

    private Logger logger = LoggerFactory.getLogger(KonwledgeController.class);

    @Resource
    private LuckDrawService luckDrawService;

    @ApiOperation(value = "报名抽奖", notes = "报名抽奖")
    @RequestMapping(value = "/luckDraw/join", method = RequestMethod.POST)
    private int joinLuckDraw(@RequestBody Map map) {
        return luckDrawService.joinLuckDraw(map);
    }

    @ApiOperation(value = "检查是否已报名", notes = "检查是否已报名")
    @RequestMapping(value = "/luckDraw/hasJoin", method = RequestMethod.POST)
    private List<LinkedHashMap> getJoinInfoByCardAndType(@RequestBody Map map) {
        return luckDrawService.getJoinInfoByCardAndType(map);
    }

    @ApiOperation(value = "对外抽奖入口报名", notes = "对外抽奖入口报名")
    @RequestMapping(value = "/luckDraw/joinOutside", method = RequestMethod.POST)
    private int joinOutsideLuckDraw(@RequestBody Map map) {
        return luckDrawService.joinOutsideLuckDraw(map);
    }

    @ApiOperation(value = "检查对外抽奖入口是否已报名", notes = "检查对外抽奖入口是否已报名")
    @RequestMapping(value = "/luckDraw/hasJoinForOutside", method = RequestMethod.POST)
    private List<LinkedHashMap> getOutsideJoinInfoByOpenId(@RequestBody Map map) {
        return luckDrawService.getOutsideJoinInfoByOpenId(map);
    }
}
