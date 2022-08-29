package com.qinzi123.controller.micro;

import com.qinzi123.service.TeamService;
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
 * @title: CustomerController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/8/03 0030 11:44
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "我的团队")
public class TeamController {

    @Resource
    private TeamService teamService;

    @ApiOperation(value = "获取分销小店的团队列表", notes = "获取分销小店的团队列表")
    @RequestMapping(value = "/team/getAllTeamMemberList", method = RequestMethod.POST)
    private List<LinkedHashMap> getAllTeamMemberList(@RequestBody Map map) {
        return teamService.getAllTeamMemberList(map);
    }

    @ApiOperation(value = "获取搜索分销小店的团队", notes = "获取搜索分销小店的团队")
    @RequestMapping(value = "/team/getSearchTeamMemberList", method = RequestMethod.POST)
    private List<LinkedHashMap> getSearchTeamMemberList(@RequestBody Map map) {
        return teamService.getSearchTeamMemberList(map);
    }

    @ApiOperation(value = "获取团队成员分销订单列表", notes = "获取团队成员分销订单列表")
    @RequestMapping(value = "/team/getDistributionOrderListById", method = RequestMethod.POST)
    private List<LinkedHashMap> getDistributionOrderListById(@RequestBody Map map) {
        return teamService.getDistributionOrderListById(map);
    }

    @ApiOperation(value = "发展团队成员，受邀人扫码确认", notes = "发展团队成员，受邀人扫码确认")
    @RequestMapping(value = "/team/becomeTeamMemberByInvited", method = RequestMethod.POST)
    private int becomeTeamMemberByInvited(@RequestBody Map map) throws Exception {
        return teamService.becomeTeamMemberByInvited(map);
    }
}
