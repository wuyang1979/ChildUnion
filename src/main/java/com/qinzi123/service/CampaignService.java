package com.qinzi123.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: CampaignService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface CampaignService {

    List<LinkedHashMap> listCampaign(int start, int num);

    List<LinkedHashMap> oneCampaign(int id);

    List<LinkedHashMap> pictureList(int productId);

    List<LinkedHashMap> listOrder(int cardId);

    List<LinkedHashMap> listAllOrder(int cardId);

    List<LinkedHashMap> oneOrder(int id);

    List<LinkedHashMap> getOneOrder(int id);

    List<LinkedHashMap> getOneOrder(Map map);

    List<LinkedHashMap> getOneKnowledgeOrder(int id);

    List<LinkedHashMap> listRechargeOrder(int cardId, int start, int num);

    List<LinkedHashMap> oneRechargeOrder(int id);

    int addOrder(Map map);

    int addKnowledgeOrder(Map map);

    int addcashOrder(Map map);

    int addPayOrder(Map map);

    int addLeaguePayOrder(Map map);

    int updateMessageRead(int id);

    int updateMessageLike(int id);

/*	int addProduct(WxProduct wxProduct);
	int updateProduct(WxProduct wxProduct);
	int deleteProduct(int productId);

	int addOrder();*/

}
