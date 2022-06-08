package com.qinzi123.service.impl;

import com.qinzi123.dao.CertificateDao;
import com.qinzi123.dao.IndexDao;
import com.qinzi123.service.CertificateService;
import com.qinzi123.service.IndexService;
import com.qinzi123.util.DateUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: CertificateServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/3/16 0010 10:03
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class CertificateServiceImpl extends AbstractWechatMiniProgramService implements CertificateService {

    @Autowired
    private CertificateDao certificateDao;

    @Override
    public List<LinkedHashMap> getCertificateList(String userId, int start, int num) {
        return certificateDao.getCertificateList(userId, start, num);
    }

    @Override
    public Map<String, Object> getProductInfoByProductId(Map map) {
        return certificateDao.getProductInfoByProductId(map);
    }

    @Override
    public int getChildrenNumById(Map map) {
        return certificateDao.getChildrenNumById(map);
    }

    @Override
    public Map<String, Object> getCertificateInfoById(int id) {
        return certificateDao.getCertificateInfoById(id);
    }

    @Override
    public String getActivityNameById(Map map) {
        return certificateDao.getActivityNameById(map);
    }

    @Override
    public String getInstitutionNameById(Map map) {
        return certificateDao.getInstitutionNameById(map);
    }

}
