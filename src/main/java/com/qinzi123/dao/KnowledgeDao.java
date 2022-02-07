package com.qinzi123.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: KnowledgeDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/11/17 0017 14:12
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface KnowledgeDao {

    List<LinkedHashMap> getKnowledgeList();

    int updateReadCount(Map map);

    int hasBoughtKnowledge(Map map);

    int updateBuyCount(Map map);

    int addKnowledgeOrder(Map map);

    Map<String, Object> isLeaguer(Map map);
}
