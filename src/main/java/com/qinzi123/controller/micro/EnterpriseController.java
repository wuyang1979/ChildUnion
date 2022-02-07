package com.qinzi123.controller.micro;

import com.qinzi123.dto.CardMessageReply;
import io.swagger.annotations.Api;
import com.qinzi123.service.EnterpriseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: EnterpriseController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "亲子企服")
public class EnterpriseController {

    @Autowired
    EnterpriseService enterpriseService;

    @RequestMapping(value = "/enterprise/list", method = RequestMethod.POST)
    private List<LinkedHashMap> listEnterprise(@RequestBody Map map) {
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        return enterpriseService.listEnterprise(start, num);
    }

    @RequestMapping(value = "/enterprise/screenList", method = RequestMethod.POST)
    private List<LinkedHashMap> screenList(@RequestBody Map map) {
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        String type = map.get("type").toString();
        return enterpriseService.screenList(type, start, num);
    }

    @RequestMapping(value = "/enterprise/pictureList", method = RequestMethod.POST)
    private List<LinkedHashMap> pictureList(@RequestBody Map map) {
        return enterpriseService.pictureList(map);
    }

    @RequestMapping(value = "/enterprise/enterpriseComment/{enterpriseId}", method = RequestMethod.GET)
    private List<LinkedHashMap> getAllEnterpriseCommentByEnterpriseId(@PathVariable("enterpriseId") int enterpriseId) {
        return enterpriseService.getAllEnterpriseCommentByEnterpriseId(enterpriseId);
    }

    @RequestMapping(value = "/enterprise/getConsultingAndCommentCount/{enterpriseId}", method = RequestMethod.GET)
    private Map<String, Object> getConsultingAndCommentCount(@PathVariable("enterpriseId") int enterpriseId) {
        return enterpriseService.getConsultingAndCommentCount(enterpriseId);
    }

    @RequestMapping(value = "/enterprise/enterpriseConsulting/{enterpriseId}", method = RequestMethod.GET)
    private List<LinkedHashMap> getAllEnterpriseConsultingByEnterpriseId(@PathVariable("enterpriseId") int enterpriseId) {
        return enterpriseService.getAllEnterpriseConsultingByEnterpriseId(enterpriseId);
    }

    @RequestMapping(value = "/enterprise/addComment", method = RequestMethod.POST)
    private int addComment(@RequestBody Map map) {
        return enterpriseService.addComment(map);
    }

    @RequestMapping(value = "/enterprise/addEnterpriseConsulting", method = RequestMethod.POST)
    private int addEnterpriseConsulting(@RequestBody Map map) {
        return enterpriseService.addEnterpriseConsulting(map);
    }

    @RequestMapping(value = "/enterprise/getEnterpriseOrderList", method = RequestMethod.POST)
    private List<LinkedHashMap> getEnterpriseOrderList() {
        return enterpriseService.getEnterpriseOrderList();
    }

    @RequestMapping(value = "/enterprise/readOrder", method = RequestMethod.POST)
    private int readOrder(@RequestBody Map map) {
        int id = Integer.parseInt(map.get("id").toString());
        return enterpriseService.readOrder(id);
    }

}
