package com.qinzi123.service.impl;

import com.qinzi123.dao.ProfitDao;
import com.qinzi123.service.ProfitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @title: ProfitServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: childunion
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/6/10 0010 10:18
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */

@Service
public class ProfitServiceImpl extends AbstractWechatMiniProgramService implements ProfitService {

    @Resource
    private ProfitDao profitDao;

    @Override
    public Map getDistributionPartnerProfitInfo(Map map) {
        return profitDao.getDistributionPartnerProfitInfo(map);
    }
}
