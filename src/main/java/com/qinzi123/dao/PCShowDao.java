package com.qinzi123.dao;

import com.qinzi123.happiness.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @title: PCShowDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface PCShowDao {

    List<CardInfo> getCardInfo(@Param("start") int start, @Param("pageSize") int pageSize);

    List<BussinessPcShowCampaigns> getBussinessCampaigns(@Param("start") int start, @Param("pageSize") int pageSize);

    List<BussinessMessage> getAllBussinesseMessages(@Param("start") int start, @Param("pageSize") int pageSize);

    List<BussLeagueInfo> getBussLeagues(@Param("start") int start, @Param("pageSize") int pageSize);

    List<BussLeagueInfo> getLeaguers(@Param("start") int start, @Param("pageSize") int pageSize);

    List<CampaignPcshowInfo> getBussinessCampaignsById(@Param("id") int id);

    List<BussLeagueInfo> getMapLeaguersById(@Param("id") int id);

    List getLeaguersTotalRecords();

    List getBusCampTotalRecords();

    List getBussLeaguesTotalRecords();

    List getAllBusMesTotalRecords();

    List<CardInfo> queryCardInfos();

    List<BussLeagueCooperate> getBussLeagueCooperateById(@Param("id") int id);

    List getCardTotalRecords();

    List<BussinessPcShowCampaigns> searchCampaigns(@Param("str") String str, @Param("start") int start, @Param("pageSize") int pageSize);

    List<BussinessMessage> searchBussinesseMessages(@Param("str") String str, @Param("start") int start, @Param("pageSize") int pageSize);

    List<BussLeagueInfo> searchVipLeaguers(@Param("str") String str, @Param("start") int start, @Param("pageSize") int pageSize);

    List getSearCampTotalRecords(@Param("json") String json);

    List getSearBusMesTotalRecords(@Param("json") String json);

    List getSearLeaguesTotalRecords(@Param("key") String key);
}
