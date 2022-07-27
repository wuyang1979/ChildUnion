package com.qinzi123.controller.micro;

import com.qinzi123.service.ProfitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @title: ProfitController
 * @package: com.qinzi123.controller.micro
 * @projectName: childunion
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/6/10 0010 10:17
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "分销合伙人收益及提现")
public class ProfitController {

    @Resource
    private ProfitService profitService;

    @ApiOperation(value = "获取分销合伙人提现信息", notes = "获取分销合伙人提现信息")
    @RequestMapping(value = "/profit/getDistributionPartnerProfitInfo", method = RequestMethod.POST)
    private Map getDistributionPartnerProfitInfo(@RequestBody Map map) {
        return profitService.getDistributionPartnerProfitInfo(map);
    }
}
