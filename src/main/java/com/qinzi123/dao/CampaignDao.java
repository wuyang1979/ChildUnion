package com.qinzi123.dao;

import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: CampaignDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface CampaignDao {

    List<LinkedHashMap> listCampaign(@Param("start") int start,
                                     @Param("num") int num);

    List<LinkedHashMap> oneCampaign(@Param("id") int id);

    List<LinkedHashMap> listOrder(@Param("cardId") int cardId);

    List<LinkedHashMap> listAllOrder(@Param("cardId") int cardId);

    List<LinkedHashMap> oneOrder(@Param("id") int id);

    List<LinkedHashMap> getOneOrder(@Param("id") int id);

    List<LinkedHashMap> getOneOrder2(@Param("id") int id);

    List<LinkedHashMap> getOneKnowledgeOrder(@Param("id") int id);

    List<LinkedHashMap> listRechargeOrder(@Param("cardId") int cardId,
                                          @Param("start") int start,
                                          @Param("num") int num);

    List<LinkedHashMap> oneRechargeOrder(@Param("id") int id);

    int addOrder(Map map);

    int addOrderItem(Map map);

    int addPayment(Map map);

    int addClientEndPayment(Map map);

    int addPaymentScore(Map map);

    int updateOrder(Map map);

    int updateMessageRead(@Param("id") int id);

    int updateMessageLike(@Param("id") int id);

    List<LinkedHashMap> pictureList(int productId);
}
