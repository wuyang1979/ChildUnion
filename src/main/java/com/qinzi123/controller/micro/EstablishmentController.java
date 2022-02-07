package com.qinzi123.controller.micro;

import com.qinzi123.service.EstablishmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: EstablishmentController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "企业机构")
public class EstablishmentController {

    @Autowired
    EstablishmentService establishmentService;

    @RequestMapping(value = "/establishment/list", method = RequestMethod.POST)
    private List<LinkedHashMap> listEstablishment(@RequestBody Map map) {
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        return establishmentService.listEstablishment(start, num);
    }

    @RequestMapping(value = "/establishment/getEstablishmentInfoById", method = RequestMethod.POST)
    private List<LinkedHashMap> getEstablishmentInfoById(@RequestBody Map map) {
        int establishmentId = Integer.parseInt(map.get("establishmentId").toString());
        return establishmentService.getEstablishmentInfoById(establishmentId);
    }

    @RequestMapping(value = "/establishment/getMemberList", method = RequestMethod.POST)
    private List<LinkedHashMap> getMemberList(@RequestBody Map map) {
        int establishmentId = Integer.parseInt(map.get("id").toString());
        return establishmentService.getMemberList(establishmentId);
    }

    @RequestMapping(value = "/establishment/getEstablishmemtMainMember", method = RequestMethod.POST)
    private List<LinkedHashMap> getEstablishmemtMainMember(@RequestBody Map map) {
        int establishmentId = Integer.parseInt(map.get("id").toString());
        return establishmentService.getMainMember(establishmentId);
    }

    @RequestMapping(value = "/establishment/searchList", method = RequestMethod.POST)
    private List<LinkedHashMap> listSearchEstablishment(@RequestBody Map map) {
        return establishmentService.listSearchEstablishment(map);
    }

    @RequestMapping(value = "/establishment/screenEstablishmentList", method = RequestMethod.POST)
    private List<LinkedHashMap> listScreenEstablishment(@RequestBody Map map) {
        return establishmentService.listScreenEstablishment(map);
    }

    @ApiOperation(value = "新增入驻机构", notes = "新增入驻机构")
    @RequestMapping(value = "/establishment/addEstablishment", method = RequestMethod.POST)
    private int addEstablishment(@RequestBody Map map) {
        return establishmentService.addEstablishment(map);
    }

    @ApiOperation(value = "修改入驻机构", notes = "修改入驻机构")
    @RequestMapping(value = "/establishment/updateEstablishment", method = RequestMethod.POST)
    private int updateEstablishment(@RequestBody Map map) {
        return establishmentService.updateEstablishment(map);
    }


    @ApiOperation(value = "检查机构是否已存在", notes = "检查机构是否已存在")
    @RequestMapping(value = "/establishment/checkEstablishmentExist", method = RequestMethod.POST)
    private List<LinkedHashMap> getEstablishmentListByCompany(@RequestBody Map map) {
        return establishmentService.getEstablishmentListByCompany(map);
    }

    @ApiOperation(value = "获取机构管理人姓名", notes = "获取机构管理人姓名")
    @RequestMapping(value = "/establishment/getAdminName", method = RequestMethod.POST)
    private Map<String, Object> getAdminName(@RequestBody Map map) {
        return establishmentService.getAdminName(map);
    }

    @ApiOperation(value = "获取机构信息", notes = "获取机构信息")
    @RequestMapping(value = "/establishment/getEstablishmentInfo", method = RequestMethod.POST)
    private List<LinkedHashMap> getEstablishmentInfoByCompanyId(@RequestBody Map map) {
        return establishmentService.getEstablishmentInfoByCompanyId(map);
    }

    @ApiOperation(value = "判断是否是已注册机构成员", notes = "判断是否是已注册机构成员")
    @RequestMapping(value = "/establishment/isExistEstablishmentMember", method = RequestMethod.POST)
    private Map<String, Object> isExistEstablishmentMember(@RequestBody Map map) {
        return establishmentService.isExistEstablishmentMember(map);
    }

    @ApiOperation(value = "获取最近入驻机构信息", notes = "获取最近入驻机构信息")
    @RequestMapping(value = "/establishment/getLatestEstablishmentList", method = RequestMethod.POST)
    private List<LinkedHashMap> getLatestEstablishmentList(@RequestBody Map map) {
        return establishmentService.getLatestEstablishmentList(map);
    }
}
