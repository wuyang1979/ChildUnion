package com.qinzi123.service.impl;

import com.qinzi123.dao.EstablishmentDao;
import com.qinzi123.dto.Establishment;
import com.qinzi123.dto.EstablishmentInfo;
import com.qinzi123.service.EstablishmentService;
import com.qinzi123.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: EstablishmentServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2021/12/6 0006 10:10
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Service
public class EstablishmentServiceImpl extends AbstractWechatMiniProgramService implements EstablishmentService {

    @Autowired
    EstablishmentDao establishmentDao;

    @Override
    public List<LinkedHashMap> listEstablishment(int start, int num) {
        List<LinkedHashMap> list = establishmentDao.listEstablishment(start, num);
        list.forEach(item -> {
            item.put("members", Establishment.Scope.getValue(item.get("members").toString()));
            item.put("industry", Establishment.Industry.getValue(item.get("industry").toString()));
        });
        return list;
    }

    @Override
    public List<LinkedHashMap> getEstablishmentInfoById(int establishmentId) {
        List<LinkedHashMap> list = establishmentDao.getEstablishmentInfoById(establishmentId);
        list.forEach(item -> {
            item.put("members", Establishment.Scope.getValue(item.get("members").toString()));
            item.put("industry", Establishment.Industry.getValue(item.get("industry").toString()));
        });
        return list;
    }

    @Override
    public List<LinkedHashMap> getMemberList(int establishmentId) {
        return establishmentDao.getMemberList(establishmentId);
    }

    @Override
    public List<LinkedHashMap> getMainMember(int establishmentId) {
        return establishmentDao.getMainMember(establishmentId);
    }

    @Override
    public List<LinkedHashMap> listSearchEstablishment(Map<String, Object> map) {
        List<LinkedHashMap> list = establishmentDao.listSearchEstablishment(map);
        list.forEach(item -> {
            item.put("members", Establishment.Scope.getValue(item.get("members").toString()));
            item.put("industry", Establishment.Industry.getValue(item.get("industry").toString()));
        });
        return list;
    }

    @Override
    public List<LinkedHashMap> listScreenEstablishment(Map<String, Object> map) {
        List<LinkedHashMap> list = establishmentDao.listScreenEstablishment(map);
        list.forEach(item -> {
            item.put("members", Establishment.Scope.getValue(item.get("members").toString()));
            item.put("industry", Establishment.Industry.getValue(item.get("industry").toString()));
        });
        return list;
    }

    @Override
    public List<LinkedHashMap> getEstablishmentListByCompany(Map<String, Object> map) {
        return establishmentDao.getEstablishmentListByCompany(map);
    }

    @Override
    public List<LinkedHashMap> getEstablishmentInfoByCompanyId(Map map) {
        return establishmentDao.getEstablishmentInfoByCompanyId(map);
    }

    @Override
    public List<LinkedHashMap> getLatestEstablishmentList(Map map) {
        return establishmentDao.getLatestEstablishmentList(map);
    }

    @Override
    public Map<String, Object> getAdminName(Map<String, Object> map) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("adminName", establishmentDao.getAdminName(map));
        return resultMap;
    }

    @Override
    public Map<String, Object> isExistEstablishmentMember(Map map) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("companyId", establishmentDao.isExistEstablishmentMember(map));
        return resultMap;
    }

    @Override
    public Map<String, Object> isSelfEstablishmentLeague(Map map) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("leagueType", establishmentDao.isSelfEstablishmentLeague(map));
        return resultMap;
    }

    @Override
    public int addEstablishment(Map map) {
        LinkedHashMap<String, Object> cardInfoMap = establishmentDao.getCardInfo(map.get("cardId").toString());
        EstablishmentInfo establishmentInfo = new EstablishmentInfo();
        establishmentInfo.setCardId(map.get("cardId").toString());
        establishmentInfo.setMainImage(map.get("mainImage").toString());
        establishmentInfo.setCompany(map.get("company").toString());
        establishmentInfo.setIndustry(map.get("industry").toString());
        establishmentInfo.setScope(map.get("scope").toString());
        establishmentInfo.setAddress(map.get("address").toString());
        establishmentInfo.setCompanyTel(map.get("companyTel").toString());
        if (map.get("companyWeb") != null) {
            establishmentInfo.setCompanyWeb(map.get("companyWeb").toString());
        }
        if (map.get("licensePic") != null) {
            establishmentInfo.setLicensepic(map.get("licensePic").toString());
        }
        establishmentInfo.setEmail(map.get("email").toString());
        establishmentInfo.setIntroduce(map.get("introduce").toString());
        establishmentInfo.setMainBusiness(map.get("mainBusiness").toString());
        establishmentInfo.setMainDemand(map.get("mainDemand").toString());

        establishmentInfo.setContactname(cardInfoMap.get("realname").toString());
        establishmentInfo.setContactduty(cardInfoMap.get("job").toString());
        establishmentInfo.setContacttel(cardInfoMap.get("phone").toString());
        establishmentInfo.setContactwx(cardInfoMap.get("weixincode").toString());
        establishmentInfo.setContactopenid(cardInfoMap.get("openId").toString());
        establishmentInfo.setSubmittime(DateUtils.getNow());
        establishmentInfo.setUpdatetime(DateUtils.getNow());
        establishmentInfo.setLeaguetype(0);
        int addRows = establishmentDao.addEstablishment(establishmentInfo);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("companyId", establishmentInfo.getId());
        paramsMap.put("cardId", establishmentInfo.getCardId());
        int updateRows = establishmentDao.updateCompanyIdByCardId(paramsMap);
        return addRows;
    }

    @Override
    public int updateEstablishment(Map map) {
        return establishmentDao.updateEstablishment(map);
    }
}
