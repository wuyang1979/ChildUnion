package com.qinzi123.service.impl;

import com.qinzi123.dao.EnterpriseDao;
import com.qinzi123.service.EnterpriseService;
import com.qinzi123.util.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: EnterpriseServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/11/25 0025 15:10
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class EnterpriseServiceImpl extends AbstractWechatMiniProgramService implements EnterpriseService {

    @Resource
    EnterpriseDao enterpriseDao;

    @Override
    public List<LinkedHashMap> listEnterprise(int start, int num) {
        return enterpriseDao.listEnterprise(start, num);
    }

    @Override
    public List<LinkedHashMap> screenList(String type, int start, int num) {
        return enterpriseDao.screenList(type, start, num);
    }

    @Override
    public List<LinkedHashMap> pictureList(Map map) {
        return enterpriseDao.pictureList(map);
    }

    @Override
    public List<LinkedHashMap> getAllEnterpriseCommentByEnterpriseId(int enterpriseId) {
        return enterpriseDao.getAllEnterpriseCommentByEnterpriseId(enterpriseId);
    }

    @Override
    public Map<String, Object> getConsultingAndCommentCount(int enterpriseId) {
        int consultingCount = enterpriseDao.getConsultingCount(enterpriseId);
        int commentCount = enterpriseDao.getCommentCount(enterpriseId);
        Map<String, Object> map = new HashMap<>();
        map.put("consultingCount", consultingCount);
        map.put("commentCount", commentCount);
        return map;
    }

    @Override
    public List<LinkedHashMap> getAllEnterpriseConsultingByEnterpriseId(int enterpriseId) {
        return enterpriseDao.getAllEnterpriseConsultingByEnterpriseId(enterpriseId);
    }

    @Override
    public int addComment(Map map) {
        map.put("createTime", DateUtils.getDate());
        return enterpriseDao.addComment(map);
    }

    @Override
    public int addEnterpriseConsulting(Map map) {
        map.put("createTime", DateUtils.getAccurateDate());
        map.put("title", enterpriseDao.getTitleById(Integer.parseInt(map.get("enterpriseId").toString())));
        return enterpriseDao.addEnterpriseConsulting(map);
    }

    @Override
    public List<LinkedHashMap> getEnterpriseOrderList() {
        return enterpriseDao.getEnterpriseOrderList();
    }

    @Override
    public int readOrder(int id) {
        return enterpriseDao.readOrder(id);
    }
}
