package com.qinzi123.dao;

import com.qinzi123.dto.CardInfo;
import com.qinzi123.dto.WxOneCity;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: CardDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface CardDao {

    /**
     * 商户名单等功能
     **/

    List<LinkedHashMap> listBusiness(@Param("id") String id,
                                     @Param("start") int start,
                                     @Param("num") int num,
                                     @Param("search") String search,
                                     @Param("tagId") Integer tagId,
                                     @Param("city") Integer city);

    List<LinkedHashMap> selectBusinessListByFirstTagId(@Param("id") String id, @Param("start") int start, @Param("num") int num, @Param("tagId") String tagId, @Param("city") Integer city);

    List<LinkedHashMap> selectIndustryList(@Param("id") String id,
                                           @Param("search") String search,
                                           @Param("tagId") Integer tagId,
                                           @Param("city") Integer city);

    int countBusiness(@Param("city") Integer city);

    int getSeeCardRecord(@Param("seeCardId") Integer seeCardId, @Param("seenCardId") Integer seenCardId);

    int updateSeeContact(@Param("seeCardId") Integer seeCardId, @Param("seenCardId") Integer seenCardId);

    List<LinkedHashMap> oneBusiness(@Param("id") String id);

    List<LinkedHashMap> getTagList();

    List<LinkedHashMap> getSubServiceList();

    int addFollower(@Param("userId") int userId, @Param("followerId") int followerId);

    int addFollowerMessage(Map<String, Object> map);

    int deleteFollower(@Param("userId") int userId, @Param("followerId") int followerId);

    Map hasFollowed(@Param("userId") int userId, @Param("followerId") int followerId);

    List<LinkedHashMap> getAllService();

    List<Map> getCardInfoByOpenId(@Param("openid") String openid);

    List<LinkedHashMap> getCendIdByOpenId(Map map);

    Map getAppInfoByAppName(@Param("appName") String appName);

    Map getCardInfoById(@Param("id") String id);

    CardInfo getCardInfoBeanById(@Param("id") int id);

    List<Map> getCardInfoByPhone(@Param("phone") String phone, @Param("realname") String realname);

    int addCardInfo(Map map);

    int updateCardInfo(Map map);

    int refreshCardDate(@Param("id") int id);

    int addCardTag(Map map);

    int updateCardTag(Map map);

    int updateHeadingImgUrl(Map map);

    int updateCendHeadingImgUrl(Map map);

    int addSeeCardRecord(Map map);

    String getCardNameById(@Param("seenCardId") int seenCardId);

    Map getCardTagById(@Param("card_id") String card_id);

    Map<String, Object> getSeeCardNameAndLeaguerById(@Param("seeCardId") int seeCardId);

    List<LinkedHashMap> getFollowerById(@Param("current_id") String current_id, @Param("my_id") String my_id);

    List<LinkedHashMap> getFansById(@Param("current_id") String current_id, @Param("my_id") String my_id);

    int minusScore(Map map);

    int addScore(Map map);

    int addScoreHistory(Map map);

    int addShowScoreHistory(Map map);

    List<Map> hasScoreHistory(@Param("card_id") int card_id, @Param("score_type") int score_type);

    List<LinkedHashMap> listCitys();

    List<LinkedHashMap> searchCity(String cityName);

    WxOneCity oneCity(@Param("id") int id);

    int toLeaguer(Map map);

    int toEnterpriseLeaguer(Map map);

    int toGoldEnterpriseLeaguer(Map map);

    int membershipExpiration(Map map);

    int getEnterpriseOrderUnreadNum();

    int getBaseReserveUnreadNum();

    String getTagName(@Param("tagId") int tagId);

    int updateUserAuthStatusByCard(@Param("userId") int userId);

    int updateFollowerAuthStatusByCard(@Param("followerId") int followerId);

    List<LinkedHashMap> getFollowerAuthListByCard(@Param("followerId") int followerId);

    int getLast(@Param("messageId") int messageId);

    int updateOpenIdByCard(Map map);
}
