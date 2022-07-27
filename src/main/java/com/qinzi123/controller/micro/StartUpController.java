package com.qinzi123.controller.micro;

import com.qinzi123.service.StartUpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @title: StartUpController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/1/6 0006 14:13
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "启动控制层")
public class StartUpController {

    @Resource
    private StartUpService startUpService;

    @ApiOperation(value = "新增小程序访问次数", notes = "新增小程序访问次数")
    @RequestMapping(value = "/start/addVisitCount", method = RequestMethod.POST)
    private int addVisitCount(@RequestBody Map map) {
        return startUpService.addVisitCount();
    }

}
