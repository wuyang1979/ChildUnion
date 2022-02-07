package com.qinzi123.service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @title: BannerService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface BannerService {

    List<LinkedHashMap> listAllBanners();

    List<LinkedHashMap> oneBanner(int id);
}
