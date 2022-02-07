package com.qinzi123.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: KnowledgeService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/11/17 0017 14:10
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface KnowledgeService {

    List<LinkedHashMap> getKnowledgeList();

    int updateReadCount(Map map);

    int updateBuyCount(Map map);

    int hasBoughtKnowledge(Map map);

    Map<String, Object> isLeaguer(Map map);
}
