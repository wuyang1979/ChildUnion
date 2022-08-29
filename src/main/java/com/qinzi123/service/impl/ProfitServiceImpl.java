package com.qinzi123.service.impl;

import com.qinzi123.constant.DistributionConstant;
import com.qinzi123.dao.ProfitDao;
import com.qinzi123.dao.TeamDao;
import com.qinzi123.service.ProfitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @title: ProfitServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: childunion
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/6/10 0010 10:18
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */

@Service
public class ProfitServiceImpl extends AbstractWechatMiniProgramService implements ProfitService {

    @Resource
    private ProfitDao profitDao;

    @Resource
    private TeamDao teamDao;

    @Override
    public Map getDistributionPartnerProfitInfo(Map map) {
        return profitDao.getDistributionPartnerProfitInfo(map);
    }

    @Override
    public List<LinkedHashMap> loadSelfProfitList(Map map) {
        DecimalFormat df = new DecimalFormat("#0.00");
        String userId = map.get("userId").toString();
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        List<LinkedHashMap> orderList = profitDao.getSelfProfitListByUserId(userId);
        List<LinkedHashMap> selfProfitCustomerList = profitDao.getSelfProfitCustomerOpenIdListByUserId(userId, start, num);
        List<LinkedHashMap> selfProfitList = new ArrayList<>();
        selfProfitCustomerList.forEach(item -> {
            LinkedHashMap<String, Object> selfProfitMap = new LinkedHashMap();
            //客户openId唯一处理
            String customerOpenId = item.get("open_id").toString();
            Map<String, Object> customerInfo = profitDao.getCustomerInfoByOpenId(customerOpenId);
            Double customerTotalPay = 0.00;
            Double customerBringProfitToSelfTotal = 0.00;
            for (int i = 0; i < orderList.size(); i++) {
                if (customerOpenId.equals(orderList.get(i).get("open_id").toString())) {
                    customerTotalPay += Double.parseDouble(orderList.get(i).get("total").toString());
                    customerBringProfitToSelfTotal += Double.parseDouble(orderList.get(i).get("retail_commission").toString()) * DistributionConstant.primaryDistributionProportion;
                }
            }

            selfProfitMap.put("nickName", new String(Base64.getDecoder().decode(customerInfo.get("nick_name").toString())));
            selfProfitMap.put("headImgUrl", customerInfo.get("head_img_url"));
            selfProfitMap.put("customerTotalPay", df.format(customerTotalPay));
            selfProfitMap.put("customerBringProfitToSelfTotal", df.format(customerBringProfitToSelfTotal));
            selfProfitList.add(selfProfitMap);
        });
        return selfProfitList;
    }

    @Override
    public List<LinkedHashMap> loadTeamProfitList(Map map) {
        DecimalFormat df = new DecimalFormat("#0.00");
        String userId = map.get("userId").toString();
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        //获取所有团队下级成员
        List<String> memberList = profitDao.getMemberListByUserId(userId);
        List<String> memberOpenIdList = new ArrayList<>();
        List<LinkedHashMap> orderList = new ArrayList<>();
        if (memberList.size() != 0) {
            memberList.forEach(item -> {
                String memberUserId = item;
                List<LinkedHashMap> memberDistributionOrderList = profitDao.getSelfProfitListByUserId(memberUserId);
                orderList.addAll(memberDistributionOrderList);
                memberOpenIdList.add(teamDao.getOpenIdByUserId(memberUserId));
            });
        }
        List<LinkedHashMap> selfProfitList = new ArrayList<>();
        if (memberOpenIdList.size() != 0) {
            List<LinkedHashMap> memberProfitCustomerList = profitDao.getMemberProfitCustomerOpenIdListByUserId(memberOpenIdList, start, num);
            memberProfitCustomerList.forEach(item -> {
                LinkedHashMap<String, Object> selfProfitMap = new LinkedHashMap();
                //客户openId唯一处理
                String customerOpenId = item.get("open_id").toString();
                Map<String, Object> customerInfo = profitDao.getCustomerInfoByOpenId(customerOpenId);
                Double customerTotalPay = 0.00;
                Double customerBringProfitToSelfTotal = 0.00;
                for (int i = 0; i < orderList.size(); i++) {
                    if (customerOpenId.equals(orderList.get(i).get("open_id").toString())) {
                        customerTotalPay += Double.parseDouble(orderList.get(i).get("total").toString());
                        customerBringProfitToSelfTotal += Double.parseDouble(orderList.get(i).get("retail_commission").toString()) * DistributionConstant.primaryDistributionProportion;
                    }
                }

                selfProfitMap.put("nickName", new String(Base64.getDecoder().decode(customerInfo.get("nick_name").toString())));
                selfProfitMap.put("headImgUrl", customerInfo.get("head_img_url"));
                selfProfitMap.put("customerTotalPay", df.format(customerTotalPay));
                selfProfitMap.put("customerBringProfitToSelfTotal", df.format(customerBringProfitToSelfTotal));
                selfProfitList.add(selfProfitMap);
            });
        }
        return selfProfitList;
    }

}
