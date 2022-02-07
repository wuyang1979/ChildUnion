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
 * @title: CampaignController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "活动")
public class CampaignController {

    @Autowired
    CampaignService campaignService;

    @RequestMapping(value = "/campaign/list", method = RequestMethod.POST)
    private List<LinkedHashMap> listCampaign(@RequestBody Map map) {
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        return campaignService.listCampaign(start, num);
    }

    @RequestMapping(value = "/campaign/picList", method = RequestMethod.POST)
    private List<LinkedHashMap> pictureList(@RequestBody Map map) {
        int productId = Integer.parseInt(map.get("id").toString());
        return campaignService.pictureList(productId);
    }

    @RequestMapping(value = "/campaign/{id}", method = RequestMethod.GET)
    private List<LinkedHashMap> oneCampaign(@PathVariable int id) {
        return campaignService.oneCampaign(id);
    }


    @ApiOperation(value = "活动消息阅读数", notes = "活动消息阅读数")
    @RequestMapping(value = "/campaign/message/read/{messageId}", method = RequestMethod.GET)
    private int updateMessageRead(@PathVariable("messageId") int messageId) {
        return campaignService.updateMessageRead(messageId);
    }

    @ApiOperation(value = "活动消息点赞数", notes = "活动消息点赞数")
    @RequestMapping(value = "/campaign/message/like/{messageId}", method = RequestMethod.GET)
    private int updateMessageLike(@PathVariable("messageId") int messageId) {
        return campaignService.updateMessageLike(messageId);
    }
}
