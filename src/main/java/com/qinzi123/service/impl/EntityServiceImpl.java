package com.qinzi123.service.impl;

import com.qinzi123.dao.CardDao;
import com.qinzi123.dao.EntityDao;
import com.qinzi123.dao.ProductDao;
import com.qinzi123.dao.TableConfigDao;
import com.qinzi123.dto.*;
import com.qinzi123.service.EntityService;
import com.qinzi123.service.PushMiniProgramService;
import com.qinzi123.util.DateUtils;
import com.qinzi123.util.JsonUtil;
import com.qinzi123.util.Utils;
import io.swagger.models.auth.In;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @title: EntityServiceImpl
 * @package: com.qinzi123.service.impl
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Component
public class EntityServiceImpl implements EntityService {

    @Resource
    public EntityDao entityDao;

    @Resource
    public TableConfigDao tableConfigDao;

    @Resource
    PushMiniProgramService pushService;

    @Resource
    CardDao cardDao;

    @Resource
    EntityService entityService;

    @Resource
    ProductDao productDao;

    private HashMap<String, List<String>> tableKeyMap = new HashMap<String, List<String>>();
    private HashMap<String, HashMap<String, Column>> tableColumnMap = new HashMap<String, HashMap<String, Column>>();

    // 获取表的主键,作为修改和删除的依据
    private List<String> getKeyByTable(String tableName) {
        if (tableKeyMap.size() == 0) {
            List<TableConfig> list = tableConfigDao.findTableConfig();
            for (TableConfig tableConfig : list) {
                List<String> keyList = Arrays.asList(tableConfig.getKeyList().split(","));
                tableKeyMap.put(tableConfig.getTableName(), keyList);
            }
        }
        return tableKeyMap.get(tableName);
    }

    private HashMap<String, Column> getColumnByTable(String tableName) {
        if (tableColumnMap.size() == 0) {
            List<HashMap> list = tableConfigDao.findTableColumn();
            for (HashMap map : list) {
                String table = map.get("table_name").toString();
                String columnName = map.get("column_name").toString();
                HashMap<String, Column> tableMap = tableColumnMap.get(table) == null ? new HashMap<String, Column>() : tableColumnMap.get(table);
                tableMap.put(columnName, new Column(columnName, map.get("table_name").toString()));
                tableColumnMap.put(table, tableMap);
            }
        }
        return tableColumnMap.get(tableName);
    }

    interface Loop {
        void run(String key, String value);
    }

    // 迭代前端传入的数据
    private void iterator(Map<String, Object> map, Loop loop) {
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            loop.run(entry.getKey(), entry.getValue().toString());
        }
    }

    // 插入数据
    @Override
    public int addService(String tableName, Map<String, Object> map) {
        List<String> keyColumns = new LinkedList<String>();
        List<String> valueColumns = new LinkedList<String>();
        List<String> keys = getKeyByTable(tableName);
        iterator(map, new Loop() {
            @Override
            public void run(String key, String value) {
                if (keys.contains(key)) {
                    return; // 目前所有主键都是自动生成,这里屏蔽
                }
                keyColumns.add(key);
                valueColumns.add(Utils.fillColumn(value));
            }
        });
        return entityDao.addEntity(tableName, Utils.join(keyColumns, ","), Utils.join(valueColumns, ","));
    }

    // 插入活动基地数据
    @Override
    public int addBaseService(Map<String, Object> map) {
        return entityDao.addBaseService(map);
    }

    // 插入活动基地数据
    @Override
    public int addEnterpriseService(EnterpriseInfo enterpriseInfo) {
        return entityDao.addEnterpriseService(enterpriseInfo);
    }

    @Override
    public int getProductId(String name) {
        return entityDao.getProductId(name);
    }

    @Override
    public int getBaseId(String name) {
        return entityDao.getBaseId(name);
    }

    // 插入其他图片到产品表
    @Override
    public int addOtherImages(Map<String, Object> map) {
        int productId = Integer.parseInt(map.get("productId").toString());
        String pciStr = map.get("other_image").toString();
        Map<String, Object> paramsMap = new HashMap<>();
        String[] picArr;
        if (pciStr.contains(",")) {
            picArr = pciStr.split(",");
            for (String s : picArr) {
                paramsMap.put("productId", productId);
                paramsMap.put("url", s);
                entityDao.addOtherImages(paramsMap);
            }
        } else {
            paramsMap.put("productId", productId);
            paramsMap.put("url", pciStr);
            entityDao.addOtherImages(paramsMap);
        }
        return productId;
    }

    // 插入其他图片到基地表
    @Override
    public int addOtherImagesToBase(Map<String, Object> map) {
        int baseId = Integer.parseInt(map.get("baseId").toString());
        String pciStr = map.get("other_image").toString();
        Map<String, Object> paramsMap = new HashMap<>();
        String[] picArr;
        if (pciStr.contains(",")) {
            picArr = pciStr.split(",");
            for (String s : picArr) {
                paramsMap.put("baseId", baseId);
                paramsMap.put("url", s);
                entityDao.addOtherImagesToBase(paramsMap);
            }
        } else {
            paramsMap.put("baseId", baseId);
            paramsMap.put("url", pciStr);
            entityDao.addOtherImagesToBase(paramsMap);
        }
        return baseId;
    }

    // 插入其他图片到基地表
    @Override
    public int addOtherImagesToEnterprise(Map<String, Object> map) {
        int enterpriseId = Integer.parseInt(map.get("enterpriseId").toString());
        String pciStr = map.get("other_image").toString();
        Map<String, Object> paramsMap = new HashMap<>();
        String[] picArr;
        if (pciStr.contains(",")) {
            picArr = pciStr.split(",");
            for (String s : picArr) {
                paramsMap.put("enterpriseId", enterpriseId);
                paramsMap.put("url", s);
                entityDao.addOtherImagesToEnterprise(paramsMap);
            }
        } else {
            paramsMap.put("enterpriseId", enterpriseId);
            paramsMap.put("url", pciStr);
            entityDao.addOtherImagesToEnterprise(paramsMap);
        }
        return enterpriseId;
    }

    // 插入其他图片到产品图片表
    @Override
    public int addOtherImagesToProductInfo(Map<String, Object> map) {
        int productId = Integer.parseInt(map.get("productId").toString());
        String pciStr = map.get("other_image").toString();
        Map<String, Object> paramsMap = new HashMap<>();
        String[] picArr;
        if (pciStr.contains(",")) {
            picArr = pciStr.split(",");
            for (String s : picArr) {
                paramsMap.put("productId", productId);
                paramsMap.put("url", s);
                entityDao.addOtherImagesToProductInfo(paramsMap);
            }
        } else {
            paramsMap.put("productId", productId);
            paramsMap.put("url", pciStr);
            entityDao.addOtherImagesToProductInfo(paramsMap);
        }
        return productId;
    }

    // 新增产品规格
    @Override
    public int addProductStandards(Map<String, Object> map) {
        int productId = Integer.parseInt(map.get("productId").toString());
        List<ProductStandardDto> standardList = JsonUtil.productStandardMapToList(map, "standardList");
        standardList.forEach(productStandard -> {
            productStandard.setProductId(productId);
            productStandard.setCreateTime(DateUtils.getAccurateDate());
            entityDao.addProductStandard(productStandard);
        });
        return productId;
    }

    // 新增活动规格
    @Override
    public int addActivityStandards(Map<String, Object> map) {
        int productId = Integer.parseInt(map.get("productId").toString());
        List<ActivityStandardDto> standardList = JsonUtil.activityStandardMapToList(map, "standardList");
        standardList.forEach(activityStandard -> {
            activityStandard.setProductId(productId);
            activityStandard.setCreateTime(DateUtils.getAccurateDate());
            activityStandard.setProductName(productDao.getProductInfoByID(map).get("name").toString());
            entityDao.addActivityStandard(activityStandard);
        });
        return productId;
    }

    // 新增机构信息
    @Override
    public int addEstablishmentService(Map<String, Object> map) {
        map.put("submittime", DateUtils.getNow());
        map.put("updatetime", DateUtils.getNow());
        return entityDao.addEstablishmentService(map);
    }

    // 新增产品信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addProductInfoService(Map<String, Object> map) {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setStatus(Integer.parseInt(map.get("status").toString()));
        productInfo.setIsHot(Integer.parseInt(map.get("is_hot").toString()));
        productInfo.setCardId(map.get("card_id").toString());
        productInfo.setShopId(Integer.parseInt(map.get("shop_id").toString()));
        productInfo.setName(map.get("name").toString());
        if (map.get("address") != null) {
            productInfo.setAddress(map.get("address").toString());
        } else {
            productInfo.setAddress("");
        }
        if (map.get("address_name") != null) {
            productInfo.setAddressName(map.get("address_name").toString());
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
        int product_type = Integer.parseInt(map.get("product_type").toString());
        productInfo.setProductType(product_type);
        if (map.get("type") != null) {
            productInfo.setType(Integer.parseInt(map.get("type").toString()));
        }
        if (map.get("product_style") != null) {
            productInfo.setProductStyle(Integer.parseInt(map.get("product_style").toString()));
        }
        if (map.get("wuyu_type") != null) {
            String wuyuTypeStr = map.get("wuyu_type").toString();
            String wuyu_type_id;
            if (wuyuTypeStr.contains(",")) {
                String[] typeArr = wuyuTypeStr.split(",");
                wuyu_type_id = StringUtils.join(typeArr, "/");
            } else {
                wuyu_type_id = wuyuTypeStr;
            }
            productInfo.setWuyuType(wuyu_type_id);
        }
        productInfo.setMainImage(map.get("main_image").toString());
        int isAllowDistribution = Integer.parseInt(map.get("is_allow_distribution").toString());
        productInfo.setIsAllowDistribution(isAllowDistribution);
        productInfo.setRepeatPurchase(Integer.parseInt(map.get("repeat_purchase").toString()));
        productInfo.setPhone(map.get("phone").toString());
        if (map.get("introduce") != null) {
            productInfo.setIntroduce(Base64.getEncoder().encodeToString(map.get("introduce").toString().getBytes()));
        } else {
            productInfo.setIntroduce("");
        }
        if (map.get("vedio_path") != null) {
            productInfo.setVideoPath(map.get("vedio_path").toString());
        } else {
            productInfo.setVideoPath("");
        }
        if (map.get("instruction") != null) {
            productInfo.setInstruction(Base64.getEncoder().encodeToString(map.get("instruction").toString().getBytes()));
        } else {
            productInfo.setInstruction("");
        }
        productInfo.setBuyCount(map.get("buy_count").toString());
        productInfo.setCreateTime(DateUtils.getNow());
        if (map.get("deadline_time").toString().indexOf("NaN") != -1) {
            productInfo.setDeadlineTime(null);
        } else {
            productInfo.setDeadlineTime(map.get("deadline_time").toString());
        }
        if (map.get("qr_image") != null) {
            productInfo.setQrImage(map.get("qr_image").toString());
        }
        int rows;
        if (product_type == 0) {
            //产品
            rows = entityDao.addProductInfoService(productInfo);
        } else {
            //活动
            productInfo.setActivityType1(Integer.parseInt(map.get("activity_type1").toString()));
            productInfo.setActivityType2(Integer.parseInt(map.get("activity_type2").toString()));
            rows = entityDao.addActivityInfoService(productInfo);
        }
        int productId = productInfo.getId();
        //添加其他图片
        if (map.get("other_image") != null) {
            if (StringUtils.isNotEmpty(map.get("other_image").toString())) {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("productId", productId);
                picMap.put("other_image", map.get("other_image"));
                entityService.addOtherImagesToProductInfo(picMap);
            }
        }
        //添加规格
        if (product_type == 0) {
            //产品规格
            if (map.get("standardList") != null) {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("productId", productId);
                picMap.put("standardList", map.get("standardList"));
                entityService.addProductStandards(picMap);
            }
        } else if (product_type == 1) {
            //活动规格
            if (map.get("standardList") != null) {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("productId", productId);
                picMap.put("standardList", map.get("standardList"));
                entityService.addActivityStandards(picMap);
            }
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
    public int addCEndOrderService(Map<String, Object> map) {
        map.put("orderNo", Utils.getCurrentDateNoFlag());
        map.put("createTime", DateUtils.getAccurateDate());
        if (map.get("open_id") == null) {
            map.put("open_id", "");
        }
        if (map.get("retail_commission") == null) {
            map.put("retail_commission", "");
        }
        if (map.get("retail_commission_income") == null) {
            map.put("retail_commission_income", "");
        }
        if (map.get("primary_distribution_shop_id") == null) {
            map.put("primary_distribution_shop_id", "");
        }
        if (map.get("primary_distribution_open_id") == null) {
            map.put("primary_distribution_open_id", "");
        }
        if (map.get("platform_service_fee") == null) {
            map.put("platform_service_fee", "");
        }
        if (map.get("standard_id") == null) {
            map.put("standard_id", "");
        }
        if (map.get("receive_address") == null) {
            map.put("receive_address", "");
        }
        if (map.get("pay_time").toString().indexOf("NaN") != -1) {
            map.put("pay_time", null);
        }
        return entityDao.addCEndOrderService(map);
    }

    @Override
    public int addShopInfoService(Map<String, Object> map) {
        map.put("createTime", DateUtils.getAccurateDate());
        return entityDao.addShopInfoService(map);
    }

    // 插入其他图片到活动表
    @Override
    public int addOtherImagesToProduct(Map<String, Object> map) {
        int productId = Integer.parseInt(map.get("productId").toString());
        String pciStr = map.get("other_image").toString();
        Map<String, Object> paramsMap = new HashMap<>();
        String[] picArr;
        if (pciStr.contains(",")) {
            picArr = pciStr.split(",");
            for (String s : picArr) {
                paramsMap.put("productId", productId);
                paramsMap.put("url", s);
                entityDao.addOtherImagesToProduct(paramsMap);
            }
        } else {
            paramsMap.put("productId", productId);
            paramsMap.put("url", pciStr);
            entityDao.addOtherImagesToProduct(paramsMap);
        }
        return productId;
    }

    @Override
    public int deleteBasePicList(Map<String, Object> map) {
        return entityDao.deleteBasePicList(map);
    }

    @Override
    public int deleteEnterprisePicList(Map<String, Object> map) {
        return entityDao.deleteEnterprisePicList(map);
    }

    @Override
    public int deleteProductInfoPicList(Map<String, Object> map) {
        return entityDao.deleteProductInfoPicList(map);
    }

    @Override
    public int deleteProductPicList(Map<String, Object> map) {
        return entityDao.deleteProductPicList(map);
    }

    private void loadKeyColumns(String tableName, Map<String, Object> map, List<String> keyColumns, List<String> keys, List<String> updateColumns) {
        HashMap<String, Column> columnMap = getColumnByTable(tableName);
        iterator(map, new Loop() {
            @Override
            public void run(String key, String value) {
                Column column = columnMap.get(key);
                // 类型判断和处理,临时写一下,以后再考虑系统处理
                if (column == null || value == null) {
                    return;
                }
                if (column.getDataType().equals("int") && value.length() == 0) {
                    return;
                }
                String oneSetting = String.format(Utils.EQUAL_SQL, key, Utils.fillColumn(value));
                if (keys.contains(key)) {
                    keyColumns.add(oneSetting);
                } else {
                    if (updateColumns != null) {
                        updateColumns.add(oneSetting);
                    }
                }
            }
        });
    }

    public void callMessageUser(String tableName, Map<String, Object> map) {
        int verify = Integer.parseInt(map.get("verify").toString());
        if (verify == 1 || verify == 2) {
            String message = String.format("审批%s通过", verify == 2 ? "不" : "");
            CardInfo cardInfo = cardDao.getCardInfoBeanById(Integer.parseInt(map.get("card_id").toString()));
            if (tableName.equalsIgnoreCase("card_message")) {
                map.put("last", cardDao.getLast(Integer.parseInt(map.get("id").toString())));
                CardMessage cardMessage = ServiceHelper.convertMap2CardMessage(map, cardInfo,
                        message);
                pushService.pushMessage2OneUser(cardMessage);
            } else {
                CardMessageReply cardMessageReply = ServiceHelper.convertMap2CardMessageReply(
                        map, message);
                pushService.pushMessageReply2OneUser(cardMessageReply);
            }
        }
    }

    // 修改数据
    @Override
    public int updateService(String tableName, Map<String, Object> map) {
        List<String> updateColumns = new LinkedList<String>();
        List<String> keyColumns = new LinkedList<String>();
        loadKeyColumns(tableName, map, keyColumns, getKeyByTable(tableName), updateColumns);
        if (tableName.equalsIgnoreCase("card_message")) {
            if (Integer.parseInt(map.get("verify").toString()) == 1) {
                callMessageUser(tableName, map);
            }
        }
        if (tableName.equalsIgnoreCase("card_message_reply")) {
            callMessageUser(tableName, map);
        }
        return entityDao.updateEntity(tableName, Utils.join(updateColumns, ","), Utils.join(keyColumns, Utils.AND));
    }

    // 修改基地数据
    @Override
    public int updateBaseService(Map<String, Object> map) {
        if (map.get("other_image") != null) {
            if (StringUtils.isNotEmpty(map.get("other_image").toString())) {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("baseId", map.get("id"));
                picMap.put("other_image", map.get("other_image"));
                entityService.deleteBasePicList(picMap);
                entityService.addOtherImagesToBase(picMap);
            } else {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("baseId", map.get("id"));
                entityService.deleteBasePicList(picMap);
            }
        }
        String typeStr = map.get("topic_type_id").toString();
        String topic_type_id;
        if (typeStr.contains(",")) {
            String[] typeArr = typeStr.split(",");
            topic_type_id = StringUtils.join(typeArr, "/");
        } else {
            topic_type_id = typeStr;
        }
        map.put("topic_type_id", topic_type_id);
        return entityDao.updateBaseEntity(map);
    }

    // 修改活动数据
    @Override
    public int updateProductService(Map<String, Object> map) {
        if (map.get("other_image") != null) {
            if (StringUtils.isNotEmpty(map.get("other_image").toString())) {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("productId", map.get("id"));
                picMap.put("other_image", map.get("other_image"));
                entityService.deleteProductPicList(picMap);
                entityService.addOtherImagesToProduct(picMap);
            } else {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("productId", map.get("id"));
                entityService.deleteProductPicList(picMap);
            }
        }
        return entityDao.updateProductEntity(map);
    }

    // 修改企业服务数据
    @Override
    public int updateEnterpriseService(Map<String, Object> map) {
        if (map.get("other_image") != null) {
            if (StringUtils.isNotEmpty(map.get("other_image").toString())) {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("enterpriseId", map.get("id"));
                picMap.put("other_image", map.get("other_image"));
                entityService.deleteEnterprisePicList(picMap);
                entityService.addOtherImagesToEnterprise(picMap);
            } else {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("enterpriseId", map.get("id"));
                entityService.deleteEnterprisePicList(picMap);
            }
        }
        String typeStr = map.get("type").toString();
        String type;
        if (typeStr.contains(",")) {
            String[] typeArr = typeStr.split(",");
            type = StringUtils.join(typeArr, "/");
        } else {
            type = typeStr;
        }
        map.put("type", type);
        return entityDao.updateEnterpriseEntity(map);
    }

    // 修改机构数据
    @Override
    public int updateEstablishmentService(Map<String, Object> map) {
        return entityDao.updateEstablishmentEntity(map);
    }

    // 修改产品信息
    @Override
    public int updateProductInfoService(Map<String, Object> map) {
        //其他图片处理逻辑
        if (map.get("other_image") != null) {
            if (StringUtils.isNotEmpty(map.get("other_image").toString())) {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("productId", map.get("id"));
                picMap.put("other_image", map.get("other_image"));
                entityService.deleteProductInfoPicList(picMap);
                entityService.addOtherImagesToProductInfo(picMap);
            } else {
                Map<String, Object> picMap = new HashMap<>();
                picMap.put("productId", map.get("id"));
                entityService.deleteProductInfoPicList(picMap);
            }
        }

        int productType = Integer.parseInt(map.get("product_type").toString());
        if (productType == 0) {
            //产品规格处理逻辑先删除后新增
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", map.get("id"));
            entityDao.deleteProductStandardsService(paramMap);
            //添加规格
            if (map.get("standardList") != null) {
                Map<String, Object> standardMap = new HashMap<>();
                standardMap.put("productId", map.get("id"));
                standardMap.put("standardList", map.get("standardList"));
                entityService.addProductStandards(standardMap);
            }
        } else if (productType == 1) {
            //活动规格处理逻辑先删除后新增
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", map.get("id"));
            entityDao.deleteActivityStandardsService(paramMap);
            //添加规格
            if (map.get("standardList") != null) {
                Map<String, Object> standardMap = new HashMap<>();
                standardMap.put("productId", map.get("id"));
                standardMap.put("standardList", map.get("standardList"));
                entityService.addActivityStandards(standardMap);
            }
        }

        if (map.get("wuyu_type") != null) {
            String wuyuTypeStr = map.get("wuyu_type").toString();
            String wuyu_type_id;
            if (wuyuTypeStr.contains(",")) {
                String[] typeArr = wuyuTypeStr.split(",");
                wuyu_type_id = StringUtils.join(typeArr, "/");
            } else {
                wuyu_type_id = wuyuTypeStr;
            }
            map.put("wuyu_type", wuyu_type_id);
        }
        if (map.get("introduce") != null) {
            map.put("introduce", Base64.getEncoder().encodeToString(map.get("introduce").toString().getBytes()));
        } else {
            map.put("introduce", "");
        }
        if (map.get("vedio_path") != null) {
            map.put("vedio_path", map.get("vedio_path").toString());
        } else {
            map.put("vedio_path", "");
        }
        if (map.get("instruction") != null) {
            map.put("instruction", Base64.getEncoder().encodeToString(map.get("instruction").toString().getBytes()));
        } else {
            map.put("instruction", "");
        }
        if (map.get("deadline_time").toString().indexOf("NaN") != -1) {
            map.put("deadline_time", null);
        } else {
            map.put("deadline_time", map.get("deadline_time").toString());
        }

        int productId = Integer.parseInt(map.get("id").toString());
        //如果该商品允许分销，给所有分销小店都添加分销记录
        int isAllowDistribution = Integer.parseInt(map.get("is_allow_distribution").toString());
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
        } else if (isAllowDistribution == 0) {
            //不允许分销，删除所有分销记录
            productDao.deleteDistributionRecordByProductId(productId);
        }
        return entityDao.updateProductInfoService(map);
    }

    @Override
    public int updateCEndOrderService(Map<String, Object> map) {
        if (map.get("retail_commission") == null) {
            map.put("retail_commission", "");
        }
        if (map.get("retail_commission_income") == null) {
            map.put("retail_commission_income", "");
        }
        if (map.get("primary_distribution_shop_id") == null) {
            map.put("primary_distribution_shop_id", "");
        }
        if (map.get("primary_distribution_open_id") == null) {
            map.put("primary_distribution_open_id", "");
        }
        if (map.get("platform_service_fee") == null) {
            map.put("platform_service_fee", "");
        }
        if (map.get("standard_id") == null) {
            map.put("standard_id", "");
        }
        if (map.get("receive_address") == null) {
            map.put("receive_address", "");
        }
        if (map.get("pay_time").toString().indexOf("NaN") != -1) {
            map.put("pay_time", null);
        }
        return entityDao.updateCEndOrderService(map);
    }

    @Override
    public int updateShopInfoService(Map<String, Object> map) {
        return entityDao.updateShopInfoService(map);
    }

    // 删除数据
    @Override
    public int deleteService(String tableName, List<Map<String, Object>> list) {
        List<String> allRecordKeys = new LinkedList<String>();
        for (Map<String, Object> map : list) {
            List<String> keyColumns = new LinkedList<String>();
            loadKeyColumns(tableName, map, keyColumns, getKeyByTable(tableName), null);
            allRecordKeys.add(String.format("(%s)", Utils.join(keyColumns, Utils.AND)));
        }
        return entityDao.deleteEntity(tableName, Utils.join(allRecordKeys, Utils.OR));
    }

    // 删除基地数据
    @Override
    public int deleteBaseService(List<Map<String, Object>> list) {
        int rows = 0;

        for (Map<String, Object> item : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.get("id"));
            entityDao.deleteBaseEntity(map);
            rows++;
        }
        return rows;
    }

    // 删除基地数据
    @Override
    public int deleteBaseReserveService(List<Map<String, Object>> list) {
        int rows = 0;

        for (Map<String, Object> item : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.get("id"));
            entityDao.deleteBaseReserveEntity(map);
            rows++;
        }
        return rows;
    }

    // 删除企业服务数据
    @Override
    public int deleteEnterpriseService(List<Map<String, Object>> list) {
        int rows = 0;

        for (Map<String, Object> item : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.get("id"));
            entityDao.deleteEnterpriseService(map);
            rows++;
        }
        return rows;
    }

    // 删除企业服务数据
    @Override
    public int deleteEnterpriseConsultingService(List<Map<String, Object>> list) {
        int rows = 0;

        for (Map<String, Object> item : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.get("id"));
            entityDao.deleteEnterpriseConsultingService(map);
            rows++;
        }
        return rows;
    }

    // 删除企业服务数据
    @Override
    public int deleteEnterpriseCommentService(List<Map<String, Object>> list) {
        int rows = 0;

        for (Map<String, Object> item : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.get("id"));
            entityDao.deleteEnterpriseCommentService(map);
            rows++;
        }
        return rows;
    }

    // 删除机构数据
    @Override
    public int deleteEstablishmentService(List<Map<String, Object>> list) {
        int rows = 0;

        for (Map<String, Object> item : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.get("id"));
            entityDao.deleteEstablishmentService(map);
            rows++;
        }
        return rows;
    }

    // 删除产品数据
    @Override
    public int deleteProductInfoService(List<Map<String, Object>> list) {
        int rows = 0;

        for (Map<String, Object> item : list) {
            int productType = Integer.parseInt(item.get("product_type").toString());
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.get("id"));
            //删除产品
            entityDao.deleteProductInfoService(map);
            if (productType == 0) {
                //删除产品规格
                entityDao.deleteProductStandardsService(map);
            } else if (productType == 1) {
                //删除活动规格
                entityDao.deleteActivityStandardsService(map);
            }
            //删除关联产品的其他图片
            entityDao.deleteProductPicturesService(map);
            //删除分销记录表中的产品
            entityDao.deleteDistributionRecordsService(map);
            rows++;
        }
        return rows;
    }

    // 删除成长GO订单数据
    @Override
    public int deleteCEndOrderService(List<Map<String, Object>> list) {
        int rows = 0;

        for (Map<String, Object> item : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.get("id"));
            entityDao.deleteCEndOrderService(map);
            rows++;
        }
        return rows;
    }

    // 删除小店数据
    @Override
    public int deleteShopInfoService(List<Map<String, Object>> list) {
        int rows = 0;

        for (Map<String, Object> item : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.get("id"));
            entityDao.deleteShopInfoService(map);
            rows++;
        }
        return rows;
    }

    // 根据key查找数据
    @Override
    public List<LinkedHashMap> showService(String tableName, Map<String, Object> map) {
        String queryTableName = tableName;
        List<String> keyColumns = new LinkedList<String>();
        loadKeyColumns(queryTableName, map, keyColumns, getKeyByTable(queryTableName), null);
        return entityDao.findEntityByKey(queryTableName, Utils.join(keyColumns, Utils.AND));
    }

    // 查询所有数据，目前分页在客户端做，将来可以考虑服务端做
    @Override
    public List<LinkedHashMap> showAllService(String tableName) {
        List<LinkedHashMap> resultList = entityDao.findEntitys(tableName);
        if ("product_info".equals(tableName)) {
            resultList.forEach(item -> {
                int productType = Integer.parseInt(item.get("product_type").toString());
                item.put("introduce", new String(Base64.getDecoder().decode(item.get("introduce").toString())));
                item.put("instruction", new String(Base64.getDecoder().decode(item.get("instruction").toString())));
                int productId = Integer.parseInt(item.get("id").toString());
                if (productType == 0) {
                    //产品规格
                    item.put("standardList", entityDao.getProductStandardByProductId(productId));
                } else if (productType == 1) {
                    //活动规格
                    item.put("standardList", entityDao.getAcitivityStandardByProductId(productId));
                }
            });
        }
        return resultList;
    }

    // 查询基地数据，目前分页在客户端做，将来可以考虑服务端做
    @Override
    public List<LinkedHashMap> showBaseService(String tableName) {
        return entityDao.findBaseEntity(tableName);
    }

    @Override
    public List<LinkedHashMap> showSpecialService(Map<String, Object> map) {
        String tableName = map.get("model").toString();
        String key = map.get("key").toString();
        String value = map.get("value").toString();
        String selectList = Utils.join(new String[]{key, value}, ",");
        return entityDao.findSpecialEntitys(selectList, tableName);
    }

    @Override
    public List<LinkedHashMap> showSaaSService(String tableName, String condition) {
        return entityDao.findEntityByKey(tableName, condition);
    }

    @Override
    public List<LinkedHashMap> pictureList(int baseId) {
        return entityDao.pictureList(baseId);
    }

    @Override
    public List<LinkedHashMap> campaignPictureList(int productId) {
        return entityDao.campaignPictureList(productId);
    }

    @Override
    public List<LinkedHashMap> productPictureList(int productId) {
        return entityDao.productPictureList(productId);
    }

    @Override
    public List<LinkedHashMap> enterprisePictureList(int enterpriseId) {
        return entityDao.enterprisePictureList(enterpriseId);
    }

    @Override
    public List<LinkedHashMap> getLuckDrawMemberList() {
        return entityDao.getLuckDrawMemberList();
    }

    @Override
    public List<LinkedHashMap> getOutsideLuckDrawMemberList() {
        return entityDao.getOutsideLuckDrawMemberList();
    }

    @Override
    public int clearAllJoiner() {
        return entityDao.clearAllJoiner();
    }


    @Override
    public List<LinkedHashMap> getProductStandards() {
        return entityDao.getProductStandards();
    }

    @Override
    public List<LinkedHashMap> getActivityStandards() {
        return entityDao.getActivityStandards();
    }
}
