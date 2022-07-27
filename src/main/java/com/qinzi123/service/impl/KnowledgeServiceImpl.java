package com.qinzi123.service.impl;

import com.qinzi123.dao.BaseDao;
import com.qinzi123.dao.KnowledgeDao;
import com.qinzi123.service.KnowledgeService;
import com.qinzi123.util.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: KnowledgeServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/11/17 0017 14:10
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
@Service
public class KnowledgeServiceImpl extends AbstractWechatMiniProgramService implements KnowledgeService {

    @Resource
    private KnowledgeDao knowledgeDao;
    @Resource
    private BaseDao baseDao;

    @Override
    public List<LinkedHashMap> getKnowledgeList() {
        return knowledgeDao.getKnowledgeList();
    }

    @Override
    public int updateReadCount(Map map) {
        return knowledgeDao.updateReadCount(map);
    }

    @Override
    public int hasBoughtKnowledge(Map map) {
        return knowledgeDao.hasBoughtKnowledge(map);
    }

    @Override
    public int updateBuyCount(Map map) {
        map.put("createTime", DateUtils.getDate());
        map.put("realname", baseDao.getCardName(map.get("card").toString()));
        knowledgeDao.addKnowledgeOrder(map);
        return knowledgeDao.updateBuyCount(map);
    }

    @Override
    public Map<String, Object> isLeaguer(Map map) {
        return knowledgeDao.isLeaguer(map);
    }
}
