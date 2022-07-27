package com.qinzi123.service.impl;

import com.qinzi123.dao.UserInfoDao;
import com.qinzi123.service.UserInfoService;
import com.qinzi123.util.DateUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: UserInfoServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/2/11 0011 13:21
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class UserInfoServiceImpl extends AbstractWechatMiniProgramService implements UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public int updateBirthdayById(Map map) {
        return userInfoDao.updateBirthdayById(map);
    }

    @Override
    public int updateGenderById(Map map) {
        return userInfoDao.updateGenderById(map);
    }

    @Override
    public List<LinkedHashMap> getReceiveAddressListById(Map map) {
        return userInfoDao.getReceiveAddressListById(map);
    }

    @Override
    public List<LinkedHashMap> getBabyListById(Map map) {
        return userInfoDao.getBabyListById(map);
    }

    @Override
    public int saveOrUpdateAddressInfoByUserId(Map map) {
        if (map.get("userId") != null) {
            List<LinkedHashMap> addressList = userInfoDao.getReceiveAddressListById(map);
            if (addressList.size() == 0) {
                map.put("createTime", DateUtils.getAccurateDate());
                return userInfoDao.addReceiveAddress(map);
            } else {
                return userInfoDao.updateReceiveAddress(map);
            }
        } else {
            return 0;
        }
    }

    @Override
    public int addBabyInfo(Map map) {
        map.put("id", getUuid());
        map.put("createTime", DateUtils.getAccurateDate());
        return userInfoDao.addBabyInfo(map);
    }

    @Override
    public int updateBabyInfo(Map map) {
        return userInfoDao.updateBabyInfo(map);
    }

    @Override
    public Map getBaseName(Map map) {
        try {
            String nickName = userInfoDao.getBaseName(map);
            String str1 = Base64.getEncoder().encodeToString(nickName.getBytes());
            byte[] decode = Base64.getDecoder().decode(str1);
            Map resultMap = new HashedMap();
            String resStr = new String(decode);
            resultMap.put("name", str1);
            return resultMap;
        } catch (Exception e) {
            return null;
        }

    }

    public String getUuid() {
        return indexDao.getUuid();
    }
}
