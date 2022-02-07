package com.qinzi123.dao;

import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: LuckDrawDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/12/24 0024 16:40
 * 注意:该代码仅为中企云服科技内部传阅，严禁其他商业用途！
 */
public interface LuckDrawDao {

    Map<String, Object> getJoinerInfoById(@Param("card") int card);

    int joinLuckDraw(Map<String, Object> map);

    int joinOutsideLuckDraw(Map<String, Object> map);

    List<LinkedHashMap> getJoinInfoByCardAndType(Map map);

    List<LinkedHashMap> getOutsideJoinInfoByOpenId(Map map);
}