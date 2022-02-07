package com.qinzi123.controller.micro;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.CardMessageReply;
import com.qinzi123.service.CooperateWeixinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: CooperateController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "商户处理")
public class CooperateController {

    @Autowired
    private CooperateWeixinService cooperateWeixinService;

    @ApiOperation(value = "所有合作消息列表", notes = "合作消息列表")
    @RequestMapping(value = "/cooperate/list", method = RequestMethod.POST)
    private List<CardMessage> listCooperate(@RequestBody Map map) {
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        return cooperateWeixinService.getAllCardMessage(start, num);
    }

    @ApiOperation(value = "判断是否已关注", notes = "判断是否已关注")
    @RequestMapping(value = "/cooperate/getFollower", method = RequestMethod.POST)
    private List<LinkedHashMap> getFollower(@RequestBody Map map) {
        return cooperateWeixinService.getFollower(map);
    }

    @ApiOperation(value = "商户合作消息列表", notes = "合作消息列表")
    @RequestMapping(value = "/cooperate/cardId", method = RequestMethod.POST)
    private List<CardMessage> listCooperateByCardId(@RequestBody Map map) {
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        int cardId = Integer.parseInt(map.get("cardId").toString());
        return cooperateWeixinService.getCardMessageByCardId(cardId, start, num);
    }

    @ApiOperation(value = "新增合作消息", notes = "合作消息列表")
    @RequestMapping(value = "/cooperate/message", method = RequestMethod.POST)
    private int addMessage(@RequestBody CardMessage cardMessage) {
        return cooperateWeixinService.addMessage(cardMessage);
    }

    @RequestMapping(value = "/cooperate/picList", method = RequestMethod.POST)
    private List<LinkedHashMap> pictureList(@RequestBody Map map) {
        int messageId = Integer.parseInt(map.get("id").toString());
        return cooperateWeixinService.pictureList(messageId);
    }

    @ApiOperation(value = "消息阅读数", notes = "消息阅读数")
    @RequestMapping(value = "/cooperate/message/read/{messageId}", method = RequestMethod.GET)
    private int updateMessageRead(@PathVariable("messageId") int messageId) {
        return cooperateWeixinService.updateMessageRead(messageId);
    }


    @ApiOperation(value = "消息点赞数", notes = "消息点赞数")
    @RequestMapping(value = "/cooperate/message/like/{messageId}", method = RequestMethod.GET)
    private int updateMessageLike(@PathVariable("messageId") int messageId) {
        return cooperateWeixinService.updateMessageLike(messageId);
    }


    @ApiOperation(value = "新增合作回复消息", notes = "合作消息列表")
    @RequestMapping(value = "/cooperate/messageReply", method = RequestMethod.POST)
    private int addMessageReply(@RequestBody CardMessageReply cardMessageReply) {
        return cooperateWeixinService.addCardMessageReply(cardMessageReply);
    }

    @ApiOperation(value = "商户合作消息回复列表", notes = "合作消息列表")
    @RequestMapping(value = "/cooperate/messageReply/{messageId}", method = RequestMethod.GET)
    private List<CardMessageReply> getAllCardMessageReplyByMessageId(@PathVariable("messageId") int messageId) {
        return cooperateWeixinService.getAllCardMessageReplyByMessageId(messageId);
    }

    @ApiOperation(value = "最新活动合作方列表", notes = "最新活动合作方列表")
    @RequestMapping(value = "/cooperate/activeCooperateList/{message}", method = RequestMethod.GET)
    private List<CardMessageReply> getActiveCooperateListByMessage(@PathVariable("message") String message) {
        return cooperateWeixinService.getActiveCooperateListByMessage(message);
    }

    @ApiOperation(value = "获取订单信息", notes = "获取订单信息")
    @RequestMapping(value = "/cooperate/getMessage/{messageId}", method = RequestMethod.GET)
    private List<LinkedHashMap> getMessage(@PathVariable("messageId") int messageId) {
        return cooperateWeixinService.getMessage(messageId);
    }

    @ApiOperation(value = "新增或修改用户授权记录", notes = "新增或修改用户授权记录")
    @RequestMapping(value = "/cooperate/addOrUpdateFollowerAuthAcceptRecord", method = RequestMethod.POST)
    private int addOrUpdateFollowerAuthAcceptRecord(@RequestBody Map map) {
        return cooperateWeixinService.addOrUpdateFollowerAuthAcceptRecord(map);
    }

    @ApiOperation(value = "获取未授权订阅消息列表", notes = "获取未授权订阅消息列表")
    @RequestMapping(value = "/cooperate/getUnAuthRecordList", method = RequestMethod.POST)
    private List<LinkedHashMap> getUnAuthRecordList(@RequestBody Map map) {
        return cooperateWeixinService.getUnAuthRecordList(map);
    }

    @ApiOperation(value = "新增或修改合作消息授权记录", notes = "新增或修改合作消息授权记录")
    @RequestMapping(value = "/cooperate/addOrUpdateCardMessageAuthAcceptRecord", method = RequestMethod.POST)
    private int addOrUpdateCardMessageAuthAcceptRecord(@RequestBody Map map) {
        return cooperateWeixinService.addOrUpdateCardMessageAuthAcceptRecord(map);
    }

    @ApiOperation(value = "获取首页展示数量", notes = "获取首页展示数量")
    @RequestMapping(value = "/cooperate/getShowDataCount", method = RequestMethod.POST)
    private Map<String, Object> getShowDataCount(@RequestBody Map map) {
        return cooperateWeixinService.getShowDataCount();
    }
}
