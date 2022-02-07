package com.qinzi123.service.impl;

import com.qinzi123.dao.StartUpDao;
import com.qinzi123.service.StartUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @title: StartUpServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/1/6 0006 14:14
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class StartUpServiceImpl extends AbstractWechatMiniProgramService implements StartUpService {

    @Autowired
    private StartUpDao startUpDao;

    @Override
    public int addVisitCount() {
        return startUpDao.addVisitCount();
    }
}
