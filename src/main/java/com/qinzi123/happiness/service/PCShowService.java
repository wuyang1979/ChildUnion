package com.qinzi123.happiness.service;

import java.util.List;
import java.util.Map;

/**
 * @title: PCShowService
 * @package: com.qinzi123.happiness.service
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface PCShowService {
    public List<Map<String, Object>> getCardInfo(String json);

    public List<Map<String, Object>> getBussinessCampaigns(String json);

    public List<Map<String, Object>> getAllBussinesseMessages(String json);

    public List<Map<String, Object>> getBussLeagues(String json);

    public List<Map<String, Object>> getVipLeaguers(String json);

    public List<Map<String, Object>> getBussinessCampaignsById(String json);

    public List<Map<String, Object>> getLeaguersById(String json);

    public Object getBusCampTotalRecords(String json);

    public Object getAllBusMesTotalRecords(String json);

    public Object getBussLeaguesTotalRecords(String json);

    public Object getLeaguersTotalRecords();

    public Object getCardTotalRecords();
}
