package com.qinzi123.controller.web;

import com.qinzi123.happiness.service.PCShowService;
import com.qinzi123.happiness.util.HttpResponseUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @title: PCShowController
 * @package: com.qinzi123.controller.web
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Api(value = "网页版本")
@RestController
@RequestMapping("/pcshow")
public class PCShowController {

    @Autowired
    PCShowService pcShowService;

    private Map<String, Object> dataMap;


    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public Map<String, Object> returnService(List<Map<String, Object>> data) {
        return HttpResponseUtil.genSuccessfulMsg(data);
    }

    public Map<String, Object> returnTotleService(List<Map<String, Object>> data, Object totalRecords) {
        return HttpResponseUtil.genSuccessfulTolMsg(data, totalRecords);

    }

/*	@GetMapping("/getVipLeague")
	public Map getVipLeague(@RequestParam("json") String json){
		return pcShowService.getVipLeague(json);
	}*/

    @GetMapping("/getAllBussinesseMessages")
    public Map<String, Object> getAllBussinesseMessages(@RequestParam("json") String json) {
        return returnTotleService(pcShowService.getAllBussinesseMessages(json),
                pcShowService.getAllBusMesTotalRecords(json));
    }

    @GetMapping("/getBussLeagues")
    public Map<String, Object> getBussLeagues(@RequestParam("json") String json) {
        return returnTotleService(pcShowService.getBussLeagues(json),
                pcShowService.getBussLeaguesTotalRecords(json));
    }

    @GetMapping("/getVipLeaguers")
    public Map<String, Object> getVipLeaguers(@RequestParam("json") String json) {
        return returnTotleService(pcShowService.getVipLeaguers(json),
                pcShowService.getLeaguersTotalRecords());
    }

    @GetMapping("/getBussinessCampaigns")
    public Map<String, Object> getBussinessCampaigns(@RequestParam("json") String json) {
        return returnTotleService(pcShowService.getBussinessCampaigns(json),
                pcShowService.getBusCampTotalRecords(json));
    }

    @GetMapping("/getCardInfo")
    public Map<String, Object> getCardInfo(@RequestParam("json") String json) {
        return returnTotleService(pcShowService.getCardInfo(json), pcShowService.getCardTotalRecords());
    }

    @GetMapping("/getBussinessCampaignsById")
    public Map<String, Object> getBussinessCampaignsById(@RequestParam("json") String json) {
        return returnService(pcShowService.getBussinessCampaignsById(json));
    }

    @GetMapping("/getLeaguersById")
    public Map<String, Object> getLeaguersById(@RequestParam("json") String json) {
        return returnService(pcShowService.getLeaguersById(json));
    }
}
