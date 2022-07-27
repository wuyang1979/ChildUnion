package com.qinzi123.controller.micro;

import com.qinzi123.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @title: BannerController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "广告处理")
public class BannerController {

    @Resource
    BannerService bannerService;

    @ApiOperation(value = "广告查询", notes = "广告查询")
    @RequestMapping(value = "/banner/list", method = RequestMethod.GET)
    private List<LinkedHashMap> listAllBanners() {
        return bannerService.listAllBanners();
    }

    @ApiOperation(value = "单个广告", notes = "单个广告")
    @RequestMapping(value = "/banner/{id}", method = RequestMethod.GET)
    private List<LinkedHashMap> oneBanner(@PathVariable int id) {
        return bannerService.oneBanner(id);
    }

}
