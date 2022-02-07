package com.qinzi123.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.FollowMessage;
import com.qinzi123.dto.ScoreType;
import com.qinzi123.dto.WxOneCity;
import com.qinzi123.exception.GlobalProcessException;
import com.qinzi123.happiness.util.StringUtil;
import com.qinzi123.service.BusinessWeixinService;
import com.qinzi123.service.PushMiniProgramService;
import com.qinzi123.service.PushOfficialAccountService;
import com.qinzi123.service.ScoreService;
import com.qinzi123.util.DateUtils;
import com.qinzi123.util.Utils;
import com.qinzi123.util.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * @title: BusinessWeixinServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Component
@EnableAsync
public class BusinessWeixinServiceImpl extends AbstractWechatMiniProgramService implements BusinessWeixinService {

    @Autowired
    ScoreService scoreService;

    @Autowired
    PushMiniProgramService pushService;

    private Logger logger = LoggerFactory.getLogger(BusinessWeixinServiceImpl.class);

    /**
     * 列出所有商户, 可查询
     *
     * @param id     某用户
     * @param start  开始记录
     * @param num    查询条目
     * @param search 搜索字段值
     * @return
     */
    public List<LinkedHashMap> listBusiness(String id, int start, int num,
                                            String search, Integer tagId, Integer city) {
        if (search == null || search.trim().length() == 0)
            search = null;
        else
            search = String.format("%%%s%%", search);
        if (tagId == null || tagId == -1)
            tagId = null;
        if (city == null || city == -1)
            city = null;
        return cardDao.listBusiness(id, start, num, search, tagId, city);
    }

    @Override
    public List<LinkedHashMap> selectBusinessListByFirstTagId(String id, int start, int num,
                                                              String tagId, Integer city) {
        if (city == null || city == -1)
            city = null;
        return cardDao.selectBusinessListByFirstTagId(id, start, num, tagId, city);
    }

    public List<LinkedHashMap> selectIndustryList(String id, String search, Integer tagId, Integer city) {
        if (search == null || search.trim().length() == 0)
            search = null;
        else
            search = String.format("%%%s%%", search);
        if (tagId == null || tagId == -1)
            tagId = null;
        if (city == null || city == -1)
            city = null;
        return cardDao.selectIndustryList(id, search, tagId, city);
    }

    @Override
    public int countBusiness(Integer city) {
        return cardDao.countBusiness(city);
    }

    @Override
    public int getSeeCardRecord(Integer seeCardId, Integer seenCardId) {
        return cardDao.getSeeCardRecord(seeCardId, seenCardId);
    }

    @Override
    public int updateSeeContact(Integer seeCardId, Integer seenCardId) {
        return cardDao.updateSeeContact(seeCardId, seenCardId);
    }

    /**
     * 查询某个商户情况
     *
     * @param id 商户ID
     * @return
     */
    public List<LinkedHashMap> oneBusiness(String id) {
        return cardDao.oneBusiness(id);
    }

    @Override
    public List<LinkedHashMap> getTagList() {
        return cardDao.getTagList();
    }

    @Override
    public List<LinkedHashMap> getSubServiceList() {
        return cardDao.getSubServiceList();
    }

    /**
     * 根据 code 获取 card id 值
     *
     * @param code
     * @return
     */
    public int getIdByCode(String code) {
        String openid = getWxOpenId(code);
        return getIdByOpenId(openid);
    }

    @Override
    public int getIdByOpen(String openId) {
        return getIdByOpenId(openId);
    }

    @Override
    public Map<String, Object> getOpenIdByCode(String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("openid", getWxOpenId(code));
        return map;
    }

    public String getWxOpenId(String code) {


        // 微信小程序ID
        String appid = "wx2f3e800fce3fd438";
        // 微信小程序秘钥
        String secret = "52ae70bbe182e47bbeec03d9825deb96";

        // 根据小程序穿过来的code想这个url发送请求
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        // 发送请求，返回Json字符串
        String str = WeChatUtil.httpRequest(url, "GET", null);
        // 转成Json对象 获取openid
        JSONObject jsonObject = JSONObject.parseObject(str);

        // 然后书写自己的处理逻辑即可
        String openId = jsonObject.get("openid").toString();
        if (StringUtil.isEmpty(openId)) {
            throw new GlobalProcessException("openid为空");
        }

//        String result = sendGet("https://api.weixin.qq.com/sns/jscode2session",
//                "appid=wx2f3e800fce3fd438" +//小程序APPID
//                        "&secret=52ae70bbe182e47bbeec03d9825deb96" + //小程序秘钥
//                        "&js_code=" + code + //前端传来的code
//                        "&grant_type=authorization_code");
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        if (jsonObject.containsKey("errcode")) {
//            throw new GlobalProcessException(jsonObject.get("errcode").toString());
//        }
//        String openId = jsonObject.get("openid").toString();
//        if (StringUtils.isNullOrEmpty(openId)) {
//            throw new GlobalProcessException("openid为空");
//        }
        return openId;
    }

    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 是否关注过
     *
     * @param userId
     * @param followerId
     * @return
     */
    public boolean hasFollowed(int userId, int followerId) {
        Map map = cardDao.hasFollowed(userId, followerId);
        return (map != null && map.size() > 0);
    }

    /**
     * 关注操作
     *
     * @param userId
     * @param followerId
     * @return
     */
    @Override
    public int addFollower(int userId, int followerId) {
        Map<String, Object> userMap = cardDao.getCardInfoById(userId + "");
        Map<String, Object> followerMap = cardDao.getCardInfoById(followerId + "");
        Map<String, Object> messageParamsMap = new HashMap<>();
        //1:互动消息
        messageParamsMap.put("message_type", 1);
        //1:关注行为
        messageParamsMap.put("message_behavior", 1);
        messageParamsMap.put("send_person_card", userId);
        messageParamsMap.put("send_person_name", userMap.get("realname"));
        messageParamsMap.put("receive_person_card", followerId);
        messageParamsMap.put("receive_person_name", followerMap.get("realname"));
        messageParamsMap.put("create_time", DateUtils.getAccurateDate());
        //添加关注行为消息
        cardDao.addFollowerMessage(messageParamsMap);

        //推送订阅消息
        String responseMessage = WeChatUtil.pushFollowMessageToOneUser(followerMap.get("openid").toString(), userMap.get("realname").toString());
        //更新订阅消息授权状态
        cardDao.updateUserAuthStatusByCard(userId);
        List<LinkedHashMap> followerAuthList = cardDao.getFollowerAuthListByCard(followerId);
        if (followerAuthList.size() == 0) {
            Map paramsMap = new HashMap();
            paramsMap.put("card", followerId);
            paramsMap.put("type", 1);
            paramsMap.put("authStatus", 0);
            paramsMap.put("createTime", DateUtils.getAccurateDate());
            cooperateDao.addAuthAcceptRecord(paramsMap);
        } else {
            cardDao.updateFollowerAuthStatusByCard(followerId);
        }

        return !hasFollowed(userId, followerId) ?
                cardDao.addFollower(userId, followerId) : 1;
    }

    /**
     * 取消关注
     *
     * @param userId
     * @param followerId
     * @return
     */
    public int deleteFollower(int userId, int followerId) {
        return cardDao.deleteFollower(userId, followerId);
    }

    /**
     * 获取所有服务的 标签 tag
     *
     * @return
     */
    public List<LinkedHashMap> getAllService() {
        return cardDao.getAllService();
    }

    private int loadUserId(Map map) {
        // 构建查询条件
        // 小程序和微信公众号共用一个表, openid 是两个
        //Map cardInfo =cardDao.getCardInfoByOpenId(openid);
        logger.info("检查用户是否存在");
        String phone = map.get("phone").toString();
        String realname = map.get("realname").toString();
        List<Map> cardInfoMap = cardDao.getCardInfoByPhone(phone, realname);
        if (cardInfoMap != null && cardInfoMap.size() > 0) {
            if (cardInfoMap.size() > 1) throw new GlobalProcessException("相同的用户名和手机号码已存在");
            logger.info("用户已存在, {}", cardInfoMap.toString());
            return Integer.parseInt(cardInfoMap.get(0).get("id").toString());
        }
        return 0;
    }

    private Map makeUserMap(Map map) {
        // 获取 openid
//        String code = map.get("code").toString();
        String openid = map.get("openId").toString();
        map.put("openid", openid);
        // 更新当前时间
        map.put("datetime", Utils.getCurrentDate());
        // 保护用户如果没有输入性别
        if (map.get("gender") == null) {
            map.put("gender", "0");
        }
        // 保护用户如果没有城市,给默认值
        if (map.get("city") == null) {
            map.put("city", 220);
        }
        return map;
    }

    private Map makeTagMap(Map map) {
        if (map.get("tag") == null) throw new GlobalProcessException("所属亲子行业为空");
        // 处理 标签,需要拆分成 3个标签
        try {
            String tag = map.get("tag").toString();
            String[] strTemp = tag.split("");
            String tagTemp = "";
            for (String s : strTemp) {
                if (!("[".equals(s) || "]".equals(s) || " ".equals(s))) {
                    tagTemp += s;
                }
            }

            String[] tagList = tagTemp.split(",");
            int id = 0;
            for (int index = 0; index < tagList.length; index++) {
                map.put("tag" + (index + 1), tagList[index]);
            }
            return map;
        } catch (Exception e) {
            throw new GlobalProcessException(e.toString());
        }
    }

    private void userPushMessage(Map map) {
        int inviteId = Integer.parseInt(map.get("invite").toString());
        if (inviteId != -1) scoreService.addScore(inviteId, ScoreType.Invite);
        try {
            logger.info("注册成功，准备给所有用户推送消息");
            pushService.pushCard2AllUser(map);
        } catch (Exception e) {
            logger.error("推送新注册用户，给所有用户失败", e);
        }
    }

    /**
     * 更新用户信息
     *
     * @param map
     * @return
     */
    @Override
    @Transactional
    public int setUser(Map map) {
        int id = loadUserId(map);
        Map userMap = makeUserMap(map);
        Map tagMap = makeTagMap(map);
        if (id == 0) {
            cardDao.addCardInfo(userMap);
            logger.info("新增用户成功, {}", userMap.toString());
            tagMap.put("card_id", Integer.parseInt(userMap.get("id").toString()));
            cardDao.addCardTag(tagMap);
            logger.info("新增用户标签成功, {}", tagMap.toString());
            userPushMessage(userMap);
        } else {
            userMap.put("id", id);
            tagMap.put("card_id", id);
            cardDao.updateCardInfo(userMap);
            logger.info("修改用户成功, {}", userMap.toString());
            cardDao.updateCardTag(tagMap);
            logger.info("修改用户标签成功, {}", tagMap.toString());
        }
        return id;
    }

    @Override
    public int updateHeadingImgUrl(Map map) {
        return cardDao.updateHeadingImgUrl(map);
    }

    @Override
    public int addSeeCardRecord(Map map) {
        //查看人cardId
        int seeCardId = Integer.parseInt(map.get("seeCardId").toString());
        //被查看人cardId
        int seenCardId = Integer.parseInt(map.get("seenCardId").toString());

        Map<String, Object> resultMap = cardDao.getSeeCardNameAndLeaguerById(seeCardId);
        map.put("seenCardName", cardDao.getCardNameById(seenCardId));
        map.put("seeCardName", resultMap.get("realname"));
        map.put("leaguer", resultMap.get("leaguer"));
        map.put("createTime", DateUtils.getAccurateDate());
        return cardDao.addSeeCardRecord(map);
    }

    /**
     * 根据 用户id获取 标签
     *
     * @param id
     * @return
     */
    public Map getCardTagById(String id) {
        return cardDao.getCardTagById(id);
    }

    /**
     * 获取某人 关注的商户列表, 同时需要提取我的关注信息
     *
     * @param current_id 某商户
     * @param my_id      我
     * @return
     */
    public List<LinkedHashMap> getFollowerById(String current_id, String my_id) {
        return cardDao.getFollowerById(current_id, my_id);
    }

    /**
     * 获取关注 某人的粉丝, 同时需要提取我的关注信息
     *
     * @param current_id 某商户
     * @param my_id      我
     * @return
     */
    public List<LinkedHashMap> getFansById(String current_id, String my_id) {
        return cardDao.getFansById(current_id, my_id);
    }

    /**
     * 批量关注商户
     *
     * @param userId
     * @param idList
     * @return
     */
    public int batchAddFollower(int userId, String[] idList) {
        for (String id : idList)
            addFollower(userId, Integer.parseInt(id));
        return 1;
    }

    /**
     * 签到积分
     *
     * @param cardId
     * @return
     */
    public int sign(int cardId) {
        scoreService.addScore(cardId, ScoreType.Sign);
        cardDao.refreshCardDate(cardId);
        return 1;
    }

    public int hasScoreHistory(int cardId) {
        List<Map> list = cardDao.hasScoreHistory(cardId, ScoreType.Sign.getType());
        return list != null && list.size() > 0 ? 1 : 0;
    }

    public List<LinkedHashMap> listCitys() {
        return cardDao.listCitys();
    }

    public WxOneCity oneCity(int id) {
        return cardDao.oneCity(id);
    }

    public List<LinkedHashMap> searchCity(String cityName) {
        return cardDao.searchCity(cityName);
    }

    @Override
    public Map<String, Object> getUnreadNum() {
        Map<String, Object> map = new HashMap<>();
        map.put("enterpriseOrderUnreadNum", cardDao.getEnterpriseOrderUnreadNum());
        map.put("baseReserveUnreadNum", cardDao.getBaseReserveUnreadNum());
        return map;
    }

    @Override
    public List<String> getTagNameList(Map map) {
        List<String> list = new ArrayList<>();
        int tag1 = Integer.parseInt(map.get("tag1").toString());
        list.add(cardDao.getTagName(tag1));
        if (map.get("tag2") != null) {
            int tag2 = Integer.parseInt(map.get("tag2").toString());
            list.add(cardDao.getTagName(tag2));
        }
        if (map.get("tag3") != null) {
            int tag3 = Integer.parseInt(map.get("tag3").toString());
            list.add(cardDao.getTagName(tag3));
        }
        return list;
    }
}
