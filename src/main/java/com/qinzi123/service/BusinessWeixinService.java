package com.qinzi123.service;

import com.qinzi123.dto.WxOneCity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: BusinessWeixinService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface BusinessWeixinService {

    List<LinkedHashMap> listBusiness(String id, int start, int num, String search, Integer tagId, Integer city);

    List<LinkedHashMap> selectBusinessListByFirstTagId(String id, int start, int num, String tagId, Integer city);

    List<LinkedHashMap> selectIndustryList(String id, String search, Integer tagId, Integer city);

    int countBusiness(Integer city);

    int getSeeCardRecord(Integer seeCardId, Integer seenCardId);

    int updateSeeContact(Integer seeCardId, Integer seenCardId);

    List<LinkedHashMap> oneBusiness(String id);

    List<LinkedHashMap> getTagList();

    List<LinkedHashMap> getSubServiceList();

    int getIdByCode(String code);

    int getIdByOpen(String openId);

    Map<String, Object> getOpenIdByCode(String code);

    int addFollower(int userId, int followerId);

    int deleteFollower(int userId, int followerId);

    boolean hasFollowed(int userId, int followerId);

    List<LinkedHashMap> getAllService();

    int setUser(Map map);

    int updateHeadingImgUrl(Map map);

    int addSeeCardRecord(Map map);

    Map getCardTagById(String id);

    List<LinkedHashMap> getFollowerById(String current_id, String my_id);

    List<LinkedHashMap> getFansById(String current_id, String my_id);

    int batchAddFollower(int userId, String[] idList);

    int sign(int cardId);

    int hasScoreHistory(int cardId);

    List<LinkedHashMap> listCitys();

    List<LinkedHashMap> searchCity(String cityName);

    WxOneCity oneCity(int id);

    Map<String, Object> getUnreadNum();

    List<String> getTagNameList(Map map);
}
