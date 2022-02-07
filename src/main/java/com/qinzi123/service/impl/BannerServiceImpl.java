package com.qinzi123.service.impl;

import com.qinzi123.dao.BannerDao;
import com.qinzi123.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @title: BannerServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class BannerServiceImpl extends AbstractWechatMiniProgramService implements BannerService {


    @Autowired
    BannerDao bannerDao;

    public List<LinkedHashMap> listAllBanners() {
        return bannerDao.listAllBanners();
    }

    public List<LinkedHashMap> oneBanner(int id) {
        return bannerDao.oneBanner(id);
    }
}
