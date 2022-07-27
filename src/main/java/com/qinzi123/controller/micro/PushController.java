package com.qinzi123.controller.micro;

import com.qinzi123.dto.WxSmallFormId;
import com.qinzi123.service.PushMiniProgramService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @title: PushController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "获取formId")
public class PushController {

    @Resource
    PushMiniProgramService pushService;

    @ApiOperation(value = "新增form", notes = "新增form")
    @RequestMapping(value = "/formId/{id}", method = RequestMethod.POST)
    private int addFormId(@PathVariable("id") String id,
                          @RequestBody WxSmallFormId wxSmallFormId) {
        return pushService.addFormId(wxSmallFormId);
    }

    @ApiOperation(value = "批量新增form", notes = "批量新增form")
    @RequestMapping(value = "/formList/{card}-{idList}", method = RequestMethod.POST)
    private int batchAddFormId(@PathVariable("card") int card,
                               @PathVariable("idList") String idList) {
        return pushService.batchAddFormId(card, idList.split(","));
    }

}
