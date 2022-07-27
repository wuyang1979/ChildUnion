package com.qinzi123.service.impl;

import com.qinzi123.dao.CampaignDao;
import com.qinzi123.dto.OrderType;
import com.qinzi123.exception.GlobalProcessException;
import com.qinzi123.service.CampaignService;
import com.qinzi123.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: CampaignServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class CampaignServiceImpl extends AbstractWechatMiniProgramService implements CampaignService {

    private Logger log = LoggerFactory.getLogger(CampaignServiceImpl.class);

    @Resource
    CampaignDao campaignDao;

    public List<LinkedHashMap> listCampaign(int start, int num) {
        return campaignDao.listCampaign(start, num);
    }

    @Override
    public List<LinkedHashMap> pictureList(int productId) {
        return campaignDao.pictureList(productId);
    }

    public List<LinkedHashMap> oneCampaign(int id) {
        return campaignDao.oneCampaign(id);
    }

    public List<LinkedHashMap> listOrder(int cardId) {
        return campaignDao.listOrder(cardId);
    }

    public List<LinkedHashMap> listAllOrder(int cardId) {
        return campaignDao.listAllOrder(cardId);
    }

    public List<LinkedHashMap> oneOrder(int id) {
        return campaignDao.oneOrder(id);
    }

    public List<LinkedHashMap> getOneOrder(int id) {
        return campaignDao.getOneOrder(id);
    }

    @Override
    public List<LinkedHashMap> getOneOrder(Map map) {
        String type = map.get("type").toString();
        int id = Integer.parseInt(map.get("id").toString());
        if ("1".equals(type)) {
            return campaignDao.getOneOrder2(id);
        } else {
            return campaignDao.getOneOrder(id);
        }

    }

    @Override
    public List<LinkedHashMap> getOneKnowledgeOrder(int id) {
        return campaignDao.getOneKnowledgeOrder(id);
    }

    @Override
    public List<LinkedHashMap> listRechargeOrder(int cardId, int start, int num) {
        return campaignDao.listRechargeOrder(cardId, start, num);
    }

    @Override
    public int updateMessageRead(int id) {
        return campaignDao.updateMessageRead(id);
    }

    @Override
    public int updateMessageLike(int id) {
        return campaignDao.updateMessageLike(id);
    }

    @Override
    public List<LinkedHashMap> oneRechargeOrder(int id) {
        return campaignDao.oneRechargeOrder(id);
    }

    private void isProductLeft(Map map) {
        int id = Integer.parseInt(map.get("productId").toString());
        int num = Integer.parseInt(map.get("num").toString());
        List<LinkedHashMap> productList = oneCampaign(id);
        if (productList == null || productList.size() == 0) throw new GlobalProcessException("该商品不存在");
        int stock = Integer.parseInt(productList.get(0).get("stock").toString());
        if (num > stock) throw new GlobalProcessException("该商品已售完");
    }

    private int addOrder(Map map, OrderType orderType) {
        log.info("增加订单" + map.toString());
        map.put("orderNo", Utils.getCurrentDateNoFlag());
        map.put("createTime", Utils.getCurrentDate());
        map.put("order_type", orderType.getType());

        campaignDao.addOrder(map);
        int orderId = Integer.parseInt(map.get("id").toString());
        map.put("orderId", orderId);
        campaignDao.addOrderItem(map);
        return orderId;
    }

    @Transactional
    @Override
    public int addOrder(Map map) {
        log.info("*******************增加积分订单*******************");
        isProductLeft(map);
        return addOrder(map, OrderType.SCORE);
    }

    @Transactional
    @Override
    public int addKnowledgeOrder(Map map) {
        log.info("*******************增加积分订单*******************");
        return addOrder(map, OrderType.SCORE);
    }

    @Transactional
    @Override
    public int addcashOrder(Map map) {
        log.info("*******************增加支付订单*******************");
        isProductLeft(map);
        return addOrder(map, OrderType.PAY);
    }

    @Transactional
    @Override
    public int addPayOrder(Map map) {
        log.info("*******************增加支付订单*******************");
        return addOrder(map, OrderType.PAY);
    }

    @Transactional
    @Override
    public int addLeaguePayOrder(Map map) {
        log.info("*******************增加充值会员订单*******************");
        return addOrder(map, OrderType.LEAGUE);
    }
}
