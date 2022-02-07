package com.qinzi123.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: LuckDrawService
 * @package: com.qinzi123.service
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/12/24 0024 16:38
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface LuckDrawService {

    int joinLuckDraw(Map map);

    int joinOutsideLuckDraw(Map map);

    List<LinkedHashMap> getJoinInfoByCardAndType(Map map);

    List<LinkedHashMap> getOutsideJoinInfoByOpenId(Map map);
}
