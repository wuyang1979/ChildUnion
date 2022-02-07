package com.qinzi123.dao;

import com.qinzi123.dto.CardInfoSend;
import com.qinzi123.dto.CardMessageSend;
import com.qinzi123.dto.WxSmallFormId;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: PushDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface PushDao {

    int addMessageSend(CardMessageSend cardMessageSend);

    int addFormId(WxSmallFormId wxSmallFormId);

    int updateFormId(WxSmallFormId wxSmallFormId);

    List<WxSmallFormId> getCanUseSmallFormId(@Param("cardId") int cardId);

    List<Map> getEveryUserCanUseSmallFormId();

    List<String> getFansUser2Push(@Param("followerId") int followerId);

    int addCardSend(CardInfoSend cardInfoSend);

    List<LinkedHashMap> getAllAuthedOpenIdList();

    int updateCardMessageAuthStatus();
}
