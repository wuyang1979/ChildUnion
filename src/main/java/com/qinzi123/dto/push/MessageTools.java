package com.qinzi123.dto.push;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.FollowMessage;
import com.qinzi123.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: MessageTools
 * @package: com.qinzi123.dto.push
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class MessageTools {

    public static String cardMessage2Page(CardMessage cardMessage) {
        Map<String, Object> map = new HashMap() {{
            put("id", cardMessage.getId());
            put("title", cardMessage.getTitle());
            put("message", cardMessage.getMessage());
            put("last", Utils.getDateLast(cardMessage.getLast()));
            put("read", cardMessage.getReadCount());
            put("like", cardMessage.getGiveLike());
            put("card", cardMessage.getCardId());
            put("phone", cardMessage.getCardInfo().getPhone());
            put("realname", cardMessage.getCardInfo().getRealname());
            put("job", cardMessage.getCardInfo().getJob());
            put("company", cardMessage.getCardInfo().getCompany());
            put("headimgurl", cardMessage.getCardInfo().getHeadimgurl());
            put("messageType", cardMessage.getMessageType());
            put("sourceType", cardMessage.getSourceType());
            put("sourcePath", cardMessage.getSourcePath().contains("http") ? cardMessage.getSourcePath() : "https://www.qinzi123.com/" + cardMessage.getSourcePath());
            put("shareFlag", false);
            put("messageList", new ArrayList<>());
        }};
        return Utils.fillUrlParams("pages/cooperate/oneMessage", map);
    }


    public static String followMessage2Page(FollowMessage followMessage) {
        Map<String, Object> map = new HashMap() {{
        }};
        return Utils.fillUrlParams("pages/message/list", map);
    }

}
