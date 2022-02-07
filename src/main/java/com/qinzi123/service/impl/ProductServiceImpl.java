package com.qinzi123.service.impl;

import com.qinzi123.dao.ProductDao;
import com.qinzi123.dto.Establishment;
import com.qinzi123.dto.ProductInfo;
import com.qinzi123.service.EntityService;
import com.qinzi123.service.ProductService;
import com.qinzi123.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: ProductServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2022/1/22 0022 11:29
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */

@Service
public class ProductServiceImpl extends AbstractWechatMiniProgramService implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    EntityService entityService;

    @Override
    public int addProduct(Map map) {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setStatus(0);
        productInfo.setType(0);
        productInfo.setCardId(map.get("card").toString());
        productInfo.setName(map.get("name").toString());
        productInfo.setMainImage(map.get("mainImage").toString());
        productInfo.setOriginalPrice(map.get("originalPrice").toString());
        productInfo.setPresentPrice(map.get("presentPrice").toString());
        productInfo.setInventory(map.get("inventory").toString());
        productInfo.setRepeatPurchase(Integer.parseInt(map.get("repeatPurchase").toString()));
        productInfo.setOnceMaxPurchaseCount(Integer.parseInt(map.get("onceMaxPurchaseCount").toString()));
        productInfo.setPhone(map.get("phone").toString());
        productInfo.setIntroduce(map.get("introduce").toString());
        productInfo.setVideoPath(map.get("videoPath").toString());
        productInfo.setInstruction(map.get("instruction").toString());
        productInfo.setCreateTime(DateUtils.getNow());
        productInfo.setDeadlineTime(map.get("deadlineTime").toString());
        int rows = productDao.addProduct(productInfo);

        //添加图片
        int productId = productInfo.getId();
        if (map.get("otherImage") != null) {
            String pciStr = map.get("otherImage").toString();
            pciStr = pciStr.replace("[", "");
            pciStr = pciStr.replace("]", "");
            pciStr = pciStr.replace(" ", "");
            if (StringUtils.isNotEmpty(pciStr)) {
                Map<String, Object> paramsMap = new HashMap<>();
                String[] picArr;
                if (pciStr.contains(",")) {
                    picArr = pciStr.split(",");
                    for (String s : picArr) {
                        paramsMap.put("productId", productId);
                        paramsMap.put("url", s);
                        productDao.addOtherImagesToProduct(paramsMap);
                    }
                } else {
                    paramsMap.put("productId", productId);
                    paramsMap.put("url", pciStr);
                    productDao.addOtherImagesToProduct(paramsMap);
                }
            }
        }
        return rows;
    }

    @Override
    public int updateProduct(Map map) {
        ProductInfo productInfo = new ProductInfo();
//        productInfo.setStatus(0);
//        productInfo.setCardId(map.get("card").toString());
        productInfo.setId(Integer.parseInt(map.get("id").toString()));
        productInfo.setName(map.get("name").toString());
        productInfo.setMainImage(map.get("mainImage").toString());
        productInfo.setOriginalPrice(map.get("originalPrice").toString());
        productInfo.setPresentPrice(map.get("presentPrice").toString());
        productInfo.setInventory(map.get("inventory").toString());
        productInfo.setRepeatPurchase(Integer.parseInt(map.get("repeatPurchase").toString()));
        productInfo.setOnceMaxPurchaseCount(Integer.parseInt(map.get("onceMaxPurchaseCount").toString()));
        productInfo.setPhone(map.get("phone").toString());
        productInfo.setIntroduce(map.get("introduce").toString());
        productInfo.setVideoPath(map.get("videoPath").toString());
        productInfo.setInstruction(map.get("instruction").toString());
        productInfo.setDeadlineTime(map.get("deadlineTime").toString());
        int rows = productDao.updateProduct(productInfo);

        //添加图片
        int productId = productInfo.getId();
        if (map.get("otherImage") != null) {
            String pciStr = map.get("otherImage").toString();
            pciStr = pciStr.replace("[", "");
            pciStr = pciStr.replace("]", "");
            pciStr = pciStr.replace(" ", "");
            if (StringUtils.isNotEmpty(pciStr)) {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("productId", productId);
                entityService.deleteProductInfoPicList(picMap);

                Map<String, Object> paramsMap = new HashMap<>();
                String[] picArr;
                if (pciStr.contains(",")) {
                    picArr = pciStr.split(",");
                    for (String s : picArr) {
                        paramsMap.put("productId", productId);
                        paramsMap.put("url", s);
                        productDao.addOtherImagesToProduct(paramsMap);
                    }
                } else {
                    paramsMap.put("productId", productId);
                    paramsMap.put("url", pciStr);
                    productDao.addOtherImagesToProduct(paramsMap);
                }
            } else {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("productId", productId);
                entityService.deleteProductInfoPicList(picMap);
            }
        }
        return rows;
    }

    @Override
    public int deleteProductById(Map map) {
        return productDao.deleteProductById(map);
    }

    @Override
    public List<LinkedHashMap> listProduct(int card, int start, int num) {
        return productDao.listProduct(card, start, num);
    }

    @Override
    public List<LinkedHashMap> getOtherImagesById(int productId) {
        return productDao.getOtherImagesById(productId);
    }

    @Override
    public Map<String, Object> getReleaseCompanyInfoByCardId(Map map) {
        return productDao.getReleaseCompanyInfoByCardId(map);
    }

    @Override
    public List<LinkedHashMap> getReleaseCompanyInfoByCompanyId(Map map) {
        List<LinkedHashMap> list = productDao.getReleaseCompanyInfoByCompanyId(map);
        list.forEach(item -> {
            item.put("members", Establishment.Scope.getValue(item.get("members").toString()));
            item.put("industry", Establishment.Industry.getValue(item.get("industry").toString()));
        });
        return list;
    }
}
