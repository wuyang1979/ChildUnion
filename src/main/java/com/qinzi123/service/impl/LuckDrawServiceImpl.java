package com.qinzi123.service.impl;

import com.qinzi123.dao.LuckDrawDao;
import com.qinzi123.service.LuckDrawService;
import com.qinzi123.util.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: LuckDrawServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/12/24 0024 16:39
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class LuckDrawServiceImpl extends AbstractWechatMiniProgramService implements LuckDrawService {

    @Resource
    private LuckDrawDao luckDrawDao;

    @Override
    public int joinLuckDraw(Map map) {
        try {
            Map<String, Object> paramsMap = luckDrawDao.getJoinerInfoById(Integer.parseInt(map.get("card").toString()));
            paramsMap.put("createTime", DateUtils.getAccurateDate());
            paramsMap.put("type", map.get("type"));
            paramsMap.put("cardId", map.get("card"));
            return luckDrawDao.joinLuckDraw(paramsMap);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public int joinOutsideLuckDraw(Map map) {
        try {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("createTime", DateUtils.getAccurateDate());
            paramsMap.put("name", map.get("name"));
            paramsMap.put("headimgurl", map.get("headimgurl"));
            paramsMap.put("openId", map.get("openId"));
            return luckDrawDao.joinOutsideLuckDraw(paramsMap);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public List<LinkedHashMap> getJoinInfoByCardAndType(Map map) {
        try {
            return luckDrawDao.getJoinInfoByCardAndType(map);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<LinkedHashMap> getOutsideJoinInfoByOpenId(Map map) {
        try {
            return luckDrawDao.getOutsideJoinInfoByOpenId(map);
        } catch (Exception e) {
            return null;
        }
    }
}
