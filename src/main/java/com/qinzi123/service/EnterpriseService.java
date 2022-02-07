package com.qinzi123.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: EnterpriseService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/11/25 0025 15:10
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface EnterpriseService {

    List<LinkedHashMap> listEnterprise(int start, int num);

    List<LinkedHashMap> screenList(String type, int start, int num);

    List<LinkedHashMap> pictureList(Map map);

    List<LinkedHashMap> getAllEnterpriseCommentByEnterpriseId(int enterpriseId);

    Map<String, Object> getConsultingAndCommentCount(int enterpriseId);

    List<LinkedHashMap> getAllEnterpriseConsultingByEnterpriseId(int enterpriseId);

    int addComment(Map map);

    int addEnterpriseConsulting(Map map);

    List<LinkedHashMap> getEnterpriseOrderList();

    int readOrder(int id);
}
