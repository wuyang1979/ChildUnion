package com.qinzi123.service.impl;

import com.qinzi123.dao.BaseDao;
import com.qinzi123.dto.TemplateData;
import com.qinzi123.dto.WxMssVo;
import com.qinzi123.service.BaseService;
import com.qinzi123.util.DateUtils;
import com.qinzi123.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @title: BaseServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class BaseServiceImpl extends AbstractWechatMiniProgramService implements BaseService {

    @Resource
    BaseDao baseDao;

    private String yjOpenId = "otofq0LHoEZJ_loNGbZL2fu8dN4c";

    private String WyOpenId = "otofq0PJi9AZ7P0D-szJ4OB_DpSI";

    private Logger logger = LoggerFactory.getLogger(CooperateWeixinServiceImpl.class);

    @Override
    public List<LinkedHashMap> listBase(int start, int num) {
        return baseDao.listBase(start, num);
    }

    @Override
    public List<LinkedHashMap> getBaseList(Map map) {
        List<LinkedHashMap> list = baseDao.getBaseList(map);
        list.forEach(item -> {
            List<String> topicTypeList = this.getTopicType(item.get("topic_type_id").toString());
            String topicTypeName;
            if (topicTypeList.size() > 1) {
                topicTypeName = topicTypeList.stream().filter(Objects::nonNull).collect(Collectors.joining("/"));
            } else {
                topicTypeName = topicTypeList.get(0);
            }
            item.put("topicTypeName", topicTypeName);
        });
        return list;
    }

    @Override
    public List<LinkedHashMap> listSearchBase(Map<String, Object> map) {
        return baseDao.listSearchBase(map);
    }

    @Override
    public List<LinkedHashMap> screenBaseList(int districtCode, int typeCode, int start, int num) {
        if (0 != districtCode && 0 != typeCode) {
            return baseDao.screenBaseList(districtCode, typeCode, start, num);
        } else if (0 != districtCode && 0 == typeCode) {
            return baseDao.screenBaseListByDistrictCode(districtCode, start, num);
        } else if (0 == districtCode && 0 != typeCode) {
            return baseDao.screenBaseListByTypeCode(typeCode, start, num);
        } else {
            return baseDao.listBase(start, num);
        }
    }

    @Override
    public List<String> getTopicType(String topicTypeId) {
        List<String> topicTypeIdList = new ArrayList<>();
        if (topicTypeId.contains("/")) {
            String[] topicTypeIdArr = topicTypeId.split("/");
            for (String item : topicTypeIdArr) {
                String topicTypeStr = baseDao.getTopicType(item);
                topicTypeIdList.add(topicTypeStr);
            }
        } else {
            topicTypeIdList.add(baseDao.getTopicType(topicTypeId));
        }
        return topicTypeIdList;
    }

    @Override
    public String getCardName(String cardId) {
        return baseDao.getCardName(cardId);
    }

    @Override
    public String getConsultNumber(String id) {
        return baseDao.getConsultNumber(id);
    }

    @Override
    public String getBaseLevel(String level) {
        return baseDao.getBaseLevel(level);
    }

    @Override
    public String getDistrictName(String district) {
        return baseDao.getDistrictName(district);
    }

    @Override
    public Map<String, Object> baseReserve(Map map) {
        Map<String, Object> resultMap = new HashMap<>();
        Map cardMap = baseDao.getCardInfo(map.get("cardId").toString());
        boolean authClick = (boolean) map.get("authClick");
        String cardName = cardMap.get("realname").toString();
        String baseName = baseDao.getActivityBaseName(map.get("baseId").toString());
        String openId;

        map.put("company", cardMap.get("company"));
        map.put("cardName", cardName);
        map.put("phone", cardMap.get("phone").toString());
        map.put("activityBaseName", baseName);
        if (authClick) {
            openId = map.get("openId").toString();
            String msg = pushWxConsultingTemplateMessage(openId, cardName, baseName);
            resultMap.put("msg", msg);
        }
        int rows = baseDao.baseReserve(map);
        resultMap.put("rows", rows);
        return resultMap;
    }

    /**
     * @return java.lang.String
     * @Author: jie.yuan
     * @Description: 微信推送咨询订阅消息
     * @Date: 2021/11/15 0015 16:35
     * @Param [openId]
     **/
    public String pushWxConsultingTemplateMessage(String openId, String cardName, String baseName) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + Utils.getWxAccessToken();
        //拼接推送的模版
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setTouser(openId);
        //订阅消息模板id
        wxMssVo.setTemplate_id("R2gfeze2ZLxJQIvSIjDwPTvQX6w-reWfQE32-ibFAlo");
        wxMssVo.setPage("pages/cooperate/list");

        Map<String, TemplateData> m = new HashMap<>(3);
        m.put("thing4", new TemplateData(cardName));
        m.put("thing3", new TemplateData(baseName));
        m.put("thing2", new TemplateData("咨询基地"));
        wxMssVo.setData(m);
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(url, wxMssVo, String.class);
        return responseEntity.getBody();
    }

//    public String getWxAccessToken() {
//        RestTemplate restTemplate = new RestTemplate();
//        Map<String, String> params = new HashMap<>();
//        params.put("APPID", "wx2f3e800fce3fd438");
//        params.put("APPSECRET", "52ae70bbe182e47bbeec03d9825deb96");
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
//                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APPID}&secret={APPSECRET}", String.class, params);
//        String body = responseEntity.getBody();
//        JSONObject object = JSON.parseObject(body);
//        String Access_Token = object.getString("access_token");
//        String expires_in = object.getString("expires_in");
//        System.out.println("有效时长expires_in：" + expires_in);
//        return Access_Token;
//    }

    @Override
    public List<LinkedHashMap> pictureList(int base) {
        return baseDao.pictureList(base);
    }

    @Override
    public List<LinkedHashMap> getAllConsultingeListByBaseId(int base) {
        return baseDao.getAllConsultingeListByBaseId(base);
    }

    @Override
    public List<LinkedHashMap> getBaseTopicTypeList() {
        List<LinkedHashMap> list = baseDao.getBaseTopicTypeList();
        list.forEach(item -> {
            item.put("isSelected", false);
        });
        return list;
    }

    @Override
    public int addBaseEntity(Map<String, Object> map) {
        map.put("createTime", DateUtils.getDate());
        map.put("leaguetype", 0);
        return baseDao.addBaseEntity(map);
    }

    @Override
    public int updateBaseEntity(Map<String, Object> map) {
        return baseDao.updateBaseEntity(map);
    }

    @Override
    public List<LinkedHashMap> getBaseReserveList() {
        return baseDao.getBaseReserveList();
    }

    @Override
    public List<LinkedHashMap> allBaseMapInfo() {
        return baseDao.allBaseMapInfo();
    }

    @Override
    public int readOrder(int id) {
        return baseDao.readOrder(id);
    }
}
