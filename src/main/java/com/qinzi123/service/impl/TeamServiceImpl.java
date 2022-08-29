package com.qinzi123.service.impl;

import com.qinzi123.dao.CustomerDao;
import com.qinzi123.dao.ShopDao;
import com.qinzi123.dao.TeamDao;
import com.qinzi123.dao.UserOrderDao;
import com.qinzi123.dto.TemplateData;
import com.qinzi123.dto.WxMssVo;
import com.qinzi123.service.TeamService;
import com.qinzi123.util.DateUtils;
import com.qinzi123.util.Utils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

/**
 * @title: CustomerServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/8/03 0030 44:45
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class TeamServiceImpl extends AbstractWechatMiniProgramService implements TeamService {

    @Resource
    private TeamDao teamDao;

    @Resource
    private CustomerDao customerDao;

    @Resource
    private UserOrderDao userOrderDao;

    @Resource
    private ShopDao shopDao;

    @Override
    public List<LinkedHashMap> getAllTeamMemberList(Map map) {
        String userId = map.get("userId").toString();
        int shopId = Integer.parseInt(map.get("shopId").toString());
        List<LinkedHashMap> allTeamMemberList;
        if (StringUtils.isEmpty(userId)) {
            //userId为空，说明该用户已开通过小店
            map.put("firstCommander", shopId);
            allTeamMemberList = teamDao.getAllTeamMemberList(map);
        } else {
            //userId不为空，说明该用户未开通小店但已成为分销合伙人
            map.put("firstCommander", userId);
            allTeamMemberList = teamDao.getAllTeamMemberList(map);
        }
        allTeamMemberList.forEach(item -> {
            item.put("nick_name", new String(Base64.getDecoder().decode(item.get("nick_name").toString())));
        });
        return allTeamMemberList;
    }

    @Override
    public List<LinkedHashMap> getSearchTeamMemberList(Map map) {
        List<LinkedHashMap> allTeamMemberList = teamDao.getSearchTeamMemberList(map);
        allTeamMemberList.forEach(item -> {
            item.put("nick_name", new String(Base64.getDecoder().decode(item.get("nick_name").toString())));
        });
        return allTeamMemberList;
    }

    @Override
    public List<LinkedHashMap> getDistributionOrderListById(Map map) {
        List<LinkedHashMap> orderList = teamDao.getDistributionOrderListById(map);
        orderList.forEach(item -> {
            int standardId = Integer.parseInt(item.get("standard_id").toString());
            int productType = Integer.parseInt(item.get("product_type").toString());
            if (productType == 0) {
                //产品订单
                List<LinkedHashMap> standardList = customerDao.getProductStandardNameById(standardId);
                item.put("standardName", standardList.get(0).get("name"));
            } else if (productType == 1) {
                //活动订单
                List<LinkedHashMap> standardList = customerDao.getActivityStandardNameById(standardId);
                item.put("standardName", standardList.get(0).get("adults_num") + "大" + standardList.get(0).get("children_num") + "小");
            }
        });
        return orderList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int becomeTeamMemberByInvited(Map map) throws Exception {
        String inviterUserId = map.get("inviterUserId").toString();
        String inviteeUserId = map.get("inviteeUserId").toString();
        if (StringUtils.isEmpty(inviterUserId)) {
            throw new Exception("邀请人userId为空");
        }
        if (StringUtils.isEmpty(inviteeUserId)) {
            throw new Exception("受邀人userId为空");
        }

        //添加分销合伙人记录，已校验过受邀人未成为分销合伙人
        Map<String, Object> distributionPartnerParams = new HashedMap();
        distributionPartnerParams.put("userId", inviteeUserId);
        String createTime = DateUtils.getAccurateDate();
        distributionPartnerParams.put("createTime", createTime);
        distributionPartnerParams.put("payTime", createTime);
        distributionPartnerParams.put("orderNo", Utils.getCurrentDateNoFlag());
        indexDao.addFreeDistributionPartnerOrder(distributionPartnerParams);

        //添加团队记录
        //查询受邀人是否存在级联关系
        List<LinkedHashMap> cascadeList = userOrderDao.getCascadeListByUserId(inviteeUserId);
        //根据邀请人userId查询是否开通过小店
        List<LinkedHashMap> shopList = shopDao.getShopListByUserId(inviterUserId);
        Map<String, Object> teamParams = new HashedMap();
        teamParams.put("user_id", inviteeUserId);
        teamParams.put("create_time", DateUtils.getAccurateDate());
        if (cascadeList.size() == 0) {
            //受邀人不存在级联关系，上级为邀请人
            if (shopList.size() == 0) {
                //邀请人为C端分销合伙人
                teamParams.put("first_commander_type", 2);
                teamParams.put("first_commander", inviterUserId);
            } else {
                //邀请人为B端开通过小店的商户
                teamParams.put("first_commander_type", 1);
                teamParams.put("first_commander", shopList.get(0).get("id"));
            }
        } else {
            //受邀人存在级联关系，上级为客户级联归属的上级
            int superiorType = Integer.parseInt(cascadeList.get(0).get("superior_type").toString());
            String superiorId = cascadeList.get(0).get("superior_id").toString();
            teamParams.put("first_commander", superiorId);
            if (superiorType == 0) {
                teamParams.put("first_commander_type", 1);
            } else if (superiorType == 1) {
                teamParams.put("first_commander_type", 2);
            }
        }
        //添加团队成员信息
        int rows = teamDao.addCommanderInfoRecord(teamParams);

        //订阅消息推送给团长
        String inviterOpenId = teamDao.getOpenIdByUserId(inviterUserId);
        String inviteeNickName = new String(Base64.getDecoder().decode(teamDao.getNickNameByUserId(inviteeUserId)));
        pushWxNewMembersJoinTemplateMessage(inviterOpenId, inviteeNickName);

        //修改授权记录表
        teamDao.updateNewMembersJoinAuthRecordByUserId(inviterUserId);

        return rows;
    }

    /**
     * @return java.lang.String
     * @Author: jie.yuan
     * @Description: 微信推送c端发展合伙人订阅消息
     * @Date: 2022/08/11 0015 11:45
     * @Param [openId, nickName]
     **/
    public String pushWxNewMembersJoinTemplateMessage(String openId, String nickName) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + Utils.getWxChengZhangGoAccessToken();
        //拼接推送的模版
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setTouser(openId);
        //订阅消息模板id
        wxMssVo.setTemplate_id("-06RHUlbrgdLIA_Ii7g4iNHSkXtZJ_j3g7Iq6i9N4wk");
        wxMssVo.setPage("pages/my/index");

        Map<String, TemplateData> m = new HashMap<>();
        m.put("phrase1", new TemplateData("扫码加入"));
        String thing2 = nickName + "刚刚加入您的团队";
        m.put("thing2", new TemplateData(thing2));
        wxMssVo.setData(m);
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(url, wxMssVo, String.class);
        return responseEntity.getBody();
    }

}
