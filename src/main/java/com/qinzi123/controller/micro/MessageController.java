package com.qinzi123.controller.micro;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.service.MessageService;
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
 * @title: MessageController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/12/31 0031 13:21
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "消息")
public class MessageController {

    @Resource
    MessageService messageService;

    @ApiOperation(value = "消息列表", notes = "消息列表")
    @RequestMapping(value = "/message/list", method = RequestMethod.POST)
    private List<LinkedHashMap> getMessageList(@RequestBody Map map) {
        return messageService.getMessageList(map);
    }

    @ApiOperation(value = "更新消息已读状态", notes = "更新消息已读状态")
    @RequestMapping(value = "/message/updateReadStatus", method = RequestMethod.POST)
    private int updateReadStatus(@RequestBody Map map) {
        int messageId = Integer.parseInt((map.get("id").toString()));
        return messageService.updateReadStatus(messageId);
    }

    @ApiOperation(value = "获取合作消息信息", notes = "获取合作消息信息")
    @RequestMapping(value = "/message/getCardMessageInfoById", method = RequestMethod.POST)
    private List<CardMessage> getCardMessageInfoById(@RequestBody Map map) {
        return messageService.getCardMessageInfoById(map);
    }

    @ApiOperation(value = "获取未读消息列表", notes = "获取未读消息列表")
    @RequestMapping(value = "/message/judgeReadStatus", method = RequestMethod.POST)
    private int getUnreadMessageListByCard(@RequestBody Map map) {
        return messageService.getUnreadMessageCountByCard(map);
    }
}
