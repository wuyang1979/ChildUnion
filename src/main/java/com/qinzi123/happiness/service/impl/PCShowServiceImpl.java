package com.qinzi123.happiness.service.impl;

import com.qinzi123.dao.PCShowDao;
import com.qinzi123.happiness.domain.*;
import com.qinzi123.happiness.service.PCShowService;
import com.qinzi123.happiness.util.CalendarUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: PCShowServiceImpl
 * @package: com.qinzi123.happiness.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class PCShowServiceImpl implements PCShowService {

    private static final Logger LOG = LoggerFactory.getLogger(PCShowServiceImpl.class);

    static class Request {
        public int start = 1;
        public int currentPage = 1;
        public int pageSize = 1000;
        public String key = null;
        public int id;
    }

    private String json = null;
    private Request request = new Request();

    @Resource
    private PCShowDao pcshowdao;

    public PCShowDao getPCShowDao() {
        return pcshowdao;
    }

    public void setPCShowDao(PCShowDao PCShowDao) {
        this.pcshowdao = PCShowDao;
    }

    private void parseJson(String json) {
        try {
            if (json == null)
                return;

            JSONObject jsonObj = new JSONObject(json);
            if (jsonObj.has("currentPage"))
                request.currentPage = jsonObj.getInt("currentPage");
            request.start = request.currentPage > 0 ? request.currentPage - 1 : request.currentPage;
            if (jsonObj.has("pageSize"))
                request.pageSize = jsonObj.getInt("pageSize");
            request.start = request.start * request.pageSize;
            if (jsonObj.has("id"))
                request.id = jsonObj.getInt("id");
            if (jsonObj.has("key")) {
                request.key = jsonObj.getString("key");
            } else {
                request.key = null;
            }
        } catch (JSONException e) {
            LOG.error("getJson  error.", e);
        }
    }

    // 名片信息
    @Override
    public List<Map<String, Object>> getCardInfo(String json) {
        return getMapCardInfo(json);
    }

    public List<Map<String, Object>> getMapCardInfo(String json) {
        parseJson(json);
        List<CardInfo> cardInfos = pcshowdao.getCardInfo(request.start,
                request.pageSize);
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for (CardInfo cardInfo : cardInfos) {
            map = new HashMap<String, Object>();
            map.put("id", cardInfo.getId());
            map.put("realName", cardInfo.getRealName());
            map.put("company", cardInfo.getCompany());
            map.put("headImgUrl", cardInfo.getHeadImgUrl());
            map.put("introduce", cardInfo.getIntroduce());
            map.put("permission", cardInfo.getPermission());
            map.put("datetime", cardInfo.getDatetime());
            map.put("job", cardInfo.getJob());
            map.put("leaguer", cardInfo.getLeaguer());
            ret.add(map);
        }
        return ret;
    }

    @Override
    public Object getCardTotalRecords() {
        return pcshowdao.getCardTotalRecords().get(0);
    }

    // 活动详情
    @Override
    public List<Map<String, Object>> getBussinessCampaigns(String json) {
        parseJson(json);
        if (request.key == null) {
            List<BussinessPcShowCampaigns> bussinessPcShowCampaigns = pcshowdao
                    .getBussinessCampaigns(request.start,
                            request.pageSize);
            return bussinessMapCampaigns(bussinessPcShowCampaigns);
        } else {
            List<BussinessPcShowCampaigns> bussinessPcShowCampaigns = pcshowdao
                    .searchCampaigns(request.key, request.start,
                            request.pageSize);
            return bussinessMapCampaigns(bussinessPcShowCampaigns);
        }
    }

    public List<Map<String, Object>> bussinessMapCampaigns(
            List<BussinessPcShowCampaigns> bussiness) {
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for (BussinessPcShowCampaigns bussinessPcShowCampaign : bussiness) {
            map = new HashMap<String, Object>();
            map.put("imagepath", bussinessPcShowCampaign.getImagePath());
            map.put("cost", bussinessPcShowCampaign.getCost());
            map.put("childcost", bussinessPcShowCampaign.getChildCost());
            map.put("datetimespan", CalendarUtil.combineShowTime(
                    bussinessPcShowCampaign.getDatetime(),
                    bussinessPcShowCampaign.getEndDatetime()));
            map.put("id", bussinessPcShowCampaign.getId());
            map.put("title", bussinessPcShowCampaign.getTitle());
            map.put("url", bussinessPcShowCampaign.getUrl());
            map.put("agemin", bussinessPcShowCampaign.getAgemin());
            map.put("agemax", bussinessPcShowCampaign.getAgemax());
            map.put("address", bussinessPcShowCampaign.getAddress());
            map.put("amendSignup", bussinessPcShowCampaign.getAmendSignup());
            map.put("peopleNumber", bussinessPcShowCampaign.getPeopleNumber());
            map.put("statusimagepath",
                    getStatusImagePath(bussinessPcShowCampaign.getEndDatetime()));
            map.put("mincost", bussinessPcShowCampaign.getCost() <= 0 ? 0
                    : bussinessPcShowCampaign.getCost());
            map.put("datetimespan", CalendarUtil.combineShowTime(
                    bussinessPcShowCampaign.getDatetime(),
                    bussinessPcShowCampaign.getEndDatetime()));
            ret.add(map);
        }
        return ret;
    }

    @Override
    public Object getBusCampTotalRecords(String json) {
        parseJson(json);
        if (request.key == null) {
            return pcshowdao.getBusCampTotalRecords().get(0);
        } else {
            return pcshowdao.getSearCampTotalRecords(request.key).get(0);
        }
    }

    // 动态信息
    @Override
    public List<Map<String, Object>> getAllBussinesseMessages(String json) {
        parseJson(json);
        if (request.key == null) {
            List<BussinessMessage> bussinessMessages = pcshowdao
                    .getAllBussinesseMessages(request.start,
                            request.pageSize);
            return bussinesseMapMessages(bussinessMessages);
        } else {
            List<BussinessMessage> bussinessMessages = pcshowdao
                    .searchBussinesseMessages(request.key, request.start,
                            request.pageSize);
            return bussinesseMapMessages(bussinessMessages);
        }
    }

    private List<Map<String, Object>> bussinesseMapMessages(
            List<BussinessMessage> bussinessMessages) {
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for (BussinessMessage bussinessMessage : bussinessMessages) {
            map = new HashMap<String, Object>();
            map.put("id", bussinessMessage.getId());
            map.put("title", bussinessMessage.getTitle());
            map.put("content", bussinessMessage.getContent());
            map.put("createtime", bussinessMessage.getCreateTime().getTime());
            map.put("status", bussinessMessage.getStatus());
            map.put("bussinessid", bussinessMessage.getBussinessId());
            map.put("bussinessname", bussinessMessage.getBussinessInfo()
                    .getBussinessName());
            map.put("bussinessnick", bussinessMessage.getBussinessInfo()
                    .getNickname());
            map.put("userheadimgurl", bussinessMessage.getBussinessInfo()
                    .getHeadImgUrl());
            map.put("readnumber", bussinessMessage.getReadNum());
            ret.add(map);
        }
        return ret;
    }

    @Override
    public Object getAllBusMesTotalRecords(String json) {
        parseJson(json);
        if (request.key == null) {
            return pcshowdao.getAllBusMesTotalRecords().get(0);
        } else {
            return pcshowdao.getSearBusMesTotalRecords(request.key).get(0);
        }
    }

    // 最新会员
    @Override
    public List<Map<String, Object>> getBussLeagues(String json) {
        parseJson(json);
        if (request.key == null) {
            List<BussLeagueInfo> bussLeagueInfos = pcshowdao.getBussLeagues(
                    request.start, request.pageSize);
            return mapLeaguers(bussLeagueInfos);
        } else {
            List<BussLeagueInfo> bussLeagueInfos = pcshowdao.searchVipLeaguers(
                    request.key, request.start, request.pageSize);
            return mapLeaguers(bussLeagueInfos);
        }
    }

    public List<Map<String, Object>> mapLeaguers(List<BussLeagueInfo> leagueInfo) {
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for (BussLeagueInfo bussLeagueInfo : leagueInfo) {
            map = new HashMap<String, Object>();
            map.put("id", bussLeagueInfo.getId());
            map.put("leaguetype", bussLeagueInfo.getLeagueType());
            map.put("company", bussLeagueInfo.getCompany());
            map.put("companydesc", bussLeagueInfo.getCompanyDesc());
            map.put("licensepic", bussLeagueInfo.getLicensePic());
            map.put("maindemand", bussLeagueInfo.getMainDemand());
            map.put("logoPic", bussLeagueInfo.getLogoPic());
            ret.add(map);
        }
        return ret;
    }

    @Override
    public Object getBussLeaguesTotalRecords(String json) {
        parseJson(json);
        if (request.key == null) {
            return pcshowdao.getBussLeaguesTotalRecords().get(0);
        } else {
            return pcshowdao.getSearLeaguesTotalRecords(request.key).get(0);
        }
    }

    // 获取最新会员信息
    @Override
    public List<Map<String, Object>> getVipLeaguers(String json) {
        parseJson(json);
        List<BussLeagueInfo> bussLeagueInfos = pcshowdao.getLeaguers(
                request.start, request.pageSize);
        return mapLeaguers(bussLeagueInfos);
    }

    @Override
    public Object getLeaguersTotalRecords() {
        return pcshowdao.getLeaguersTotalRecords().get(0);
    }

    @Override
    public List<Map<String, Object>> getBussinessCampaignsById(String json) {
        return getMapBussinessCampaignsById(json);
    }

    public List<Map<String, Object>> getMapBussinessCampaignsById(String json) {
        parseJson(json);
        List<CampaignPcshowInfo> campaignPcshowInfos = pcshowdao
                .getBussinessCampaignsById(request.id);
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for (CampaignPcshowInfo campaignPcshowInfo : campaignPcshowInfos) {
            map = new HashMap<String, Object>();
            map.put("id", campaignPcshowInfo.getId());
            map.put("content", campaignPcshowInfo.getContent());
            map.put("imagepath", campaignPcshowInfo.getImagepath());
            map.put("peopleNumber", campaignPcshowInfo.getPeopleNumber());
            map.put("amendSignup", campaignPcshowInfo.getAmendSignup());
            map.put("agemin", campaignPcshowInfo.getAgemin());
            map.put("agemax", campaignPcshowInfo.getAgemax());
            map.put("cost", campaignPcshowInfo.getCost());
            map.put("childCost", campaignPcshowInfo.getChildCost());
            map.put("address", campaignPcshowInfo.getAddress());
            map.put("phone", campaignPcshowInfo.getPhone());
            map.put("organizationName",
                    campaignPcshowInfo.getOrganizationName());
            map.put("organizationPhone",
                    campaignPcshowInfo.getOrganizationPhone());
            map.put("datetimespan", CalendarUtil.combineShowTime(
                    campaignPcshowInfo.getDatetime(),
                    campaignPcshowInfo.getEndDatetime()));
            map.put("signUpTimeRange", CalendarUtil.combineShowTime(
                    campaignPcshowInfo.getSignupStartTime(),
                    campaignPcshowInfo.getSignupEndTime()));
            ret.add(map);
        }
        return ret;
    }

    // 点击会员
    @Override
    public List<Map<String, Object>> getLeaguersById(String json) {
        return getMapLeaguersById(json);
    }

    public List<Map<String, Object>> getMapLeaguersById(String json) {
        parseJson(json);
        List<BussLeagueInfo> bussLeagueInfos = pcshowdao
                .getMapLeaguersById(request.id);
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for (BussLeagueInfo bussLeagueInfo : bussLeagueInfos) {
            map = new HashMap<String, Object>();
            map.put("id", bussLeagueInfo.getId());
            map.put("leagueType", bussLeagueInfo.getLeagueType());
            map.put("companyDesc", bussLeagueInfo.getCompanyDesc());
            map.put("company", bussLeagueInfo.getCompany());
            map.put("logoPic", bussLeagueInfo.getLogoPic());
            map.put("foundTime", bussLeagueInfo.getFoundTime());
            map.put("members", bussLeagueInfo.getMembers());
            map.put("companyAddr", bussLeagueInfo.getCompanyAddr());
            map.put("companyTel", bussLeagueInfo.getCompanyTel());
            map.put("companyWeb", bussLeagueInfo.getCompanyWeb());
            map.put("industry", bussLeagueInfo.getIndustry());
            map.put("mainBussiness", bussLeagueInfo.getMainBussiness());
            map.put("mainDemand", bussLeagueInfo.getMainDemand());
            map.put("licensePic", bussLeagueInfo.getLicensePic());
            map.put("contactName", bussLeagueInfo.getContactName());
            map.put("contactDuty", bussLeagueInfo.getContactDuty());
            map.put("contactTel", bussLeagueInfo.getContactTel());
            map.put("contactWX", bussLeagueInfo.getContactWX());
            map.put("submitTime", bussLeagueInfo.getSubmitTime());
            map.put("updateTime", bussLeagueInfo.getUpdateTime());
            map.put("contactOpenId", bussLeagueInfo.getContactOpenId());
            map.put("cooperate", getBussLeagueCooperateById(json));
            ret.add(map);
        }
        return ret;
    }

    public List<Map<String, Object>> getBussLeagueCooperateById(String json) {
        parseJson(json);
        List<BussLeagueCooperate> bussLeagueCooperates = pcshowdao
                .getBussLeagueCooperateById(request.id);
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        List<CardInfo> cardInfos = pcshowdao.queryCardInfos();
        Map<String, CardInfo> cardmap = getCardInfoMap(cardInfos);
        Map<String, Object> map = null;
        for (BussLeagueCooperate bussLeagueCooperate : bussLeagueCooperates) {
            map = new HashMap<String, Object>();
            map.put("id", bussLeagueCooperate.getId());
            map.put("bussleagueid", bussLeagueCooperate.getBussLeagueId());
            map.put("datetime", bussLeagueCooperate.getDatetime().getTime());
            map.put("purposeopenid", bussLeagueCooperate.getPurposeOpenId());
            map.put("purposename",
                    cardmap.containsKey(bussLeagueCooperate.getPurposeOpenId()) ? cardmap
                            .get(bussLeagueCooperate.getPurposeOpenId())
                            .getRealName() : "匿名");
            ret.add(map);
        }
        return ret;
    }

    private Map<String, CardInfo> getCardInfoMap(List<CardInfo> cardInfos) {
        Map<String, CardInfo> ret = new HashMap<String, CardInfo>();
        if (null == cardInfos || cardInfos.size() == 0)
            return ret;
        for (CardInfo cardInfo : cardInfos) {
            ret.put(cardInfo.getOpenId(), cardInfo);
        }
        return ret;
    }

    private String getStatusImagePath(long endTime) {
        // 活动结束时间<=当前时间，则活动已结束
        return endTime <= System.currentTimeMillis() ? "../images/campaign/campaign_title_status_finished.png"
                : "../images/campaign/campaign_title_status_new.png";
    }
}
