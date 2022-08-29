package com.qinzi123.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.qinzi123.dao.ProductDao;
import com.qinzi123.dao.UserOrderDao;
import com.qinzi123.dto.ActivityStandardInfo;
import com.qinzi123.dto.Establishment;
import com.qinzi123.dto.ProductInfo;
import com.qinzi123.dto.ProductStandardInfo;
import com.qinzi123.service.EntityService;
import com.qinzi123.service.ProductService;
import com.qinzi123.util.DateUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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

    @Resource
    private ProductDao productDao;
    @Resource
    EntityService entityService;
    @Resource
    UserOrderDao userOrderDao;

    @Override
    public int addProduct(Map map) {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setStatus(2);
        productInfo.setIsHot(0);
        productInfo.setType(0);
        productInfo.setProductType(0);
        productInfo.setProductStyle(Integer.parseInt(map.get("productStyle").toString()));
        productInfo.setWuyuType(map.get("wuyuType").toString());
        productInfo.setCardId(map.get("card").toString());
        productInfo.setName(map.get("name").toString());
        productInfo.setAddress(map.get("address").toString());
        productInfo.setAddressName(map.get("addressName").toString());
        productInfo.setLongitude(map.get("longitude").toString());
        productInfo.setLatitude(map.get("latitude").toString());
        productInfo.setMainImage(map.get("mainImage").toString());
        int isAllowDistribution = Integer.parseInt(map.get("isAllowDistribution").toString());
        productInfo.setIsAllowDistribution(isAllowDistribution);
        productInfo.setRepeatPurchase(Integer.parseInt(map.get("repeatPurchase").toString()));
        productInfo.setPhone(map.get("phone").toString());
        if (map.get("introduce") == null) {
            productInfo.setIntroduce("");
        } else {
            productInfo.setIntroduce(Base64.getEncoder().encodeToString(map.get("introduce").toString().getBytes()));
        }
        if (map.get("instruction") == null) {
            productInfo.setInstruction("");
        } else {
            productInfo.setInstruction(Base64.getEncoder().encodeToString(map.get("instruction").toString().getBytes()));
        }
        productInfo.setVideoPath(map.get("videoPath").toString());
        productInfo.setBuyCount("0");
        productInfo.setCreateTime(DateUtils.getNow());
        productInfo.setDeadlineTime(map.get("deadlineTime").toString());
        if (map.get("qrImage") == null) {
            productInfo.setQrImage("");
        } else {
            productInfo.setQrImage(map.get("qrImage").toString());
        }
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

        //添加产品种类
        String standardsStr = map.get("standardsList").toString().replace("=", ":");
        JSONArray standardsJsonArr = JSONArray.parseArray(standardsStr);
        List<ProductStandardInfo> standardList = standardsJsonArr.toJavaList(ProductStandardInfo.class);
        standardList.forEach(item -> {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("name", item.getName());
            paramsMap.put("price", item.getPrice());
            paramsMap.put("productId", productId);
            if (isAllowDistribution == 0) {
                paramsMap.put("distribution", "");
            } else {
                paramsMap.put("distribution", item.getDistribution());
            }
            paramsMap.put("inventory", item.getInventory());
            paramsMap.put("onceMaxPurchaseCount", item.getOnceMaxPurchaseCount());
            paramsMap.put("onceMinPurchaseCount", item.getOnceMinPurchaseCount());
            paramsMap.put("createTime", DateUtils.getAccurateDate());
            productDao.addProductStandards(paramsMap);
        });

        //发布者自动成为核销员
        List<LinkedHashMap> writeOffList = productDao.getWriteOffListByCard(Integer.parseInt(productInfo.getCardId()));
        //判断发布者是否已是核销员
        if (writeOffList.size() == 0) {
            Map<String, Object> userInfo = productDao.getUserInfoById(Integer.parseInt(productInfo.getCardId()));
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("open_id", userInfo.get("openid"));
            paramsMap.put("company_id", userInfo.get("company_id"));
            paramsMap.put("nick_name", userInfo.get("realname"));
            paramsMap.put("head_img_url", userInfo.get("headimgurl"));
            paramsMap.put("createTime", DateUtils.getAccurateDate());
            productDao.addWriteOffClerk(paramsMap);
        }

        //如果该商品允许分销，给所有分销小店都添加分销记录
        if (isAllowDistribution == 1) {
            //商品允许分销
            Map<String, Object> paramsMap = new HashedMap();
            paramsMap.put("productId", productId);
            paramsMap.put("releaser_shop_id", productDao.getReleaserShopIdByProductId(paramsMap));
            paramsMap.put("create_time", DateUtils.getAccurateDate());
            List<LinkedHashMap> distributionShopList = productDao.getAllDistributionShopList();
            distributionShopList.forEach(item -> {
                paramsMap.put("primary_shop_id", Integer.parseInt(item.get("id").toString()));
                productDao.addDistributionRecord(paramsMap);
            });
        }
        return rows;
    }

    @Override
    public Map<String, Object> getBuyCountById(Map map) {
        return productDao.getBuyCountById(map);
    }

    @Override
    public int addActivity(Map map) {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setStatus(2);
        productInfo.setIsHot(0);
        productInfo.setProductType(1);
        productInfo.setActivityType1(Integer.parseInt(map.get("activityType1").toString()));
        productInfo.setActivityType2(Integer.parseInt(map.get("activityType2").toString()));
        productInfo.setWuyuType(map.get("wuyuType").toString());
        productInfo.setCardId(map.get("card").toString());
        productInfo.setName(map.get("name").toString());
        productInfo.setAddress(map.get("address").toString());
        productInfo.setAddressName(map.get("addressName").toString());
        productInfo.setLongitude(map.get("longitude").toString());
        productInfo.setLatitude(map.get("latitude").toString());
        productInfo.setMainImage(map.get("mainImage").toString());
        int isAllowDistribution = Integer.parseInt(map.get("isAllowDistribution").toString());
        productInfo.setIsAllowDistribution(isAllowDistribution);
        productInfo.setRepeatPurchase(Integer.parseInt(map.get("repeatPurchase").toString()));
        productInfo.setPhone(map.get("phone").toString());
        if (map.get("introduce") == null) {
            productInfo.setIntroduce("");
        } else {
            productInfo.setIntroduce(Base64.getEncoder().encodeToString(map.get("introduce").toString().getBytes()));
        }
        if (map.get("instruction") == null) {
            productInfo.setInstruction("");
        } else {
            productInfo.setInstruction(Base64.getEncoder().encodeToString(map.get("instruction").toString().getBytes()));
        }
        productInfo.setVideoPath(map.get("videoPath").toString());
        productInfo.setBuyCount("0");
        productInfo.setCreateTime(DateUtils.getNow());
        productInfo.setDeadlineTime(map.get("deadlineTime").toString());
        if (map.get("qrImage") == null) {
            productInfo.setQrImage("");
        } else {
            productInfo.setQrImage(map.get("qrImage").toString());
        }
        int rows = productDao.addActivity(productInfo);

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

        //添加活动规格
        String standardsStr = map.get("standardsList").toString().replace("=", ":");

        JSONArray standardsJsonArr = JSONArray.parseArray(standardsStr);
        List<ActivityStandardInfo> standardList = standardsJsonArr.toJavaList(ActivityStandardInfo.class);
        standardList.forEach(item -> {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("adultsNum", item.getAdultsNum());
            paramsMap.put("childrenNum", item.getChildrenNum());
            paramsMap.put("price", item.getPrice());
            paramsMap.put("productId", productId);
            if (isAllowDistribution == 0) {
                paramsMap.put("distribution", "");
            } else {
                paramsMap.put("distribution", item.getDistribution());
            }
            paramsMap.put("inventory", item.getInventory());
            paramsMap.put("onceMaxPurchaseCount", item.getOnceMaxPurchaseCount());
            paramsMap.put("onceMinPurchaseCount", item.getOnceMinPurchaseCount());
            paramsMap.put("createTime", DateUtils.getAccurateDate());
            paramsMap.put("productName", productDao.getProductInfoByID(paramsMap).get("name"));
            productDao.addActivityStandards(paramsMap);
        });

        //发布者自动成为核销员
        List<LinkedHashMap> writeOffList = productDao.getWriteOffListByCard(Integer.parseInt(productInfo.getCardId()));
        //判断发布者是否已是核销员
        if (writeOffList.size() == 0) {
            Map<String, Object> userInfo = productDao.getUserInfoById(Integer.parseInt(productInfo.getCardId()));
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("open_id", userInfo.get("openid"));
            paramsMap.put("company_id", userInfo.get("company_id"));
            paramsMap.put("nick_name", userInfo.get("realname"));
            paramsMap.put("head_img_url", userInfo.get("headimgurl"));
            paramsMap.put("createTime", DateUtils.getAccurateDate());
            productDao.addWriteOffClerk(paramsMap);
        }

        //如果该商品允许分销，给所有分销小店都添加分销记录
        if (isAllowDistribution == 1) {
            //商品允许分销
            Map<String, Object> paramsMap = new HashedMap();
            paramsMap.put("productId", productId);
            paramsMap.put("releaser_shop_id", productDao.getReleaserShopIdByProductId(paramsMap));
            paramsMap.put("create_time", DateUtils.getAccurateDate());
            List<LinkedHashMap> distributionShopList = productDao.getAllDistributionShopList();
            distributionShopList.forEach(item -> {
                paramsMap.put("primary_shop_id", Integer.parseInt(item.get("id").toString()));
                productDao.addDistributionRecord(paramsMap);
            });
        }
        return rows;
    }

    @Override
    public int updateProduct(Map map) {
        ProductInfo productInfo = new ProductInfo();
        int productId = Integer.parseInt(map.get("id").toString());
        productInfo.setId(productId);
        productInfo.setName(map.get("name").toString());
        productInfo.setWuyuType(map.get("wuyuType").toString());
        if (map.get("address") != null) {
            productInfo.setAddress(map.get("address").toString());
        } else {
            productInfo.setAddress("");
        }
        if (map.get("addressName") != null) {
            productInfo.setAddressName(map.get("addressName").toString());
        } else {
            productInfo.setAddressName("");
        }
        if (map.get("longitude") != null) {
            productInfo.setLongitude(map.get("longitude").toString());
        } else {
            productInfo.setLongitude("");
        }
        if (map.get("latitude") != null) {
            productInfo.setLatitude(map.get("latitude").toString());
        } else {
            productInfo.setLatitude("");
        }
        productInfo.setProductStyle(Integer.parseInt(map.get("productStyle").toString()));
        productInfo.setMainImage(map.get("mainImage").toString());
        int isAllowDistribution = Integer.parseInt(map.get("isAllowDistribution").toString());
        productInfo.setIsAllowDistribution(isAllowDistribution);
        productInfo.setRepeatPurchase(Integer.parseInt(map.get("repeatPurchase").toString()));
        productInfo.setPhone(map.get("phone").toString());
        if (map.get("introduce") == null) {
            productInfo.setIntroduce("");
        } else {
            productInfo.setIntroduce(Base64.getEncoder().encodeToString(map.get("introduce").toString().getBytes()));
        }
        if (map.get("instruction") == null) {
            productInfo.setInstruction("");
        } else {
            productInfo.setInstruction(Base64.getEncoder().encodeToString(map.get("instruction").toString().getBytes()));
        }
        if (map.get("videoPath") != null) {
            productInfo.setVideoPath(map.get("videoPath").toString());
        } else {
            productInfo.setVideoPath("");
        }
        productInfo.setDeadlineTime(map.get("deadlineTime").toString());
        if (map.get("qrImage") == null) {
            productInfo.setQrImage("");
        } else {
            productInfo.setQrImage(map.get("qrImage").toString());
        }
        int rows = productDao.updateProduct(productInfo);

        //添加图片
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

        //更新或添加产品种类
        String standardsStr = map.get("standardsList").toString().replace("=", ":");
        JSONArray standardsJsonArr = JSONArray.parseArray(standardsStr);
        List<ProductStandardInfo> standardList = standardsJsonArr.toJavaList(ProductStandardInfo.class);
        List<LinkedHashMap> oldStandardList = productDao.getOldStandardListByProductId(productId);
        oldStandardList.forEach(item -> {
            int standardId = Integer.parseInt(item.get("id").toString());
            boolean needDeleteStandardFlag = true;
            for (int i = 0; i < standardList.size(); i++) {
                if (standardList.get(i).getId() == standardId) {
                    needDeleteStandardFlag = false;
                    break;
                }
            }

            if (needDeleteStandardFlag) {
                productDao.deleteProductStandardByStandardId(standardId);
            }
        });

        standardList.forEach(item -> {
            int standardId = item.getId();
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("name", item.getName());
            paramsMap.put("price", item.getPrice());
            paramsMap.put("productId", productId);
            if (isAllowDistribution == 0) {
                paramsMap.put("distribution", "");
            } else {
                paramsMap.put("distribution", item.getDistribution());
            }
            paramsMap.put("inventory", item.getInventory());
            paramsMap.put("onceMaxPurchaseCount", item.getOnceMaxPurchaseCount());
            paramsMap.put("onceMinPurchaseCount", item.getOnceMinPurchaseCount());
            paramsMap.put("createTime", DateUtils.getAccurateDate());
            if (standardId == 0) {
                //新增规格
                productDao.addProductStandards(paramsMap);
            } else {
                //更新规格
                paramsMap.put("standardId", standardId);
                productDao.updateProductStandards(paramsMap);
            }

        });

        return rows;
    }

    @Override
    public int updateActivity(Map map) {
        ProductInfo productInfo = new ProductInfo();
        int productId = Integer.parseInt(map.get("id").toString());
        productInfo.setId(productId);
        productInfo.setName(map.get("name").toString());
        productInfo.setWuyuType(map.get("wuyuType").toString());
        productInfo.setActivityType1(Integer.parseInt(map.get("activityType1").toString()));
        productInfo.setActivityType2(Integer.parseInt(map.get("activityType2").toString()));
        if (map.get("address") != null) {
            productInfo.setAddress(map.get("address").toString());
        } else {
            productInfo.setAddress("");
        }
        if (map.get("addressName") != null) {
            productInfo.setAddressName(map.get("addressName").toString());
        } else {
            productInfo.setAddressName("");
        }
        if (map.get("longitude") != null) {
            productInfo.setLongitude(map.get("longitude").toString());
        } else {
            productInfo.setLongitude("");
        }
        if (map.get("latitude") != null) {
            productInfo.setLatitude(map.get("latitude").toString());
        } else {
            productInfo.setLatitude("");
        }
        productInfo.setMainImage(map.get("mainImage").toString());
        int isAllowDistribution = Integer.parseInt(map.get("isAllowDistribution").toString());
        productInfo.setIsAllowDistribution(isAllowDistribution);
        productInfo.setRepeatPurchase(Integer.parseInt(map.get("repeatPurchase").toString()));
        productInfo.setPhone(map.get("phone").toString());
        if (map.get("introduce") == null) {
            productInfo.setIntroduce("");
        } else {
            productInfo.setIntroduce(Base64.getEncoder().encodeToString(map.get("introduce").toString().getBytes()));
        }
        if (map.get("instruction") == null) {
            productInfo.setInstruction("");
        } else {
            productInfo.setInstruction(Base64.getEncoder().encodeToString(map.get("instruction").toString().getBytes()));
        }
        if (map.get("videoPath") != null) {
            productInfo.setVideoPath(map.get("videoPath").toString());
        } else {
            productInfo.setVideoPath("");
        }
        productInfo.setDeadlineTime(map.get("deadlineTime").toString());
        if (map.get("qrImage") == null) {
            productInfo.setQrImage("");
        } else {
            productInfo.setQrImage(map.get("qrImage").toString());
        }
        int rows = productDao.updateActivity(productInfo);

        //添加图片
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

        //更新或添加活动规格
        String standardsStr = map.get("standardsList").toString().replace("=", ":");

        JSONArray standardsJsonArr = JSONArray.parseArray(standardsStr);
        List<ActivityStandardInfo> standardList = standardsJsonArr.toJavaList(ActivityStandardInfo.class);
        List<LinkedHashMap> oldStandardList = productDao.getOldActivityStandardListByProductId(productId);
        oldStandardList.forEach(item -> {
            int standardId = Integer.parseInt(item.get("id").toString());
            boolean needDeleteStandardFlag = true;
            for (int i = 0; i < standardList.size(); i++) {
                if (standardList.get(i).getId() == standardId) {
                    needDeleteStandardFlag = false;
                    break;
                }
            }

            if (needDeleteStandardFlag) {
                productDao.deleteStandardByStandardId(standardId);
            }
        });
        standardList.forEach(item -> {
            int standardId = item.getId();
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("adultsNum", item.getAdultsNum());
            paramsMap.put("childrenNum", item.getChildrenNum());
            paramsMap.put("price", item.getPrice());
            paramsMap.put("productId", productId);
            if (isAllowDistribution == 0) {
                paramsMap.put("distribution", "");
            } else {
                paramsMap.put("distribution", item.getDistribution());
            }
            paramsMap.put("inventory", item.getInventory());
            paramsMap.put("onceMaxPurchaseCount", item.getOnceMaxPurchaseCount());
            paramsMap.put("onceMinPurchaseCount", item.getOnceMinPurchaseCount());
            paramsMap.put("createTime", DateUtils.getAccurateDate());
            paramsMap.put("productName", productDao.getProductInfoByID(paramsMap).get("name"));
            if (standardId == 0) {
                //新增规格
                productDao.addActivityStandards(paramsMap);
            } else {
                //更新规格
                paramsMap.put("standardId", standardId);
                productDao.updateActivityStandards(paramsMap);
            }
        });
        return rows;
    }

    @Override
    public int deleteProductById(Map map) {
        return productDao.deleteProductById(map);
    }

    @Override
    public int upProduct(Map map) {
        return productDao.upProduct(map);
    }

    @Override
    public int upActivity(Map map) {
        return productDao.upActivity(map);
    }

    @Override
    public int downProduct(Map map) {
        return productDao.downProduct(map);
    }

    @Override
    public int downActivity(Map map) {
        return productDao.downActivity(map);
    }

    @Override
    public List<LinkedHashMap> listProduct(int card, int start, int num) {
        return productDao.listProduct(card, start, num);
    }

    @Override
    public List<LinkedHashMap> listActivity(int card, int start, int num) {
        return productDao.listActivity(card, start, num);
    }

    @Override
    public List<LinkedHashMap> listReleasedProduct(int card, int start, int num) {
        List<LinkedHashMap> resultList = productDao.listReleasedProduct(card, start, num);
        resultList.forEach(item -> {
            int productType = Integer.parseInt(item.get("product_type").toString());
            item.put("introduce", new String(Base64.getDecoder().decode(item.get("introduce").toString())));
            item.put("instruction", new String(Base64.getDecoder().decode(item.get("instruction").toString())));
            item.put("shopForm", productDao.getShopFormByShopId(Integer.parseInt(item.get("shop_id").toString())));
            if (productType == 1) {
                //活动
                item.put("pictureCount", productDao.getPictureCountByProductId(item));
                List<LinkedHashMap> standardsList = productDao.getStandardsList(item);
                List<LinkedHashMap> standardsListOrderByDistribution = productDao.getStandardsListOrderByDistribution(item);
                item.put("present_price", standardsList.get(0).get("price"));
                item.put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                if (standardsList.size() == 1) {
                    item.put("onlyOneStandard", true);
                } else if (standardsList.size() > 1) {
                    item.put("onlyOneStandard", false);
                }
            } else if (productType == 0) {
                //产品
                List<LinkedHashMap> standardsList = productDao.getProductStandardsList(item);
                List<LinkedHashMap> standardsListOrderByDistribution = productDao.getProductStandardsListOrderByDistribution(item);
                item.put("present_price", standardsList.get(0).get("price"));
                item.put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                if (standardsList.size() == 1) {
                    item.put("onlyOneStandard", true);
                } else if (standardsList.size() > 1) {
                    item.put("onlyOneStandard", false);
                }
            }
        });
        return resultList;
    }

    @Override
    public List<LinkedHashMap> listSearchReleasedProduct(int card, int start, int num, String searchValue) {
        List<LinkedHashMap> resultList = productDao.listSearchReleasedProduct(card, start, num, searchValue);
        resultList.forEach(item -> {
            int productType = Integer.parseInt(item.get("product_type").toString());
            item.put("introduce", new String(Base64.getDecoder().decode(item.get("introduce").toString())));
            item.put("instruction", new String(Base64.getDecoder().decode(item.get("instruction").toString())));
            item.put("shopForm", productDao.getShopFormByShopId(Integer.parseInt(item.get("shop_id").toString())));
            if (productType == 1) {
                //活动
                item.put("pictureCount", productDao.getPictureCountByProductId(item));
                List<LinkedHashMap> standardsList = productDao.getStandardsList(item);
                List<LinkedHashMap> standardsListOrderByDistribution = productDao.getStandardsListOrderByDistribution(item);
                item.put("present_price", standardsList.get(0).get("price"));
                item.put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                if (standardsList.size() == 1) {
                    item.put("onlyOneStandard", true);
                } else if (standardsList.size() > 1) {
                    item.put("onlyOneStandard", false);
                }
            } else if (productType == 0) {
                //产品
                List<LinkedHashMap> standardsList = productDao.getProductStandardsList(item);
                List<LinkedHashMap> standardsListOrderByDistribution = productDao.getProductStandardsListOrderByDistribution(item);
                item.put("present_price", standardsList.get(0).get("price"));
                item.put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                if (standardsList.size() == 1) {
                    item.put("onlyOneStandard", true);
                } else if (standardsList.size() > 1) {
                    item.put("onlyOneStandard", false);
                }
            }
        });
        return resultList;
    }


    @Override
    public List<LinkedHashMap> listDistributionProduct(int card, int start, int num) {
        List<LinkedHashMap> resultList = productDao.listDistributionProduct(card, start, num);
        resultList.forEach(item -> {
            int productType = Integer.parseInt(item.get("product_type").toString());
            item.put("introduce", new String(Base64.getDecoder().decode(item.get("introduce").toString())));
            item.put("instruction", new String(Base64.getDecoder().decode(item.get("instruction").toString())));
            if (productType == 1) {
                //活动
                item.put("pictureCount", productDao.getPictureCountByProductId(item));
                List<LinkedHashMap> standardsList = productDao.getStandardsList(item);
                List<LinkedHashMap> standardsListOrderByDistribution = productDao.getStandardsListOrderByDistribution(item);
                item.put("present_price", standardsList.get(0).get("price"));
                item.put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                if (standardsList.size() == 1) {
                    item.put("onlyOneStandard", true);
                } else if (standardsList.size() > 1) {
                    item.put("onlyOneStandard", false);
                }
            } else if (productType == 0) {
                //产品
                List<LinkedHashMap> standardsList = productDao.getProductStandardsList(item);
                List<LinkedHashMap> standardsListOrderByDistribution = productDao.getProductStandardsListOrderByDistribution(item);
                item.put("present_price", standardsList.get(0).get("price"));
                item.put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                if (standardsList.size() == 1) {
                    item.put("onlyOneStandard", true);
                } else if (standardsList.size() > 1) {
                    item.put("onlyOneStandard", false);
                }
            }
        });
        return resultList;
    }


    @Override
    public List<LinkedHashMap> listSearchDistributionProduct(int card, int start, int num, String searchValue) {
        List<LinkedHashMap> resultList = productDao.listSearchDistributionProduct(card, start, num, searchValue);
        resultList.forEach(item -> {
            int productType = Integer.parseInt(item.get("product_type").toString());
            item.put("introduce", new String(Base64.getDecoder().decode(item.get("introduce").toString())));
            item.put("instruction", new String(Base64.getDecoder().decode(item.get("instruction").toString())));
            if (productType == 1) {
                //活动
                item.put("pictureCount", productDao.getPictureCountByProductId(item));
                List<LinkedHashMap> standardsList = productDao.getStandardsList(item);
                List<LinkedHashMap> standardsListOrderByDistribution = productDao.getStandardsListOrderByDistribution(item);
                item.put("present_price", standardsList.get(0).get("price"));
                item.put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                if (standardsList.size() == 1) {
                    item.put("onlyOneStandard", true);
                } else if (standardsList.size() > 1) {
                    item.put("onlyOneStandard", false);
                }
            } else if (productType == 0) {
                //产品
                List<LinkedHashMap> standardsList = productDao.getProductStandardsList(item);
                List<LinkedHashMap> standardsListOrderByDistribution = productDao.getProductStandardsListOrderByDistribution(item);
                item.put("present_price", standardsList.get(0).get("price"));
                item.put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                if (standardsList.size() == 1) {
                    item.put("onlyOneStandard", true);
                } else if (standardsList.size() > 1) {
                    item.put("onlyOneStandard", false);
                }
            }
        });
        return resultList;
    }

    @Override
    public List<LinkedHashMap> getAllListProduct(int type, String activityType1, String activityType2, int start, int num) {
        return productDao.getAllListProduct(type, activityType1, activityType2, start, num);
    }

    @Override
    public List<LinkedHashMap> getExclusiveAllList(int shopId, int type, int start, int num) {
        return productDao.getExclusiveAllList(shopId, type, start, num);
    }

    @Override
    public String getCompanyNameByCompanyId(Map map) {
        return productDao.getCompanyNameByCompanyId(map);
    }

    @Override
    public List<LinkedHashMap> getAllAllianceProductList(int card, int start, int num) {
        List<LinkedHashMap> resultList = productDao.getAllAllianceProductList(card, start, num);
        if (card != 0) {
            int shopId = productDao.getShopIdByCard(card);
            List<LinkedHashMap> distributionProductList = productDao.getDistributionProductListByShopId(shopId);
            for (int i = 0; i < resultList.size(); i++) {
                int productType = Integer.parseInt(resultList.get(i).get("product_type").toString());
                resultList.get(i).put("introduce", new String(Base64.getDecoder().decode(resultList.get(i).get("introduce").toString())));
                resultList.get(i).put("instruction", new String(Base64.getDecoder().decode(resultList.get(i).get("instruction").toString())));
                if (productType == 1) {
                    //活动
                    List<LinkedHashMap> standardsList = productDao.getStandardsList(resultList.get(i));
                    List<LinkedHashMap> standardsListOrderByDistribution = productDao.getStandardsListOrderByDistribution(resultList.get(i));
                    resultList.get(i).put("present_price", standardsList.get(0).get("price"));
                    resultList.get(i).put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                    if (standardsList.size() == 1) {
                        resultList.get(i).put("onlyOneStandard", true);
                    } else if (standardsList.size() > 1) {
                        resultList.get(i).put("onlyOneStandard", false);
                    }
                } else if (productType == 0) {
                    //产品
                    List<LinkedHashMap> standardsList = productDao.getProductStandardsList(resultList.get(i));
                    List<LinkedHashMap> standardsListOrderByDistribution = productDao.getProductStandardsListOrderByDistribution(resultList.get(i));
                    resultList.get(i).put("present_price", standardsList.get(0).get("price"));
                    resultList.get(i).put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                    if (standardsList.size() == 1) {
                        resultList.get(i).put("onlyOneStandard", true);
                    } else if (standardsList.size() > 1) {
                        resultList.get(i).put("onlyOneStandard", false);
                    }
                }

                resultList.get(i).put("hasPushedMyShop", false);
                resultList.get(i).put("shopName", productDao.getShopNameByShopId(Integer.parseInt(resultList.get(i).get("shop_id").toString())));
                if (distributionProductList.size() > 0) {
                    for (int j = 0; j < distributionProductList.size(); j++) {
                        if (Integer.parseInt(distributionProductList.get(j).get("product_id").toString()) == Integer.parseInt(resultList.get(i).get("id").toString())) {
                            resultList.get(i).put("hasPushedMyShop", true);
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < resultList.size(); i++) {
                int productType = Integer.parseInt(resultList.get(i).get("product_type").toString());
                resultList.get(i).put("introduce", new String(Base64.getDecoder().decode(resultList.get(i).get("introduce").toString())));
                resultList.get(i).put("instruction", new String(Base64.getDecoder().decode(resultList.get(i).get("instruction").toString())));
                if (productType == 1) {
                    //活动
                    List<LinkedHashMap> standardsList = productDao.getStandardsList(resultList.get(i));
                    List<LinkedHashMap> standardsListOrderByDistribution = productDao.getStandardsListOrderByDistribution(resultList.get(i));
                    resultList.get(i).put("present_price", standardsList.get(0).get("price"));
                    resultList.get(i).put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                    if (standardsList.size() == 1) {
                        resultList.get(i).put("onlyOneStandard", true);
                    } else if (standardsList.size() > 1) {
                        resultList.get(i).put("onlyOneStandard", false);
                    }
                } else if (productType == 0) {
                    //产品
                    List<LinkedHashMap> standardsList = productDao.getProductStandardsList(resultList.get(i));
                    List<LinkedHashMap> standardsListOrderByDistribution = productDao.getProductStandardsListOrderByDistribution(resultList.get(i));
                    resultList.get(i).put("present_price", standardsList.get(0).get("price"));
                    resultList.get(i).put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                    if (standardsList.size() == 1) {
                        resultList.get(i).put("onlyOneStandard", true);
                    } else if (standardsList.size() > 1) {
                        resultList.get(i).put("onlyOneStandard", false);
                    }
                }

                resultList.get(i).put("shopName", productDao.getShopNameByShopId(Integer.parseInt(resultList.get(i).get("shop_id").toString())));
                resultList.get(i).put("hasPushedMyShop", false);
            }
        }
        return resultList;
    }

    @Override
    public List<LinkedHashMap> getAllianceProductListOrderByBuyCount(int card, int start, int num) {
        List<LinkedHashMap> resultList = productDao.getAllianceProductListOrderByBuyCount(card, start, num);
        if (card != 0) {
            int shopId = productDao.getShopIdByCard(card);
            List<LinkedHashMap> distributionProductList = productDao.getDistributionProductListByShopId(shopId);
            for (int i = 0; i < resultList.size(); i++) {
                int productType = Integer.parseInt(resultList.get(i).get("product_type").toString());
                resultList.get(i).put("introduce", new String(Base64.getDecoder().decode(resultList.get(i).get("introduce").toString())));
                resultList.get(i).put("instruction", new String(Base64.getDecoder().decode(resultList.get(i).get("instruction").toString())));
                if (productType == 1) {
                    //活动
                    List<LinkedHashMap> standardsList = productDao.getStandardsList(resultList.get(i));
                    List<LinkedHashMap> standardsListOrderByDistribution = productDao.getStandardsListOrderByDistribution(resultList.get(i));
                    resultList.get(i).put("present_price", standardsList.get(0).get("price"));
                    resultList.get(i).put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                    if (standardsList.size() == 1) {
                        resultList.get(i).put("onlyOneStandard", true);
                    } else if (standardsList.size() > 1) {
                        resultList.get(i).put("onlyOneStandard", false);
                    }
                } else if (productType == 0) {
                    //产品
                    List<LinkedHashMap> standardsList = productDao.getProductStandardsList(resultList.get(i));
                    List<LinkedHashMap> standardsListOrderByDistribution = productDao.getProductStandardsListOrderByDistribution(resultList.get(i));
                    resultList.get(i).put("present_price", standardsList.get(0).get("price"));
                    resultList.get(i).put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                    if (standardsList.size() == 1) {
                        resultList.get(i).put("onlyOneStandard", true);
                    } else if (standardsList.size() > 1) {
                        resultList.get(i).put("onlyOneStandard", false);
                    }
                }

                resultList.get(i).put("shopName", productDao.getShopNameByShopId(Integer.parseInt(resultList.get(i).get("shop_id").toString())));
                resultList.get(i).put("hasPushedMyShop", false);
                if (distributionProductList.size() > 0) {
                    for (int j = 0; j < distributionProductList.size(); j++) {
                        if (Integer.parseInt(distributionProductList.get(j).get("product_id").toString()) == Integer.parseInt(resultList.get(i).get("id").toString())) {
                            resultList.get(i).put("hasPushedMyShop", true);
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < resultList.size(); i++) {
                int productType = Integer.parseInt(resultList.get(i).get("product_type").toString());
                resultList.get(i).put("introduce", new String(Base64.getDecoder().decode(resultList.get(i).get("introduce").toString())));
                resultList.get(i).put("instruction", new String(Base64.getDecoder().decode(resultList.get(i).get("instruction").toString())));
                if (productType == 1) {
                    //活动
                    List<LinkedHashMap> standardsList = productDao.getStandardsList(resultList.get(i));
                    List<LinkedHashMap> standardsListOrderByDistribution = productDao.getStandardsListOrderByDistribution(resultList.get(i));
                    resultList.get(i).put("present_price", standardsList.get(0).get("price"));
                    resultList.get(i).put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                    if (standardsList.size() == 1) {
                        resultList.get(i).put("onlyOneStandard", true);
                    } else if (standardsList.size() > 1) {
                        resultList.get(i).put("onlyOneStandard", false);
                    }
                } else if (productType == 0) {
                    //产品
                    List<LinkedHashMap> standardsList = productDao.getProductStandardsList(resultList.get(i));
                    List<LinkedHashMap> standardsListOrderByDistribution = productDao.getProductStandardsListOrderByDistribution(resultList.get(i));
                    resultList.get(i).put("present_price", standardsList.get(0).get("price"));
                    resultList.get(i).put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                    if (standardsList.size() == 1) {
                        resultList.get(i).put("onlyOneStandard", true);
                    } else if (standardsList.size() > 1) {
                        resultList.get(i).put("onlyOneStandard", false);
                    }
                }

                resultList.get(i).put("shopName", productDao.getShopNameByShopId(Integer.parseInt(resultList.get(i).get("shop_id").toString())));
                resultList.get(i).put("hasPushedMyShop", false);
            }
        }
        return resultList;
    }

    @Override
    public List<LinkedHashMap> getSearchAllianceProductList(int card, int start, int num, String searchValue) {
        List<LinkedHashMap> resultList = productDao.getSearchAllianceProductList(card, start, num, searchValue);
        if (card != 0) {
            int shopId = productDao.getShopIdByCard(card);
            List<LinkedHashMap> distributionProductList = productDao.getDistributionProductListByShopId(shopId);
            for (int i = 0; i < resultList.size(); i++) {
                int productType = Integer.parseInt(resultList.get(i).get("product_type").toString());
                resultList.get(i).put("introduce", new String(Base64.getDecoder().decode(resultList.get(i).get("introduce").toString())));
                resultList.get(i).put("instruction", new String(Base64.getDecoder().decode(resultList.get(i).get("instruction").toString())));
                if (productType == 1) {
                    //活动
                    List<LinkedHashMap> standardsList = productDao.getStandardsList(resultList.get(i));
                    List<LinkedHashMap> standardsListOrderByDistribution = productDao.getStandardsListOrderByDistribution(resultList.get(i));
                    resultList.get(i).put("present_price", standardsList.get(0).get("price"));
                    resultList.get(i).put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                    if (standardsList.size() == 1) {
                        resultList.get(i).put("onlyOneStandard", true);
                    } else if (standardsList.size() > 1) {
                        resultList.get(i).put("onlyOneStandard", false);
                    }
                } else if (productType == 0) {
                    //产品
                    List<LinkedHashMap> standardsList = productDao.getProductStandardsList(resultList.get(i));
                    List<LinkedHashMap> standardsListOrderByDistribution = productDao.getProductStandardsListOrderByDistribution(resultList.get(i));
                    resultList.get(i).put("present_price", standardsList.get(0).get("price"));
                    resultList.get(i).put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                    if (standardsList.size() == 1) {
                        resultList.get(i).put("onlyOneStandard", true);
                    } else if (standardsList.size() > 1) {
                        resultList.get(i).put("onlyOneStandard", false);
                    }
                }

                resultList.get(i).put("shopName", productDao.getShopNameByShopId(Integer.parseInt(resultList.get(i).get("shop_id").toString())));
                resultList.get(i).put("hasPushedMyShop", false);
                if (distributionProductList.size() > 0) {
                    for (int j = 0; j < distributionProductList.size(); j++) {
                        if (Integer.parseInt(distributionProductList.get(j).get("product_id").toString()) == Integer.parseInt(resultList.get(i).get("id").toString())) {
                            resultList.get(i).put("hasPushedMyShop", true);
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < resultList.size(); i++) {
                int productType = Integer.parseInt(resultList.get(i).get("product_type").toString());
                resultList.get(i).put("introduce", new String(Base64.getDecoder().decode(resultList.get(i).get("introduce").toString())));
                resultList.get(i).put("instruction", new String(Base64.getDecoder().decode(resultList.get(i).get("instruction").toString())));
                if (productType == 1) {
                    //活动
                    List<LinkedHashMap> standardsList = productDao.getStandardsList(resultList.get(i));
                    List<LinkedHashMap> standardsListOrderByDistribution = productDao.getStandardsListOrderByDistribution(resultList.get(i));
                    resultList.get(i).put("present_price", standardsList.get(0).get("price"));
                    resultList.get(i).put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                    if (standardsList.size() == 1) {
                        resultList.get(i).put("onlyOneStandard", true);
                    } else if (standardsList.size() > 1) {
                        resultList.get(i).put("onlyOneStandard", false);
                    }
                } else if (productType == 0) {
                    //产品
                    List<LinkedHashMap> standardsList = productDao.getProductStandardsList(resultList.get(i));
                    List<LinkedHashMap> standardsListOrderByDistribution = productDao.getProductStandardsListOrderByDistribution(resultList.get(i));
                    resultList.get(i).put("present_price", standardsList.get(0).get("price"));
                    resultList.get(i).put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                    if (standardsList.size() == 1) {
                        resultList.get(i).put("onlyOneStandard", true);
                    } else if (standardsList.size() > 1) {
                        resultList.get(i).put("onlyOneStandard", false);
                    }
                }

                resultList.get(i).put("shopName", productDao.getShopNameByShopId(Integer.parseInt(resultList.get(i).get("shop_id").toString())));
                resultList.get(i).put("hasPushedMyShop", false);
            }
        }
        return resultList;
    }

    @Override
    public List<LinkedHashMap> getSearchAllianceProductListOrderByBuyCount(int card, int start, int num, String searchValue) {
        List<LinkedHashMap> resultList = productDao.getSearchAllianceProductListOrderByBuyCount(card, start, num, searchValue);
        if (card != 0) {
            int shopId = productDao.getShopIdByCard(card);
            List<LinkedHashMap> distributionProductList = productDao.getDistributionProductListByShopId(shopId);
            for (int i = 0; i < resultList.size(); i++) {
                int productType = Integer.parseInt(resultList.get(i).get("product_type").toString());
                resultList.get(i).put("introduce", new String(Base64.getDecoder().decode(resultList.get(i).get("introduce").toString())));
                resultList.get(i).put("instruction", new String(Base64.getDecoder().decode(resultList.get(i).get("instruction").toString())));
                if (productType == 1) {
                    //活动
                    List<LinkedHashMap> standardsList = productDao.getStandardsList(resultList.get(i));
                    List<LinkedHashMap> standardsListOrderByDistribution = productDao.getStandardsListOrderByDistribution(resultList.get(i));
                    resultList.get(i).put("present_price", standardsList.get(0).get("price"));
                    resultList.get(i).put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                    if (standardsList.size() == 1) {
                        resultList.get(i).put("onlyOneStandard", true);
                    } else if (standardsList.size() > 1) {
                        resultList.get(i).put("onlyOneStandard", false);
                    }
                } else if (productType == 0) {
                    //产品
                    List<LinkedHashMap> standardsList = productDao.getProductStandardsList(resultList.get(i));
                    List<LinkedHashMap> standardsListOrderByDistribution = productDao.getProductStandardsListOrderByDistribution(resultList.get(i));
                    resultList.get(i).put("present_price", standardsList.get(0).get("price"));
                    resultList.get(i).put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                    if (standardsList.size() == 1) {
                        resultList.get(i).put("onlyOneStandard", true);
                    } else if (standardsList.size() > 1) {
                        resultList.get(i).put("onlyOneStandard", false);
                    }
                }

                resultList.get(i).put("shopName", productDao.getShopNameByShopId(Integer.parseInt(resultList.get(i).get("shop_id").toString())));
                resultList.get(i).put("hasPushedMyShop", false);
                if (distributionProductList.size() > 0) {
                    for (int j = 0; j < distributionProductList.size(); j++) {
                        if (Integer.parseInt(distributionProductList.get(j).get("product_id").toString()) == Integer.parseInt(resultList.get(i).get("id").toString())) {
                            resultList.get(i).put("hasPushedMyShop", true);
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < resultList.size(); i++) {
                int productType = Integer.parseInt(resultList.get(i).get("product_type").toString());
                resultList.get(i).put("introduce", new String(Base64.getDecoder().decode(resultList.get(i).get("introduce").toString())));
                resultList.get(i).put("instruction", new String(Base64.getDecoder().decode(resultList.get(i).get("instruction").toString())));
                if (productType == 1) {
                    //活动
                    List<LinkedHashMap> standardsList = productDao.getStandardsList(resultList.get(i));
                    List<LinkedHashMap> standardsListOrderByDistribution = productDao.getStandardsListOrderByDistribution(resultList.get(i));
                    resultList.get(i).put("present_price", standardsList.get(0).get("price"));
                    resultList.get(i).put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                    if (standardsList.size() == 1) {
                        resultList.get(i).put("onlyOneStandard", true);
                    } else if (standardsList.size() > 1) {
                        resultList.get(i).put("onlyOneStandard", false);
                    }
                } else if (productType == 0) {
                    //产品
                    List<LinkedHashMap> standardsList = productDao.getProductStandardsList(resultList.get(i));
                    List<LinkedHashMap> standardsListOrderByDistribution = productDao.getProductStandardsListOrderByDistribution(resultList.get(i));
                    resultList.get(i).put("present_price", standardsList.get(0).get("price"));
                    resultList.get(i).put("distribution", standardsListOrderByDistribution.get(0).get("distribution"));
                    if (standardsList.size() == 1) {
                        resultList.get(i).put("onlyOneStandard", true);
                    } else if (standardsList.size() > 1) {
                        resultList.get(i).put("onlyOneStandard", false);
                    }
                }

                resultList.get(i).put("shopName", productDao.getShopNameByShopId(Integer.parseInt(resultList.get(i).get("shop_id").toString())));
                resultList.get(i).put("hasPushedMyShop", false);
            }
        }
        return resultList;
    }

    @Override
    public int addDistributionRecord(Map map) {
        map.put("releaser_shop_id", productDao.getReleaserShopIdByProductId(map));
        map.put("primary_shop_id", productDao.getShopIdByCard(Integer.parseInt(map.get("card").toString())));
        map.put("create_time", DateUtils.getAccurateDate());
        return productDao.addDistributionRecord(map);
    }

    @Override
    public List<LinkedHashMap> getSearchList(Map map) {
        return productDao.getSearchList(map);
    }

    @Override
    public List<LinkedHashMap> getExclusiveSearchList(Map map) {
        return productDao.getExclusiveSearchList(map);
    }

    @Override
    public List<LinkedHashMap> getOtherImagesById(int productId) {
        return productDao.getOtherImagesById(productId);
    }

    @Override
    public Map<String, Object> getReleaseShopInfoByShopId(Map map) {
        return productDao.getReleaseShopInfoByShopId(map);
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

    @Override
    public int getInventoryByStandardId(Map map) {
        int productType = Integer.parseInt(map.get("productType").toString());
        int inventory = 0;
        if (productType == 0) {
            inventory = Integer.parseInt(productDao.getProductInventoryByStandardId(map));
        } else if (productType == 1) {
            inventory = Integer.parseInt(productDao.getActivityInventoryByStandardId(map));
        }
        return inventory;
    }

    @Override
    public Map<String, Object> getOrderCount(Map map) {
        Map<String, Object> resultMap = new HashedMap();
        resultMap.put("peddingPayCouont", productDao.getPeddingPayCouontByCardId(map).size());
        resultMap.put("peddingconfirm", productDao.getPeddingconfirmByCardId(map).size());
        return resultMap;
    }

    @Override
    public Map<String, Object> loadOrderCountByUserId(Map map) {
        Map<String, Object> resultMap = new HashedMap();
        resultMap.put("peddingPayCouont", productDao.getPeddingPayCouontByUserId(map).size());
        resultMap.put("peddingconfirm", productDao.getPeddingconfirmByUserId(map).size());
        return resultMap;
    }

    @Override
    public List<LinkedHashMap> getSelectedOrderList(Map map) {
        int selectType = Integer.parseInt(map.get("selectType").toString());
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        int card = Integer.parseInt(map.get("card").toString());
        List<LinkedHashMap> list;
        if (selectType == -1) {
            //全部订单
            list = productDao.getAllOrderList(card, start, num);
        } else {
            list = productDao.getSelectedOrderList(card, selectType, start, num);
        }
        list.forEach(item -> {
            Map<String, Object> paramsMap = new HashedMap();
            paramsMap.put("productId", item.get("product_id").toString());
            paramsMap.put("openId", item.get("open_id").toString());
            Map productMap = productDao.getProductInfoByID(paramsMap);
            Map userInfoMap = productDao.getUserInfoByOpenID(paramsMap);
            Map<String, Object> standardInfo;
            int standardId = Integer.parseInt(item.get("standard_id").toString());
            int issuerCardId = Integer.parseInt(item.get("card_id").toString());
            item.put("isIssuer", issuerCardId == card);
            String standardName = "";
            if (productMap != null) {
                int productType = Integer.parseInt((productMap.get("product_type").toString()));
                if (productType == 0) {
                    if (Integer.parseInt((productMap.get("product_style").toString())) == 1) {
                        //实体产品，带出收货地址
                        String openId = item.get("open_id").toString();
                        String userId = userOrderDao.getUserIdByOpenId(openId);
                        Map<String, Object> addressMap = userOrderDao.getReceiveAddressByUserId(userId);
                        item.put("receive_name", addressMap.get("receive_name"));
                        item.put("receive_phone", addressMap.get("receive_phone"));
                        item.put("receive_address", addressMap.get("province").toString() + addressMap.get("city").toString() + addressMap.get("area").toString() + addressMap.get("address").toString());
                        item.put("hasReceiveAddress", true);
                    } else {
                        item.put("hasReceiveAddress", false);
                    }
                    standardInfo = userOrderDao.getProductStandardInfoByStandardId(standardId);
                    standardName = standardInfo.get("name").toString();
                } else {
                    item.put("hasReceiveAddress", false);
                    standardInfo = userOrderDao.getActivityStandardInfoByStandardId(standardId);
                    int adultsNum = Integer.parseInt(standardInfo.get("adults_num").toString());
                    int childrenNum = Integer.parseInt(standardInfo.get("children_num").toString());
                    if (adultsNum == 0 && childrenNum != 0) {
                        standardName = "儿童票";
                    } else if (adultsNum != 0 && childrenNum == 0) {
                        standardName = "成人票";
                    } else {
                        standardName = "亲子" + standardInfo.get("adults_num").toString() + "大" + standardInfo.get("children_num").toString() + "小";
                    }
                }
            }
            item.put("nickName", userInfoMap.get("nick_name").toString());
            item.put("standardName", standardName);
        });
        return list;
    }

    @Override
    public List<LinkedHashMap> getSelectedOrderListByUserId(Map map) {
        int selectType = Integer.parseInt(map.get("selectType").toString());
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        String userId1 = map.get("userId").toString();
        List<LinkedHashMap> list;
        if (selectType == -1) {
            //全部订单
            list = productDao.getAllDistributionOrderList(userId1, start, num);
        } else {
            list = productDao.getSelectedDistributionOrderList(userId1, selectType, start, num);
        }
        list.forEach(item -> {
            Map<String, Object> paramsMap = new HashedMap();
            paramsMap.put("productId", item.get("product_id").toString());
            paramsMap.put("openId", item.get("open_id").toString());
            Map productMap = productDao.getProductInfoByID(paramsMap);
            Map userInfoMap = productDao.getUserInfoByOpenID(paramsMap);
            Map<String, Object> standardInfo;
            int standardId = Integer.parseInt(item.get("standard_id").toString());
            String standardName = "";
            if (productMap != null) {
                int productType = Integer.parseInt((productMap.get("product_type").toString()));
                if (productType == 0) {
                    if (Integer.parseInt((productMap.get("product_style").toString())) == 1) {
                        //实体产品，带出收货地址
                        String openId = item.get("open_id").toString();
                        String userId = userOrderDao.getUserIdByOpenId(openId);
                        Map<String, Object> addressMap = userOrderDao.getReceiveAddressByUserId(userId);
                        item.put("receive_name", addressMap.get("receive_name"));
                        item.put("receive_phone", addressMap.get("receive_phone"));
                        item.put("receive_address", addressMap.get("province").toString() + addressMap.get("city").toString() + addressMap.get("area").toString() + addressMap.get("address").toString());
                        item.put("hasReceiveAddress", true);
                    } else {
                        item.put("hasReceiveAddress", false);
                    }
                    standardInfo = userOrderDao.getProductStandardInfoByStandardId(standardId);
                    standardName = standardInfo.get("name").toString();
                } else {
                    item.put("hasReceiveAddress", false);
                    standardInfo = userOrderDao.getActivityStandardInfoByStandardId(standardId);
                    int adultsNum = Integer.parseInt(standardInfo.get("adults_num").toString());
                    int childrenNum = Integer.parseInt(standardInfo.get("children_num").toString());
                    if (adultsNum == 0 && childrenNum != 0) {
                        standardName = "儿童票";
                    } else if (adultsNum != 0 && childrenNum == 0) {
                        standardName = "成人票";
                    } else {
                        standardName = "亲子" + standardInfo.get("adults_num").toString() + "大" + standardInfo.get("children_num").toString() + "小";
                    }
                }
            }
            item.put("nickName", new String(Base64.getDecoder().decode(userInfoMap.get("nick_name").toString())));
            item.put("phone", userInfoMap.get("phone").toString());
            item.put("head_img_url", userInfoMap.get("head_img_url").toString());
            item.put("standardName", standardName);
        });
        return list;
    }

    @Override
    public List<LinkedHashMap> getStandardListByProductId(Map map) {
        List<LinkedHashMap> list = productDao.getStandardListByProductId(map);
        list.forEach(item -> {
            int standardId = Integer.parseInt(item.get("id").toString());
            int orderCount = productDao.getActivityOrderCountByStandardId(standardId);
            //查询该产品种类是否已有订单
            if (orderCount == 0) {
                item.put("hasOrder", false);
            } else if (orderCount > 0) {
                item.put("hasOrder", true);
            }
        });
        return list;
    }

    @Override
    public int getOrderCountByproductId(Map map) {
        return productDao.getOrderCountByproductId(map);
    }

    @Override
    public List<LinkedHashMap> getProductStandardListByProductId(Map map) {
        List<LinkedHashMap> list = productDao.getProductStandardListByProductId(map);
        list.forEach(item -> {
            int standardId = Integer.parseInt(item.get("id").toString());
            int orderCount = productDao.getProductOrderCountByStandardId(standardId);
            //查询该产品种类是否已有订单
            if (orderCount == 0) {
                item.put("hasOrder", false);
            } else if (orderCount > 0) {
                item.put("hasOrder", true);
            }
        });
        return list;
    }

    @Override
    public List<LinkedHashMap> getStandardsList(Map map) {
        return productDao.getStandardsList(map);
    }


    @Override
    public Map loadProductInfoById(Map map) {
        Map<String, Object> resultMap = productDao.loadProductInfoById(map);
        resultMap.put("introduce", new String(Base64.getDecoder().decode(resultMap.get("introduce").toString())));
        resultMap.put("instruction", new String(Base64.getDecoder().decode(resultMap.get("instruction").toString())));
        return resultMap;
    }

    @Override
    public int getIssuerShopIdByProductId(int productId) {
        return productDao.getIssuerShopIdByProductId(productId);
    }

    @Override
    public Map<String, Object> getIssuerShopInfoByProductId(int productId) {
        return productDao.getIssuerShopInfoByProductId(productId);
    }

    @Override
    public int getMyShopIdByCard(int card) {
        return productDao.getMyShopIdByCard(card);
    }

    @Override
    public String getPhoneByUserId(String userId) {
        return productDao.getPhoneByUserId(userId);
    }

    @Override
    public List<LinkedHashMap> getAllActivityList(int start, int num) {
        return productDao.getAllActivityList(start, num);
    }

    @Override
    public List<LinkedHashMap> getSearchActivityList(Map map) {
        return productDao.getSearchActivityList(map);
    }

    @Override
    public List<LinkedHashMap> getRecommendProductList(Map map) {
        return productDao.getRecommendProductList(map);
    }

    @Override
    public List<LinkedHashMap> getRecommendProductListByShopId(Map map) {
        return productDao.getRecommendProductListByShopId(map);
    }

    @Override
    public List<LinkedHashMap> getActivityPictureByProductId(Map map) {
        return productDao.getActivityPictureByProductId(map);
    }

    @Override
    public int saveActivityPictureById(Map map) {
        //添加图片
        int productId = Integer.parseInt(map.get("productId").toString());
        int rows = 0;
        if (map.get("pictureList") != null) {
            String pciStr = map.get("pictureList").toString();
            pciStr = pciStr.replace("[", "");
            pciStr = pciStr.replace("]", "");
            pciStr = pciStr.replace(" ", "");
            if (StringUtils.isNotEmpty(pciStr)) {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("productId", productId);
                productDao.deleteAitivityPictureByProductId(picMap);

                Map<String, Object> paramsMap = new HashMap<>();
                String[] picArr;
                if (pciStr.contains(",")) {
                    picArr = pciStr.split(",");
                    for (String s : picArr) {
                        paramsMap.put("productId", productId);
                        paramsMap.put("url", s);
                        productDao.addAvtivityPicture(paramsMap);
                        rows += 1;
                    }
                } else {
                    paramsMap.put("productId", productId);
                    paramsMap.put("url", pciStr);
                    rows = productDao.addAvtivityPicture(paramsMap);
                }
            } else {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("productId", productId);
                rows = productDao.deleteAitivityPictureByProductId(picMap);
            }
        }
        return rows;
    }

    @Override
    public int deleteDistributionRecord(Map map) {
        return productDao.deleteDistributionRecord(map);
    }

    @Override
    public int checkProductStatus(Map map) {
        return productDao.checkProductStatus(map);
    }
}
