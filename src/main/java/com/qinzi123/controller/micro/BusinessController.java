package com.qinzi123.controller.micro;

import com.qinzi123.dto.WxOneCity;
import com.qinzi123.service.BusinessWeixinService;
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
 * @title: BusinessController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "商户处理")
public class BusinessController {

    private Logger logger = LoggerFactory.getLogger(BusinessController.class);

    @Autowired
    private BusinessWeixinService businessWeixinService;

    @ApiOperation(value = "商户列表", notes = "商户列表")
    @RequestMapping(value = "/business/list", method = RequestMethod.POST)
    private List<LinkedHashMap> listBusiness(@RequestBody Map map) {
        String id = map.get("id").toString();
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        String search = map.get("search").toString();
        int tagId = -1, city = -1;
        if (map.get("tag") != null)
            tagId = Integer.parseInt(map.get("tag").toString());
        if (map.get("city") != null)
            city = Integer.parseInt(map.get("city").toString());
        return businessWeixinService.listBusiness(id, start, num, search, tagId, city);
    }

    @ApiOperation(value = "筛选一级列表商户列表", notes = "筛选一级列表商户列表")
    @RequestMapping(value = "/business/selectBusinessListByFirstTagId", method = RequestMethod.POST)
    private List<LinkedHashMap> selectBusinessListByFirstTagId(@RequestBody Map map) {
        String id = map.get("id").toString();
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        String tagId = "";
        int city = -1;
        if (map.get("tag") != null)
            tagId = map.get("tag").toString();
        if (map.get("city") != null)
            city = Integer.parseInt(map.get("city").toString());
        return businessWeixinService.selectBusinessListByFirstTagId(id, start, num, tagId, city);
    }

    @ApiOperation(value = "商户列表", notes = "商户列表")
    @RequestMapping(value = "/business/selectIndustryList", method = RequestMethod.POST)
    private List<LinkedHashMap> selectIndustryList(@RequestBody Map map) {
        String id = map.get("id").toString();
        String search = map.get("search").toString();
        int tagId = -1, city = -1;
        if (map.get("tag") != null)
            tagId = Integer.parseInt(map.get("tag").toString());
        if (map.get("city") != null)
            city = Integer.parseInt(map.get("city").toString());
        return businessWeixinService.selectIndustryList(id, search, tagId, city);
    }

    @ApiOperation(value = "获取具体行业列表", notes = "获取具体行业列表")
    @RequestMapping(value = "/business/getTagList", method = RequestMethod.POST)
    private List<LinkedHashMap> getTagList() {
        return businessWeixinService.getTagList();
    }

    @ApiOperation(value = "获取行业分类列表", notes = "获取行业分类列表")
    @RequestMapping(value = "/business/getSubServiceList", method = RequestMethod.POST)
    private List<LinkedHashMap> getSubServiceList() {
        return businessWeixinService.getSubServiceList();
    }

    @ApiOperation(value = "商户列表", notes = "商户列表")
    @RequestMapping(value = "/business/list/{id}-{start}-{num}", method = RequestMethod.GET)
    private List<LinkedHashMap> listBusinessNoSearch(@PathVariable String id, @PathVariable int start,
                                                     @PathVariable int num) {
        return businessWeixinService.listBusiness(id, start, num, null, -1, -1);
    }

    @ApiOperation(value = "商户统计", notes = "商户统计")
    @RequestMapping(value = "/business/count/{city}", method = RequestMethod.GET)
    private int countBusiness(@PathVariable int city) {
        return businessWeixinService.countBusiness(city);
    }

    @ApiOperation(value = "获取用户参看名片记录", notes = "获取用户参看名片记录")
    @RequestMapping(value = "/business/getSeeCardRecord", method = RequestMethod.POST)
    private int getSeeCardRecord(@RequestBody Map map) {
        int seeCardId = Integer.parseInt(map.get("seeCardId").toString());
        int seenCardId = Integer.parseInt(map.get("seenCardId").toString());
        return businessWeixinService.getSeeCardRecord(seeCardId, seenCardId);
    }

    @ApiOperation(value = "更新用户查看名片联系方式状态", notes = "更新用户查看名片联系方式状态")
    @RequestMapping(value = "/business/updateSeeContact", method = RequestMethod.POST)
    private int updateSeeContact(@RequestBody Map map) {
        int seeCardId = Integer.parseInt(map.get("seeCardId").toString());
        int seenCardId = Integer.parseInt(map.get("seenCardId").toString());
        return businessWeixinService.updateSeeContact(seeCardId, seenCardId);
    }

    @ApiOperation(value = "更新头像地址", notes = "更新头像地址")
    @RequestMapping(value = "/business/updateHeadingImgUrl", method = RequestMethod.POST)
    private int updateHeadingImgUrl(@RequestBody Map map) {
        return businessWeixinService.updateHeadingImgUrl(map);
    }

    @ApiOperation(value = "添加用户参看名片记录", notes = "添加用户参看名片记录")
    @RequestMapping(value = "/business/addSeeCardRecord", method = RequestMethod.POST)
    private int addSeeCardRecord(@RequestBody Map map) {
        return businessWeixinService.addSeeCardRecord(map);
    }

    @ApiOperation(value = "具体商户信息", notes = "具体商户信息")
    @RequestMapping(value = "/business/info/{id}", method = RequestMethod.GET)
    private List<LinkedHashMap> oneBusiness(@PathVariable String id) {
        return businessWeixinService.oneBusiness(id);
    }

    @ApiOperation(value = "获取根据code信息", notes = "具体商户信息")
    @RequestMapping(value = "/business/info/code/{openId}", method = RequestMethod.GET)
    private int getIdByCode(@PathVariable String openId) {
        return businessWeixinService.getIdByOpen(openId);
    }

    @RequestMapping(value = "/business/info/getOpenId/{code}", method = RequestMethod.GET)
    private Map<String, Object> getOpenId(@PathVariable String code) {
        return businessWeixinService.getOpenIdByCode(code);
    }

    @ApiOperation(value = "关注", notes = "关注")
    @RequestMapping(value = "/business/addFollower/{userId}-{followerId}", method = RequestMethod.GET)
    private int addFollower(@PathVariable int userId, @PathVariable int followerId) {
        return businessWeixinService.addFollower(userId, followerId);
    }

    @ApiOperation(value = "取消关注", notes = "取消关注")
    @RequestMapping(value = "/business/deleteFollower/{userId}-{followerId}", method = RequestMethod.GET)
    private int deleteFollower(@PathVariable int userId, @PathVariable int followerId) {
        return businessWeixinService.deleteFollower(userId, followerId);
    }

    @ApiOperation(value = "服务列表", notes = "服务列表")
    @RequestMapping(value = "/business/service/list", method = RequestMethod.GET)
    private List<LinkedHashMap> getAllService() {
        return businessWeixinService.getAllService();
    }

    @ApiOperation(value = "更新用户", notes = "更新用户")
    @RequestMapping(value = "/business/setUser", method = RequestMethod.POST)
    private int addUserCard(@RequestBody Map map) {
        logger.info("用户从客户端发起 用户数据修改, {}", map.toString());
        return businessWeixinService.setUser(map);
    }

    @ApiOperation(value = "获取用户标签", notes = "获取用户标签")
    @RequestMapping(value = "/business/tag/{id}", method = RequestMethod.GET)
    private Map getCardTagById(@PathVariable String id) {
        return businessWeixinService.getCardTagById(id);
    }

    @ApiOperation(value = "获取我关注的", notes = "获取我关注的")
    @RequestMapping(value = "/business/my/follower/{current_id}-{my_id}", method = RequestMethod.GET)
    private List<LinkedHashMap> getFollowerById(@PathVariable String current_id, @PathVariable String my_id) {
        return businessWeixinService.getFollowerById(current_id, my_id);
    }

    @ApiOperation(value = "获取关注我的", notes = "获取关注我的")
    @RequestMapping(value = "/business/my/fans/{current_id}-{my_id}", method = RequestMethod.GET)
    private List<LinkedHashMap> getFansById(@PathVariable String current_id, @PathVariable String my_id) {
        return businessWeixinService.getFansById(current_id, my_id);
    }

    @ApiOperation(value = "批量关注", notes = "批量关注")
    @RequestMapping(value = "/business/batchAddFollower/{userId}-{followerIdList}", method = RequestMethod.GET)
    private int batchAddFollower(@PathVariable int userId, @PathVariable String followerIdList) {
        return businessWeixinService.batchAddFollower(userId, followerIdList.split(","));
    }

    @ApiOperation(value = "是否关注", notes = "获取某用户是否关注")
    @RequestMapping(value = "/business/hasFollowed/{userId}-{followerId}", method = RequestMethod.GET)
    private int hashFollowed(@PathVariable int userId, @PathVariable int followerId) {
        return businessWeixinService.hasFollowed(userId, followerId) ? 1 : 0;
    }

    @ApiOperation(value = "签到", notes = "签到")
    @RequestMapping(value = "/business/sign/{id}", method = RequestMethod.GET)
    private int sign(@PathVariable("id") int id) {
        return businessWeixinService.sign(id);
    }

    @ApiOperation(value = "是否签到", notes = "签到")
    @RequestMapping(value = "/business/hasSigned/{id}", method = RequestMethod.GET)
    private int hashSigned(@PathVariable("id") int id) {
        return businessWeixinService.hasScoreHistory(id);
    }


    @ApiOperation(value = "获取所有城市", notes = "获取所有城市")
    @RequestMapping(value = "/business/listCitys", method = RequestMethod.GET)
    private List<LinkedHashMap> listCitys() {
        return businessWeixinService.listCitys();
    }

    @ApiOperation(value = "获取单个城市", notes = "获取单个城市")
    @RequestMapping(value = "/business/oneCity/{id}", method = RequestMethod.GET)
    private WxOneCity oneCity(@PathVariable int id) {
        return businessWeixinService.oneCity(id);
    }

    @ApiOperation(value = "模糊匹配城市", notes = "模糊匹配城市")
    @RequestMapping(value = "/business/searchCity/{cityName}", method = RequestMethod.GET)
    private List<LinkedHashMap> searchCity(@PathVariable String cityName) {
        return businessWeixinService.searchCity(cityName);
    }

    @ApiOperation(value = "获取企业服务和基地预定未读消息数量", notes = "获取企业服务和基地预定未读消息数量")
    @RequestMapping(value = "/business/getUnreadNum", method = RequestMethod.POST)
    private Map<String, Object> getUnreadNum() {
        return businessWeixinService.getUnreadNum();
    }

    @ApiOperation(value = "获取标签名称", notes = "获取标签名称")
    @RequestMapping(value = "/business/getTagNameList", method = RequestMethod.POST)
    private List<String> getTagNameList(@RequestBody Map map) {
        return businessWeixinService.getTagNameList(map);
    }
}
