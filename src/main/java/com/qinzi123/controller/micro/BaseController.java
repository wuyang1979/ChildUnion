package com.qinzi123.controller.micro;

import com.qinzi123.service.BaseService;
import com.qinzi123.util.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: BaseController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "活动基地")
public class BaseController {

    @Resource
    BaseService baseService;

    @RequestMapping(value = "/base/list", method = RequestMethod.POST)
    private List<LinkedHashMap> listBase(@RequestBody Map map) {
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        List<LinkedHashMap> baseList = baseService.listBase(start, num);
        baseList.forEach(iMap -> {
            iMap.put("topic_type", baseService.getTopicType(iMap.get("topic_type_id").toString()));
            iMap.put("card_name", baseService.getCardName(iMap.get("card_id").toString()));
            iMap.put("number", baseService.getConsultNumber(iMap.get("id").toString()));
            iMap.put("levelId", iMap.get("level").toString());
            iMap.put("level", baseService.getBaseLevel(iMap.get("level").toString()));
            iMap.put("districtName", baseService.getDistrictName(iMap.get("district").toString()));
        });
        return baseList;
    }

    @RequestMapping(value = "/base/getBaseList", method = RequestMethod.POST)
    private List<LinkedHashMap> getBaseList(@RequestBody Map map) {
        return baseService.getBaseList(map);
    }

    @RequestMapping(value = "/base/searchList", method = RequestMethod.POST)
    private List<LinkedHashMap> listSearchBase(@RequestBody Map map) {
        List<LinkedHashMap> baseList = baseService.listSearchBase(map);
        baseList.forEach(iMap -> {
            iMap.put("topic_type", baseService.getTopicType(iMap.get("topic_type_id").toString()));
            iMap.put("card_name", baseService.getCardName(iMap.get("card_id").toString()));
            iMap.put("number", baseService.getConsultNumber(iMap.get("id").toString()));
            iMap.put("levelId", iMap.get("level").toString());
            iMap.put("level", baseService.getBaseLevel(iMap.get("level").toString()));
            iMap.put("districtName", baseService.getDistrictName(iMap.get("district").toString()));
        });
        return baseList;
    }

    @RequestMapping(value = "/base/screenBaseList", method = RequestMethod.POST)
    private List<LinkedHashMap> screenBaseList(@RequestBody Map map) {
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        int districtCode = Integer.parseInt(map.get("districtCode").toString());
        int typeCode = Integer.parseInt(map.get("typeCode").toString());
        List<LinkedHashMap> baseList = baseService.screenBaseList(districtCode, typeCode, start, num);
        baseList.forEach(iMap -> {
            iMap.put("topic_type", baseService.getTopicType(iMap.get("topic_type_id").toString()));
            iMap.put("card_name", baseService.getCardName(iMap.get("card_id").toString()));
            iMap.put("number", baseService.getConsultNumber(iMap.get("id").toString()));
            iMap.put("levelId", iMap.get("level").toString());
            iMap.put("level", baseService.getBaseLevel(iMap.get("level").toString()));
            iMap.put("districtName", baseService.getDistrictName(iMap.get("district").toString()));
        });
        return baseList;
    }

    @RequestMapping(value = "/base/reserve", method = RequestMethod.POST)
    private Map<String, Object> baseReserve(@RequestBody Map map) {
        map.put("createTime", Utils.getCurrentDate());
        return baseService.baseReserve(map);
    }

    @RequestMapping(value = "/base/pictureList", method = RequestMethod.POST)
    private List<LinkedHashMap> pictureList(@RequestBody Map map) {
        int baseId = Integer.parseInt(map.get("id").toString());
        return baseService.pictureList(baseId);
    }

    @ApiOperation(value = "活动基地咨询列表", notes = "活动基地咨询列表")
    @RequestMapping(value = "/base/consultingeList/{baseId}", method = RequestMethod.GET)
    private List<LinkedHashMap> getAllConsultingeListByBaseId(@PathVariable("baseId") int baseId) {
        return baseService.getAllConsultingeListByBaseId(baseId);
    }

    @ApiOperation(value = "活动基地类型列表", notes = "活动基地类型列表")
    @RequestMapping(value = "/base/getBaseTopicTypeList", method = RequestMethod.POST)
    private List<LinkedHashMap> getBaseTopicTypeList() {
        return baseService.getBaseTopicTypeList();
    }

    @ApiOperation(value = "新增入驻基地", notes = "新增入驻基地")
    @RequestMapping(value = "/base/addBaseEntity", method = RequestMethod.POST)
    private int addBaseEntity(@RequestBody Map map) {
        return baseService.addBaseEntity(map);
    }

    @ApiOperation(value = "修改入驻基地", notes = "修改入驻基地")
    @RequestMapping(value = "/base/updateBaseEntity", method = RequestMethod.POST)
    private int updateBaseEntity(@RequestBody Map map) {
        return baseService.updateBaseEntity(map);
    }

    @ApiOperation(value = "获取活动基地预定列表", notes = "获取活动基地预定列表")
    @RequestMapping(value = "/base/getBaseReserveList", method = RequestMethod.POST)
    private List<LinkedHashMap> getBaseReserveList() {
        return baseService.getBaseReserveList();
    }

    @ApiOperation(value = "修改活动基地预订阅读状态", notes = "修改活动基地预订阅读状态")
    @RequestMapping(value = "/base/readOrder", method = RequestMethod.POST)
    private int readOrder(@RequestBody Map map) {
        int id = Integer.parseInt(map.get("id").toString());
        return baseService.readOrder(id);
    }

    @ApiOperation(value = "获取所有基地坐标信息", notes = "获取所有基地坐标信息")
    @RequestMapping(value = "/base/allBaseMapInfo", method = RequestMethod.POST)
    private List<LinkedHashMap> allBaseMapInfo() {
        return baseService.allBaseMapInfo();
    }
}
